/*     */ package com.aionemu.gameserver.utils;
/*     */ 
/*     */ import com.aionemu.commons.network.DisconnectionTask;
/*     */ import com.aionemu.commons.network.DisconnectionThreadPool;
/*     */ import com.aionemu.commons.utils.concurrent.AionRejectedExecutionHandler;
/*     */ import com.aionemu.commons.utils.concurrent.ExecuteWrapper;
/*     */ import com.aionemu.commons.utils.concurrent.ScheduledFutureWrapper;
/*     */ import com.aionemu.gameserver.configs.main.ThreadConfig;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.RejectedExecutionHandler;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.SynchronousQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class ThreadPoolManager
/*     */   implements DisconnectionThreadPool
/*     */ {
/*  43 */   private static final Logger log = Logger.getLogger(ThreadPoolManager.class);
/*     */   
/*     */   public static final long MAXIMUM_RUNTIME_IN_MILLISEC_WITHOUT_WARNING = 5000L;
/*     */   
/*  47 */   private static final long MAX_DELAY = TimeUnit.NANOSECONDS.toMillis(Long.MAX_VALUE - System.nanoTime()) / 2L;
/*     */ 
/*     */   
/*     */   private final ScheduledThreadPoolExecutor scheduledPool;
/*     */ 
/*     */   
/*     */   private final ThreadPoolExecutor instantPool;
/*     */ 
/*     */   
/*     */   private final ThreadPoolExecutor longRunningPool;
/*     */   
/*     */   private final ScheduledThreadPoolExecutor disconnectionScheduledThreadPool;
/*     */ 
/*     */   
/*     */   private static final class SingletonHolder
/*     */   {
/*  63 */     private static final ThreadPoolManager INSTANCE = new ThreadPoolManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ThreadPoolManager getInstance() {
/*  68 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ThreadPoolManager() {
/*  76 */     int instantPoolSize = Math.max(1, ThreadConfig.THREAD_POOL_SIZE / 3);
/*     */     
/*  78 */     this.scheduledPool = new ScheduledThreadPoolExecutor(ThreadConfig.THREAD_POOL_SIZE - instantPoolSize);
/*  79 */     this.scheduledPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
/*  80 */     this.scheduledPool.prestartAllCoreThreads();
/*     */     
/*  82 */     this.instantPool = new ThreadPoolExecutor(instantPoolSize, instantPoolSize, 0L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(100000));
/*     */     
/*  84 */     this.instantPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
/*  85 */     this.instantPool.prestartAllCoreThreads();
/*     */     
/*  87 */     this.longRunningPool = new ThreadPoolExecutor(0, 2147483647, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
/*     */     
/*  89 */     this.longRunningPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
/*  90 */     this.longRunningPool.prestartAllCoreThreads();
/*     */     
/*  92 */     this.disconnectionScheduledThreadPool = new ScheduledThreadPoolExecutor(4);
/*  93 */     this.disconnectionScheduledThreadPool.setRejectedExecutionHandler((RejectedExecutionHandler)new AionRejectedExecutionHandler());
/*  94 */     this.disconnectionScheduledThreadPool.prestartAllCoreThreads();
/*     */     
/*  96 */     scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 100 */             ThreadPoolManager.this.purge();
/*     */           }
/*     */         },  60000L, 60000L);
/*     */     
/* 104 */     log.info("ThreadPoolManager: Initialized with " + this.scheduledPool.getPoolSize() + " scheduler, " + this.instantPool.getPoolSize() + " instant, " + this.longRunningPool.getPoolSize() + " long, " + this.disconnectionScheduledThreadPool.getPoolSize() + " disconnection running thread(s).");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final long validate(long delay) {
/* 111 */     return Math.max(0L, Math.min(MAX_DELAY, delay));
/*     */   }
/*     */   
/*     */   private static final class ThreadPoolExecuteWrapper
/*     */     extends ExecuteWrapper
/*     */   {
/*     */     private ThreadPoolExecuteWrapper(Runnable runnable) {
/* 118 */       super(runnable);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected long getMaximumRuntimeInMillisecWithoutWarning() {
/* 124 */       return 5000L;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> schedule(Runnable r, long delay) {
/* 132 */     ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
/* 133 */     delay = validate(delay);
/*     */     
/* 135 */     return (ScheduledFuture<?>)new ScheduledFutureWrapper(this.scheduledPool.schedule((Runnable)threadPoolExecuteWrapper, delay, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleEffect(Runnable r, long delay) {
/* 140 */     return schedule(r, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleGeneral(Runnable r, long delay) {
/* 145 */     return schedule(r, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleAi(Runnable r, long delay) {
/* 150 */     return schedule(r, delay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleAtFixedRate(Runnable r, long delay, long period) {
/* 157 */     ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
/* 158 */     delay = validate(delay);
/* 159 */     period = validate(period);
/*     */     
/* 161 */     return (ScheduledFuture<?>)new ScheduledFutureWrapper(this.scheduledPool.scheduleAtFixedRate((Runnable)threadPoolExecuteWrapper, delay, period, TimeUnit.MILLISECONDS));
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleEffectAtFixedRate(Runnable r, long delay, long period) {
/* 166 */     return scheduleAtFixedRate(r, delay, period);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleGeneralAtFixedRate(Runnable r, long delay, long period) {
/* 171 */     return scheduleAtFixedRate(r, delay, period);
/*     */   }
/*     */ 
/*     */   
/*     */   public final ScheduledFuture<?> scheduleAiAtFixedRate(Runnable r, long delay, long period) {
/* 176 */     return scheduleAtFixedRate(r, delay, period);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void execute(Runnable r) {
/* 183 */     ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
/*     */     
/* 185 */     this.instantPool.execute((Runnable)threadPoolExecuteWrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void executeTask(Runnable r) {
/* 190 */     execute(r);
/*     */   }
/*     */ 
/*     */   
/*     */   public final void executeLongRunning(Runnable r) {
/* 195 */     ExecuteWrapper executeWrapper = new ExecuteWrapper(r);
/*     */     
/* 197 */     this.longRunningPool.execute((Runnable)executeWrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Future<?> submit(Runnable r) {
/* 204 */     ThreadPoolExecuteWrapper threadPoolExecuteWrapper = new ThreadPoolExecuteWrapper(r);
/*     */     
/* 206 */     return this.instantPool.submit((Runnable)threadPoolExecuteWrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public final Future<?> submitLongRunning(Runnable r) {
/* 211 */     ExecuteWrapper executeWrapper = new ExecuteWrapper(r);
/*     */     
/* 213 */     return this.longRunningPool.submit((Runnable)executeWrapper);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void executeLsPacket(Runnable pkt) {
/* 226 */     execute(pkt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scheduleDisconnection(DisconnectionTask dt, long delay) {
/* 237 */     schedule((Runnable)dt, delay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void waitForDisconnectionTasks() {
/*     */     try {
/* 249 */       this.disconnectionScheduledThreadPool.shutdown();
/* 250 */       this.disconnectionScheduledThreadPool.awaitTermination(6L, TimeUnit.MINUTES);
/*     */     }
/* 252 */     catch (Exception e) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScheduledFuture<?> scheduleTaskManager(Runnable r, long delay) {
/* 270 */     return schedule(r, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public void purge() {
/* 275 */     this.scheduledPool.purge();
/* 276 */     this.instantPool.purge();
/* 277 */     this.longRunningPool.purge();
/* 278 */     this.disconnectionScheduledThreadPool.purge();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 286 */     long begin = System.currentTimeMillis();
/*     */     
/* 288 */     log.info("ThreadPoolManager: Shutting down.");
/* 289 */     log.info("\t... executing " + getTaskCount(this.scheduledPool) + " scheduled tasks.");
/* 290 */     log.info("\t... executing " + getTaskCount(this.instantPool) + " instant tasks.");
/* 291 */     log.info("\t... executing " + getTaskCount(this.longRunningPool) + " long running tasks.");
/*     */     
/* 293 */     this.scheduledPool.shutdown();
/* 294 */     this.instantPool.shutdown();
/* 295 */     this.longRunningPool.shutdown();
/*     */     
/* 297 */     boolean success = false;
/*     */     
/*     */     try {
/* 300 */       success |= awaitTermination(5000L);
/*     */       
/* 302 */       this.scheduledPool.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
/* 303 */       this.scheduledPool.setContinueExistingPeriodicTasksAfterShutdownPolicy(false);
/*     */       
/* 305 */       success |= awaitTermination(10000L);
/*     */     }
/* 307 */     catch (InterruptedException e) {
/*     */       
/* 309 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 312 */     log.info("\t... success: " + success + " in " + (System.currentTimeMillis() - begin) + " msec.");
/* 313 */     log.info("\t... " + getTaskCount(this.scheduledPool) + " scheduled tasks left.");
/* 314 */     log.info("\t... " + getTaskCount(this.instantPool) + " instant tasks left.");
/* 315 */     log.info("\t... " + getTaskCount(this.longRunningPool) + " long running tasks left.");
/*     */   }
/*     */ 
/*     */   
/*     */   private int getTaskCount(ThreadPoolExecutor tp) {
/* 320 */     return tp.getQueue().size() + tp.getActiveCount();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getStats() {
/* 325 */     List<String> list = new ArrayList<String>();
/*     */     
/* 327 */     list.add("");
/* 328 */     list.add("Scheduled pool:");
/* 329 */     list.add("=================================================");
/* 330 */     list.add("\tgetActiveCount: ...... " + this.scheduledPool.getActiveCount());
/* 331 */     list.add("\tgetCorePoolSize: ..... " + this.scheduledPool.getCorePoolSize());
/* 332 */     list.add("\tgetPoolSize: ......... " + this.scheduledPool.getPoolSize());
/* 333 */     list.add("\tgetLargestPoolSize: .. " + this.scheduledPool.getLargestPoolSize());
/* 334 */     list.add("\tgetMaximumPoolSize: .. " + this.scheduledPool.getMaximumPoolSize());
/* 335 */     list.add("\tgetCompletedTaskCount: " + this.scheduledPool.getCompletedTaskCount());
/* 336 */     list.add("\tgetQueuedTaskCount: .. " + this.scheduledPool.getQueue().size());
/* 337 */     list.add("\tgetTaskCount: ........ " + this.scheduledPool.getTaskCount());
/* 338 */     list.add("");
/* 339 */     list.add("Instant pool:");
/* 340 */     list.add("=================================================");
/* 341 */     list.add("\tgetActiveCount: ...... " + this.instantPool.getActiveCount());
/* 342 */     list.add("\tgetCorePoolSize: ..... " + this.instantPool.getCorePoolSize());
/* 343 */     list.add("\tgetPoolSize: ......... " + this.instantPool.getPoolSize());
/* 344 */     list.add("\tgetLargestPoolSize: .. " + this.instantPool.getLargestPoolSize());
/* 345 */     list.add("\tgetMaximumPoolSize: .. " + this.instantPool.getMaximumPoolSize());
/* 346 */     list.add("\tgetCompletedTaskCount: " + this.instantPool.getCompletedTaskCount());
/* 347 */     list.add("\tgetQueuedTaskCount: .. " + this.instantPool.getQueue().size());
/* 348 */     list.add("\tgetTaskCount: ........ " + this.instantPool.getTaskCount());
/* 349 */     list.add("");
/* 350 */     list.add("Long running pool:");
/* 351 */     list.add("=================================================");
/* 352 */     list.add("\tgetActiveCount: ...... " + this.longRunningPool.getActiveCount());
/* 353 */     list.add("\tgetCorePoolSize: ..... " + this.longRunningPool.getCorePoolSize());
/* 354 */     list.add("\tgetPoolSize: ......... " + this.longRunningPool.getPoolSize());
/* 355 */     list.add("\tgetLargestPoolSize: .. " + this.longRunningPool.getLargestPoolSize());
/* 356 */     list.add("\tgetMaximumPoolSize: .. " + this.longRunningPool.getMaximumPoolSize());
/* 357 */     list.add("\tgetCompletedTaskCount: " + this.longRunningPool.getCompletedTaskCount());
/* 358 */     list.add("\tgetQueuedTaskCount: .. " + this.longRunningPool.getQueue().size());
/* 359 */     list.add("\tgetTaskCount: ........ " + this.longRunningPool.getTaskCount());
/* 360 */     list.add("");
/* 361 */     list.add("");
/* 362 */     list.add("Disconnection running pool:");
/* 363 */     list.add("=================================================");
/* 364 */     list.add("\tgetActiveCount: ...... " + this.disconnectionScheduledThreadPool.getActiveCount());
/* 365 */     list.add("\tgetCorePoolSize: ..... " + this.disconnectionScheduledThreadPool.getCorePoolSize());
/* 366 */     list.add("\tgetPoolSize: ......... " + this.disconnectionScheduledThreadPool.getPoolSize());
/* 367 */     list.add("\tgetLargestPoolSize: .. " + this.disconnectionScheduledThreadPool.getLargestPoolSize());
/* 368 */     list.add("\tgetMaximumPoolSize: .. " + this.disconnectionScheduledThreadPool.getMaximumPoolSize());
/* 369 */     list.add("\tgetCompletedTaskCount: " + this.disconnectionScheduledThreadPool.getCompletedTaskCount());
/* 370 */     list.add("\tgetQueuedTaskCount: .. " + this.disconnectionScheduledThreadPool.getQueue().size());
/* 371 */     list.add("\tgetTaskCount: ........ " + this.disconnectionScheduledThreadPool.getTaskCount());
/* 372 */     list.add("");
/*     */     
/* 374 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean awaitTermination(long timeoutInMillisec) throws InterruptedException {
/* 379 */     long begin = System.currentTimeMillis();
/*     */     
/* 381 */     while (System.currentTimeMillis() - begin < timeoutInMillisec) {
/*     */       
/* 383 */       if (!this.scheduledPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.scheduledPool.getActiveCount() > 0) {
/*     */         continue;
/*     */       }
/* 386 */       if (!this.instantPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.instantPool.getActiveCount() > 0) {
/*     */         continue;
/*     */       }
/* 389 */       if (!this.longRunningPool.awaitTermination(10L, TimeUnit.MILLISECONDS) && this.longRunningPool.getActiveCount() > 0) {
/*     */         continue;
/*     */       }
/* 392 */       return true;
/*     */     } 
/*     */     
/* 395 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\ThreadPoolManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */