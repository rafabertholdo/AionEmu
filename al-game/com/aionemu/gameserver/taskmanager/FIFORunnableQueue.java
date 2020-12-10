package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.utils.concurrent.ExecuteWrapper;

public abstract class FIFORunnableQueue<T extends Runnable> extends FIFOSimpleExecutableQueue<T> {
  protected final void removeAndExecuteFirst() {
    ExecuteWrapper.execute((Runnable) removeFirst(), 5000L);
  }
}
