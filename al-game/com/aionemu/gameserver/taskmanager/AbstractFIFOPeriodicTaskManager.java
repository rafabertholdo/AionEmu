/*    */ package com.aionemu.gameserver.taskmanager;
/*    */ 
/*    */ import com.aionemu.commons.utils.AEFastSet;
/*    */ import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
/*    */ import java.util.Collection;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractFIFOPeriodicTaskManager<T>
/*    */   extends AbstractPeriodicTaskManager
/*    */ {
/* 30 */   protected static final Logger log = Logger.getLogger(AbstractFIFOPeriodicTaskManager.class);
/*    */   
/* 32 */   private final AEFastSet<T> queue = new AEFastSet();
/*    */   
/* 34 */   private final AEFastSet<T> activeTasks = new AEFastSet();
/*    */ 
/*    */   
/*    */   public AbstractFIFOPeriodicTaskManager(int period) {
/* 38 */     super(period);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void add(T t) {
/* 43 */     writeLock();
/*    */     
/*    */     try {
/* 46 */       this.queue.add(t);
/*    */     }
/*    */     finally {
/*    */       
/* 50 */       writeUnlock();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void run() {
/* 57 */     writeLock();
/*    */     
/*    */     try {
/* 60 */       this.activeTasks.addAll((Collection)this.queue);
/*    */       
/* 62 */       this.queue.clear();
/*    */     }
/*    */     finally {
/*    */       
/* 66 */       writeUnlock();
/*    */     } 
/*    */     T task;
/* 69 */     while ((task = (T)this.activeTasks.removeFirst()) != null) {
/*    */       
/* 71 */       long begin = System.nanoTime();
/*    */ 
/*    */       
/*    */       try {
/* 75 */         callTask(task);
/*    */       }
/* 77 */       catch (RuntimeException e) {
/*    */         
/* 79 */         log.warn("", e);
/*    */       }
/*    */       finally {
/*    */         
/* 83 */         RunnableStatsManager.handleStats(task.getClass(), getCalledMethodName(), System.nanoTime() - begin);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void callTask(T paramT);
/*    */   
/*    */   protected abstract String getCalledMethodName();
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\AbstractFIFOPeriodicTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */