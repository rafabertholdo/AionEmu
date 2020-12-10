/*    */ package com.aionemu.gameserver.taskmanager;
/*    */ 
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import java.util.concurrent.locks.ReentrantLock;
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
/*    */ public abstract class FIFOExecutableQueue
/*    */   implements Runnable
/*    */ {
/*    */   private static final byte NONE = 0;
/*    */   private static final byte QUEUED = 1;
/*    */   private static final byte RUNNING = 2;
/* 30 */   private final ReentrantLock lock = new ReentrantLock();
/*    */   
/* 32 */   private volatile byte state = 0;
/*    */ 
/*    */   
/*    */   protected final void execute() {
/* 36 */     lock();
/*    */     
/*    */     try {
/* 39 */       if (this.state != 0) {
/*    */         return;
/*    */       }
/* 42 */       this.state = 1;
/*    */     }
/*    */     finally {
/*    */       
/* 46 */       unlock();
/*    */     } 
/*    */     
/* 49 */     ThreadPoolManager.getInstance().execute(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public final void lock() {
/* 54 */     this.lock.lock();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void unlock() {
/* 59 */     this.lock.unlock();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void run() {
/*    */     try {
/* 66 */       while (!isEmpty()) {
/*    */         
/* 68 */         setState((byte)1, (byte)2);
/*    */ 
/*    */         
/*    */         try {
/* 72 */           while (!isEmpty()) {
/* 73 */             removeAndExecuteFirst();
/*    */           }
/*    */         } finally {
/*    */           
/* 77 */           setState((byte)2, (byte)1);
/*    */         }
/*    */       
/*    */       } 
/*    */     } finally {
/*    */       
/* 83 */       setState((byte)1, (byte)0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void setState(byte expected, byte value) {
/* 89 */     lock();
/*    */     
/*    */     try {
/* 92 */       if (this.state != expected) {
/* 93 */         throw new IllegalStateException("state: " + this.state + ", expected: " + expected);
/*    */       }
/*    */     } finally {
/*    */       
/* 97 */       this.state = value;
/*    */       
/* 99 */       unlock();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract boolean isEmpty();
/*    */   
/*    */   protected abstract void removeAndExecuteFirst();
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\taskmanager\FIFOExecutableQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */