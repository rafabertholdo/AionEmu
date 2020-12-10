package com.aionemu.commons.taskmanager;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public abstract class AbstractLockManager {
  private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

  private final ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
  private final ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();

  public final void writeLock() {
    this.writeLock.lock();
  }

  public final void writeUnlock() {
    this.writeLock.unlock();
  }

  public final void readLock() {
    this.readLock.lock();
  }

  public final void readUnlock() {
    this.readLock.unlock();
  }
}
