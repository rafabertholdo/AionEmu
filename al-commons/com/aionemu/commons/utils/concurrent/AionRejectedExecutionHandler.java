/*    */ package com.aionemu.commons.utils.concurrent;
/*    */ 
/*    */ import java.util.concurrent.RejectedExecutionException;
/*    */ import java.util.concurrent.RejectedExecutionHandler;
/*    */ import java.util.concurrent.ThreadPoolExecutor;
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
/*    */ public final class AionRejectedExecutionHandler
/*    */   implements RejectedExecutionHandler
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(AionRejectedExecutionHandler.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
/* 35 */     if (executor.isShutdown()) {
/*    */       return;
/*    */     }
/* 38 */     log.warn(r + " from " + executor, new RejectedExecutionException());
/*    */     
/* 40 */     if (Thread.currentThread().getPriority() > 5) {
/* 41 */       (new Thread(r)).start();
/*    */     } else {
/* 43 */       r.run();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\concurrent\AionRejectedExecutionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */