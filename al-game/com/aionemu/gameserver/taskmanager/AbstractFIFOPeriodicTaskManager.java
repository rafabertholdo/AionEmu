package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.utils.AEFastSet;
import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
import java.util.Collection;
import org.apache.log4j.Logger;

public abstract class AbstractFIFOPeriodicTaskManager<T> extends AbstractPeriodicTaskManager {
  protected static final Logger log = Logger.getLogger(AbstractFIFOPeriodicTaskManager.class);

  private final AEFastSet<T> queue = new AEFastSet();

  private final AEFastSet<T> activeTasks = new AEFastSet();

  public AbstractFIFOPeriodicTaskManager(int period) {
    super(period);
  }

  public final void add(T t) {
    writeLock();

    try {
      this.queue.add(t);
    } finally {

      writeUnlock();
    }
  }

  public final void run() {
    writeLock();

    try {
      this.activeTasks.addAll((Collection) this.queue);

      this.queue.clear();
    } finally {

      writeUnlock();
    }
    T task;
    while ((task = (T) this.activeTasks.removeFirst()) != null) {

      long begin = System.nanoTime();

      try {
        callTask(task);
      } catch (RuntimeException e) {

        log.warn("", e);
      } finally {

        RunnableStatsManager.handleStats(task.getClass(), getCalledMethodName(), System.nanoTime() - begin);
      }
    }
  }

  protected abstract void callTask(T paramT);

  protected abstract String getCalledMethodName();
}
