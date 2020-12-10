package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
import java.util.Set;
import javolution.util.FastCollection;
import javolution.util.FastSet;




















public abstract class AbstractIterativePeriodicTaskManager<T>
  extends AbstractPeriodicTaskManager
{
  private final Set<T> startList = (Set<T>)new FastSet();
  private final Set<T> stopList = (Set<T>)new FastSet();
  
  private final FastSet<T> activeTasks = new FastSet();

  
  protected AbstractIterativePeriodicTaskManager(int period) {
    super(period);
  }

  
  public boolean hasTask(T task) {
    readLock();
    
    try {
      if (this.stopList.contains(task)) {
        return false;
      }
      return (this.activeTasks.contains(task) || this.startList.contains(task));
    }
    finally {
      
      readUnlock();
    } 
  }

  
  public void startTask(T task) {
    writeLock();
    
    try {
      this.startList.add(task);
      
      this.stopList.remove(task);
    }
    finally {
      
      writeUnlock();
    } 
  }

  
  public void stopTask(T task) {
    writeLock();
    
    try {
      this.stopList.add(task);
      
      this.startList.remove(task);
    }
    finally {
      
      writeUnlock();
    } 
  }


  
  public final void run() {
    writeLock();
    
    try {
      this.activeTasks.addAll(this.startList);
      this.activeTasks.removeAll(this.stopList);
      
      this.startList.clear();
      this.stopList.clear();
    }
    finally {
      
      writeUnlock();
    } 
    
    for (FastCollection.Record r = this.activeTasks.head(), end = this.activeTasks.tail(); (r = r.getNext()) != end; ) {
      
      T task = (T)this.activeTasks.valueOf(r);
      long begin = System.nanoTime();

      
      try {
        callTask(task);
      }
      catch (RuntimeException e) {
        
        log.warn("", e);
      }
      finally {
        
        RunnableStatsManager.handleStats(task.getClass(), getCalledMethodName(), System.nanoTime() - begin);
      } 
    } 
  }
  
  protected abstract void callTask(T paramT);
  
  protected abstract String getCalledMethodName();
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\AbstractIterativePeriodicTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
