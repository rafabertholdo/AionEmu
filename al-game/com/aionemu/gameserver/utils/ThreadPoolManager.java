package com.aionemu.gameserver.utils;

import com.aionemu.commons.network.DisconnectionTask;
import com.aionemu.commons.network.DisconnectionThreadPool;
import com.aionemu.commons.utils.concurrent.AionRejectedExecutionHandler;
import com.aionemu.commons.utils.concurrent.ExecuteWrapper;
import com.aionemu.commons.utils.concurrent.ScheduledFutureWrapper;
import com.aionemu.gameserver.configs.main.ThreadConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;




















public final class ThreadPoolManager
  implements DisconnectionThreadPool
{
  private static final Logger log = Logger.getLogger(ThreadPoolManager.class);
  
  public static final long MAXIMUM_RUNTIME_IN_MILLISEC_WITHOUT_WARNING = 5000L;
  
  private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2L;

  
  private final ScheduledThreadPoolExecutor scheduledPool;

  
  private final ThreadPoolExecutor instantPool;

  
  private final ThreadPoolExecutor longRunningPool;
  
  private final ScheduledThreadPoolExecutor disconnectionScheduledThreadPool;

  
  private static final class SingletonHolder
  {
    private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
  }

  
  public static ThreadPoolManager getInstance() {
    return SingletonHolder.INSTANCE;
  }




  
  private ThreadPoolManager() {
    int instantPoolSize = Math.max(1, ThreadConfig.THREAD_POOL_SIZE / 3);
    
    this.scheduledPool = new ScheduledThreadPoolExecutor(ThreadConfig.THREAD_POOL_SIZE - instantPoolSize);
    this.scheduledPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
    this.scheduledPool.prestartAllCoreThreads();
    
    this.instantPool = new ThreadPoolExecutor(instantPoolSize, instantPoolSize, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000));
    
    this.instantPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
    this.instantPool.prestartAllCoreThreads();
    
    this.longRunningPool = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
    
    this.longRunningPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
    this.longRunningPool.prestartAllCoreThreads();
    
    this.disconnectionScheduledThreadPool = new ScheduledThreadPoolExecutor(4);
    this.disconnectionScheduledThreadPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
    this.disconnectionScheduledThreadPool.prestartAllCoreThreads();
    
    scheduleAtFixedRate(new Runnable()
        {
          public void run()
          {
            ThreadPoolManager.this.purge();
          }
        },  60000L, 60000L);
    
    log.info("ThreadPoolManager: Initialized with " + this.scheduledPool.getPoolSize() + " scheduler, " + this.instantPool.getPoolSize() + " instant, " + this.longRunningPool.getPoolSize() + " long, " + this.disconnectionScheduledThreadPool.getPoolSize() + " disconnection running thread(s).");
  }



  
  private final long validate(long delay) {
    return Math.max(0L, Math.min(MAX_DELAY, delay));
  }
  
  private static final class ThreadPoolExecuteWrapper
    extends ExecuteWrapper
  {
    private ThreadPoolExecuteWrapper(Runnable runnable) {
      super(runnable);
    }


    
    protected long getMaximumRuntimeInMillisecWithoutWarning() {
      return 5000L;
    }
  }



  
  public final ScheduledFuture<?> schedule(Runnable r, long delay) {
    ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
    delay = validate(delay);
    
    return (ScheduledFuture<?>)new ScheduledFutureWrapper(this.scheduledPool.schedule((Runnable)threadPoolExecuteWrapper, delay, TimeUnit.MILLISECONDS));
  }

  
  public final ScheduledFuture<?> scheduleEffect(Runnable r, long delay) {
    return schedule(r, delay);
  }

  
  public final ScheduledFuture<?> scheduleGeneral(Runnable r, long delay) {
    return schedule(r, delay);
  }

  
  public final ScheduledFuture<?> scheduleAi(Runnable r, long delay) {
    return schedule(r, delay);
  }



  
  public final ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period) {
    ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
    delay = validate(delay);
    period = validate(period);
    
    return (ScheduledFuture<?>)new ScheduledFutureWrapper(this.scheduledPool.scheduleAtFixedRate((Runnable)threadPoolExecuteWrapper, delay, period, TimeUnit.MILLISECONDS));
  }

  
  public final ScheduledFuture<?> scheduleEffectAtFixedRate(Runnable r, long delay, long period) {
    return scheduleAtFixedRate(r, delay, period);
  }

  
  public final ScheduledFuture<?> scheduleGeneralAtFixedRate(Runnable r, long delay, long period) {
    return scheduleAtFixedRate(r, delay, period);
  }

  
  public final ScheduledFuture<?> scheduleAiAtFixedRate(Runnable r, long delay, long period) {
    return scheduleAtFixedRate(r, delay, period);
  }



  
  public final void execute(Runnable r) {
    ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
    
    this.instantPool.execute((Runnable)threadPoolExecuteWrapper);
  }

  
  public final void executeTask(Runnable r) {
    execute(r);
  }

  
  public final void executeLongRunning(Runnable r) {
    ExecuteWrapper executeWrapper = new ExecuteWrapper(r);
    
    this.longRunningPool.execute((Runnable)executeWrapper);
  }



  
  public final Future<?> submit(Runnable r) {
    ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
    
    return this.instantPool.submit((Runnable)threadPoolExecuteWrapper);
  }

  
  public final Future<?> submitLongRunning(Runnable r) {
    ExecuteWrapper executeWrapper = new ExecuteWrapper(r);
    
    return this.longRunningPool.submit((Runnable)executeWrapper);
  }









  
  public void executeLsPacket(Runnable pkt) {
    execute(pkt);
  }







  
  public final void scheduleDisconnection(DisconnectionTask dt, long delay) {
    schedule((Runnable)dt, delay);
  }







  
  public void waitForDisconnectionTasks() {
    try {
      this.disconnectionScheduledThreadPool.shutdown();
      this.disconnectionScheduledThreadPool.awaitTermination(6L, TimeUnit.MINUTES);
    }
    catch (Exception e) {}
  }














  
  public ScheduledFuture<?> scheduleTaskManager(Runnable r, long delay) {
    return schedule(r, delay);
  }

  
  public void purge() {
    this.scheduledPool.purge();
    this.instantPool.purge();
    this.longRunningPool.purge();
    this.disconnectionScheduledThreadPool.purge();
  }




  
  public void shutdown() {
    long begin = System.currentTimeMillis();
    
    log.info("ThreadPoolManager: Shutting down.");
    log.info("\t... executing " + getTaskCount(this.scheduledPool) + " scheduled tasks.");
    log.info("\t... executing " + getTaskCount(this.instantPool) + " instant tasks.");
    log.info("\t... executing " + getTaskCount(this.longRunningPool) + " long running tasks.");
    
    this.scheduledPool.shutdown();
    this.instantPool.shutdown();
    this.longRunningPool.shutdown();
    
    boolean success = false;
    
    try {
      success |= awaitTermination(5000L);
      
      this.scheduledPool.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
      this.scheduledPool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
      
      success |= awaitTermination(10000L);
    }
    catch (InterruptedException e) {
      
      e.printStackTrace();
    } 
    
    log.info("\t... success: " + success + " in " + (System.currentTimeMillis() - begin) + " msec.");
    log.info("\t... " + getTaskCount(this.scheduledPool) + " scheduled tasks left.");
    log.info("\t... " + getTaskCount(this.instantPool) + " instant tasks left.");
    log.info("\t... " + getTaskCount(this.longRunningPool) + " long running tasks left.");
  }

  
  private int getTaskCount(ThreadPoolExecutor tp) {
    return tp.getQueue().size() + tp.getActiveCount();
  }

  
  public List<String> getStats() {
    List<String> list = new ArrayList<String>();
    
    list.add("");
    list.add("Scheduled pool:");
    list.add("=================================================");
    list.add("\tgetActiveCount: ...... " + this.scheduledPool.getActiveCount());
    list.add("\tgetCorePoolSize: ..... " + this.scheduledPool.getCorePoolSize());
    list.add("\tgetPoolSize: ......... " + this.scheduledPool.getPoolSize());
    list.add("\tgetLargestPoolSize: .. " + this.scheduledPool.getLargestPoolSize());
    list.add("\tgetMaximumPoolSize: .. " + this.scheduledPool.getMaximumPoolSize());
    list.add("\tgetCompletedTaskCount: " + this.scheduledPool.getCompletedTaskCount());
    list.add("\tgetQueuedTaskCount: .. " + this.scheduledPool.getQueue().size());
    list.add("\tgetTaskCount: ........ " + this.scheduledPool.getTaskCount());
    list.add("");
    list.add("Instant pool:");
    list.add("=================================================");
    list.add("\tgetActiveCount: ...... " + this.instantPool.getActiveCount());
    list.add("\tgetCorePoolSize: ..... " + this.instantPool.getCorePoolSize());
    list.add("\tgetPoolSize: ......... " + this.instantPool.getPoolSize());
    list.add("\tgetLargestPoolSize: .. " + this.instantPool.getLargestPoolSize());
    list.add("\tgetMaximumPoolSize: .. " + this.instantPool.getMaximumPoolSize());
    list.add("\tgetCompletedTaskCount: " + this.instantPool.getCompletedTaskCount());
    list.add("\tgetQueuedTaskCount: .. " + this.instantPool.getQueue().size());
    list.add("\tgetTaskCount: ........ " + this.instantPool.getTaskCount());
    list.add("");
    list.add("Long running pool:");
    list.add("=================================================");
    list.add("\tgetActiveCount: ...... " + this.longRunningPool.getActiveCount());
    list.add("\tgetCorePoolSize: ..... " + this.longRunningPool.getCorePoolSize());
    list.add("\tgetPoolSize: ......... " + this.longRunningPool.getPoolSize());
    list.add("\tgetLargestPoolSize: .. " + this.longRunningPool.getLargestPoolSize());
    list.add("\tgetMaximumPoolSize: .. " + this.longRunningPool.getMaximumPoolSize());
    list.add("\tgetCompletedTaskCount: " + this.longRunningPool.getCompletedTaskCount());
    list.add("\tgetQueuedTaskCount: .. " + this.longRunningPool.getQueue().size());
    list.add("\tgetTaskCount: ........ " + this.longRunningPool.getTaskCount());
    list.add("");
    list.add("");
    list.add("Disconnection running pool:");
    list.add("=================================================");
    list.add("\tgetActiveCount: ...... " + this.disconnectionScheduledThreadPool.getActiveCount());
    list.add("\tgetCorePoolSize: ..... " + this.disconnectionScheduledThreadPool.getCorePoolSize());
    list.add("\tgetPoolSize: ......... " + this.disconnectionScheduledThreadPool.getPoolSize());
    list.add("\tgetLargestPoolSize: .. " + this.disconnectionScheduledThreadPool.getLargestPoolSize());
    list.add("\tgetMaximumPoolSize: .. " + this.disconnectionScheduledThreadPool.getMaximumPoolSize());
    list.add("\tgetCompletedTaskCount: " + this.disconnectionScheduledThreadPool.getCompletedTaskCount());
    list.add("\tgetQueuedTaskCount: .. " + this.disconnectionScheduledThreadPool.getQueue().size());
    list.add("\tgetTaskCount: ........ " + this.disconnectionScheduledThreadPool.getTaskCount());
    list.add("");
    
    return list;
  }

  
  private boolean awaitTermination(long timeoutInMillisec) throws InterruptedException {
    long begin = System.currentTimeMillis();
    
    while (System.currentTimeMillis() - begin < timeoutInMillisec) {
      
      if (!this.scheduledPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.scheduledPool.getActiveCount() > 0) {
        continue;
      }
      if (!this.instantPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.instantPool.getActiveCount() > 0) {
        continue;
      }
      if (!this.longRunningPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.longRunningPool.getActiveCount() > 0) {
        continue;
      }
      return true;
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\ThreadPoolManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
