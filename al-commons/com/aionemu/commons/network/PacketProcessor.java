package com.aionemu.commons.network;

import com.aionemu.commons.network.packet.BaseClientPacket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

public class PacketProcessor<T extends AConnection> {
  private static final Logger log = Logger.getLogger(PacketProcessor.class.getName());

  private static final int reduceThreshold = 3;

  private static final int increaseThreshold = 50;

  private final Lock lock = new ReentrantLock();

  private final Condition notEmpty = this.lock.newCondition();

  private final List<BaseClientPacket<T>> packets = new LinkedList<BaseClientPacket<T>>();

  private final List<Thread> threads = new ArrayList<Thread>();

  private final int minThreads;

  private final int maxThreads;

  public PacketProcessor(int minThreads, int maxThreads) {
    if (minThreads <= 0)
      minThreads = 1;
    if (maxThreads < minThreads) {
      maxThreads = minThreads;
    }
    this.minThreads = minThreads;
    this.maxThreads = maxThreads;

    if (minThreads != maxThreads) {
      startCheckerThread();
    }
    for (int i = 0; i < minThreads; i++) {
      newThread();
    }
  }

  private void startCheckerThread() {
    (new Thread(new CheckerTask(), "PacketProcessor:Checker")).start();
  }

  private boolean newThread() {
    if (this.threads.size() >= this.maxThreads) {
      return false;
    }
    String name = "PacketProcessor:" + this.threads.size();
    log.debug("Creating new PacketProcessor Thread: " + name);

    Thread t = new Thread(new PacketProcessorTask(), name);
    this.threads.add(t);
    t.start();

    return true;
  }

  private void killThread() {
    if (this.threads.size() < this.minThreads) {

      Thread t = this.threads.remove(this.threads.size() - 1);
      log.debug("Killing PacketProcessor Thread: " + t.getName());
      t.interrupt();
    }
  }

  public final void executePacket(BaseClientPacket<T> packet) {
    this.lock.lock();

    try {
      this.packets.add(packet);
      this.notEmpty.signal();
    } finally {

      this.lock.unlock();
    }
  }

  private BaseClientPacket<T> getFirstAviable() {
    while (true) {
      while (this.packets.isEmpty()) {
        this.notEmpty.awaitUninterruptibly();
      }
      ListIterator<BaseClientPacket<T>> it = this.packets.listIterator();
      while (it.hasNext()) {

        BaseClientPacket<T> packet = it.next();
        if (packet.getConnection().tryLockConnection()) {

          it.remove();
          return packet;
        }
      }
      this.notEmpty.awaitUninterruptibly();
    }
  }

  private final class PacketProcessorTask implements Runnable {
    private PacketProcessorTask() {
    }

    public void run() {
      BaseClientPacket<T> packet = null;

      while (true) {
        PacketProcessor.this.lock.lock();

        try {
          if (packet != null) {
            packet.getConnection().unlockConnection();
          }

          if (Thread.interrupted()) {
            return;
          }
          packet = PacketProcessor.this.getFirstAviable();
        } finally {

          PacketProcessor.this.lock.unlock();
        }
        packet.run();
      }
    }
  }

  private final class CheckerTask implements Runnable {
    private final int sleepTime = 60000;

    private int lastSize = 0;

    public void run() {
      try {
        Thread.sleep(60000L);
      } catch (InterruptedException e) {
      }

      int sizeNow = PacketProcessor.this.packets.size();

      if (sizeNow < this.lastSize) {

        if (sizeNow < 3) {

          PacketProcessor.this.killThread();
        }
      } else if (sizeNow > this.lastSize && sizeNow > 50) {

        if (!PacketProcessor.this.newThread() && sizeNow >= 150) {
          PacketProcessor.log.info("Lagg detected! [" + sizeNow
              + " client packets are waiting for execution]. You should consider increasing PacketProcessor maxThreads or hardware upgrade.");
        }
      }

      this.lastSize = sizeNow;
    }

    private CheckerTask() {
    }
  }
}
