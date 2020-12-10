/*    */ package com.aionemu.gameserver.taskmanager;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import javolution.util.FastList;
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
/*    */ public abstract class FIFOSimpleExecutableQueue<T>
/*    */   extends FIFOExecutableQueue
/*    */ {
/* 26 */   private final FastList<T> queue = new FastList();
/*    */ 
/*    */   
/*    */   public final void execute(T t) {
/* 30 */     synchronized (this.queue) {
/*    */       
/* 32 */       this.queue.addLast(t);
/*    */     } 
/*    */     
/* 35 */     execute();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void executeAll(Collection<T> c) {
/* 40 */     synchronized (this.queue) {
/*    */       
/* 42 */       this.queue.addAll(c);
/*    */     } 
/*    */     
/* 45 */     execute();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void remove(T t) {
/* 50 */     synchronized (this.queue) {
/*    */       
/* 52 */       this.queue.remove(t);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected final boolean isEmpty() {
/* 59 */     synchronized (this.queue) {
/*    */       
/* 61 */       return this.queue.isEmpty();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected final T removeFirst() {
/* 67 */     synchronized (this.queue) {
/*    */       
/* 69 */       return (T)this.queue.removeFirst();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void removeAndExecuteFirst();
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\FIFOSimpleExecutableQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */