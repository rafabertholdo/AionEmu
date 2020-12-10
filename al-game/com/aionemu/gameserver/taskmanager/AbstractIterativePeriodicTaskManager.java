/*     */ package com.aionemu.gameserver.taskmanager;
/*     */ 
/*     */ import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
/*     */ import java.util.Set;
/*     */ import javolution.util.FastCollection;
/*     */ import javolution.util.FastSet;
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
/*     */ public abstract class AbstractIterativePeriodicTaskManager<T>
/*     */   extends AbstractPeriodicTaskManager
/*     */ {
/*  30 */   private final Set<T> startList = (Set<T>)new FastSet();
/*  31 */   private final Set<T> stopList = (Set<T>)new FastSet();
/*     */   
/*  33 */   private final FastSet<T> activeTasks = new FastSet();
/*     */ 
/*     */   
/*     */   protected AbstractIterativePeriodicTaskManager(int period) {
/*  37 */     super(period);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTask(T task) {
/*  42 */     readLock();
/*     */     
/*     */     try {
/*  45 */       if (this.stopList.contains(task)) {
/*  46 */         return false;
/*     */       }
/*  48 */       return (this.activeTasks.contains(task) || this.startList.contains(task));
/*     */     }
/*     */     finally {
/*     */       
/*  52 */       readUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void startTask(T task) {
/*  58 */     writeLock();
/*     */     
/*     */     try {
/*  61 */       this.startList.add(task);
/*     */       
/*  63 */       this.stopList.remove(task);
/*     */     }
/*     */     finally {
/*     */       
/*  67 */       writeUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopTask(T task) {
/*  73 */     writeLock();
/*     */     
/*     */     try {
/*  76 */       this.stopList.add(task);
/*     */       
/*  78 */       this.startList.remove(task);
/*     */     }
/*     */     finally {
/*     */       
/*  82 */       writeUnlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final void run() {
/*  89 */     writeLock();
/*     */     
/*     */     try {
/*  92 */       this.activeTasks.addAll(this.startList);
/*  93 */       this.activeTasks.removeAll(this.stopList);
/*     */       
/*  95 */       this.startList.clear();
/*  96 */       this.stopList.clear();
/*     */     }
/*     */     finally {
/*     */       
/* 100 */       writeUnlock();
/*     */     } 
/*     */     
/* 103 */     for (FastCollection.Record r = this.activeTasks.head(), end = this.activeTasks.tail(); (r = r.getNext()) != end; ) {
/*     */       
/* 105 */       T task = (T)this.activeTasks.valueOf(r);
/* 106 */       long begin = System.nanoTime();
/*     */ 
/*     */       
/*     */       try {
/* 110 */         callTask(task);
/*     */       }
/* 112 */       catch (RuntimeException e) {
/*     */         
/* 114 */         log.warn("", e);
/*     */       }
/*     */       finally {
/*     */         
/* 118 */         RunnableStatsManager.handleStats(task.getClass(), getCalledMethodName(), System.nanoTime() - begin);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void callTask(T paramT);
/*     */   
/*     */   protected abstract String getCalledMethodName();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\AbstractIterativePeriodicTaskManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */