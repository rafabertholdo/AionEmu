/*    */ package com.aionemu.commons.utils.concurrent;
/*    */ 
/*    */ import java.util.concurrent.Delayed;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
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
/*    */ public final class ScheduledFutureWrapper
/*    */   implements ScheduledFuture<Object>
/*    */ {
/*    */   private final ScheduledFuture<?> future;
/*    */   
/*    */   public ScheduledFutureWrapper(ScheduledFuture<?> future) {
/* 34 */     this.future = future;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public long getDelay(TimeUnit unit) {
/* 40 */     return this.future.getDelay(unit);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int compareTo(Delayed o) {
/* 46 */     return this.future.compareTo(o);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 55 */     return this.future.cancel(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get() throws InterruptedException, ExecutionException {
/* 61 */     return this.future.get();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 67 */     return this.future.get(timeout, unit);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 73 */     return this.future.isCancelled();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isDone() {
/* 79 */     return this.future.isDone();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\concurrent\ScheduledFutureWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */