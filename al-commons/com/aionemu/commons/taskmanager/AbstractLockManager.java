/*    */ package com.aionemu.commons.taskmanager;
/*    */ 
/*    */ import java.util.concurrent.locks.ReentrantReadWriteLock;
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
/*    */ 
/*    */ public abstract class AbstractLockManager
/*    */ {
/* 27 */   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
/*    */   
/* 29 */   private final ReentrantReadWriteLock.WriteLock writeLock = this.lock.writeLock();
/* 30 */   private final ReentrantReadWriteLock.ReadLock readLock = this.lock.readLock();
/*    */ 
/*    */   
/*    */   public final void writeLock() {
/* 34 */     this.writeLock.lock();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void writeUnlock() {
/* 39 */     this.writeLock.unlock();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void readLock() {
/* 44 */     this.readLock.lock();
/*    */   }
/*    */ 
/*    */   
/*    */   public final void readUnlock() {
/* 49 */     this.readLock.unlock();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\taskmanager\AbstractLockManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */