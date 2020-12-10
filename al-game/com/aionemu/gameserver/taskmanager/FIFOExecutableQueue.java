package com.aionemu.gameserver.taskmanager;

import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.concurrent.locks.ReentrantLock;

public abstract class FIFOExecutableQueue implements Runnable {
  private static final byte NONE = 0;
  private static final byte QUEUED = 1;
  private static final byte RUNNING = 2;
  private final ReentrantLock lock = new ReentrantLock();

  private volatile byte state = 0;

  protected final void execute() {
    lock();

    try {
      if (this.state != 0) {
        return;
      }
      this.state = 1;
    } finally {

      unlock();
    }

    ThreadPoolManager.getInstance().execute(this);
  }

  public final void lock() {
    this.lock.lock();
  }

  public final void unlock() {
    this.lock.unlock();
  }

  public final void run() {
    try {
      while (!isEmpty()) {

        setState((byte) 1, (byte) 2);

        try {
          while (!isEmpty()) {
            removeAndExecuteFirst();
          }
        } finally {

          setState((byte) 2, (byte) 1);
        }

      }
    } finally {

      setState((byte) 1, (byte) 0);
    }
  }

  private void setState(byte expected, byte value) {
    lock();

    try {
      if (this.state != expected) {
        throw new IllegalStateException("state: " + this.state + ", expected: " + expected);
      }
    } finally {

      this.state = value;

      unlock();
    }
  }

  protected abstract boolean isEmpty();

  protected abstract void removeAndExecuteFirst();
}
