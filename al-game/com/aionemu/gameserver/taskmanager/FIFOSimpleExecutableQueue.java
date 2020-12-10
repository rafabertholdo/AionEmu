package com.aionemu.gameserver.taskmanager;

import java.util.Collection;
import javolution.util.FastList;

public abstract class FIFOSimpleExecutableQueue<T> extends FIFOExecutableQueue {
  private final FastList<T> queue = new FastList();

  public final void execute(T t) {
    synchronized (this.queue) {

      this.queue.addLast(t);
    }

    execute();
  }

  public final void executeAll(Collection<T> c) {
    synchronized (this.queue) {

      this.queue.addAll(c);
    }

    execute();
  }

  public final void remove(T t) {
    synchronized (this.queue) {

      this.queue.remove(t);
    }
  }

  protected final boolean isEmpty() {
    synchronized (this.queue) {

      return this.queue.isEmpty();
    }
  }

  protected final T removeFirst() {
    synchronized (this.queue) {

      return (T) this.queue.removeFirst();
    }
  }

  protected abstract void removeAndExecuteFirst();
}
