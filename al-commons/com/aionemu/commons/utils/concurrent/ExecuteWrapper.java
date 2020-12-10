/*    */ package com.aionemu.commons.utils.concurrent;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import javolution.text.TextBuilder;
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
/*    */ 
/*    */ public class ExecuteWrapper
/*    */   implements Runnable
/*    */ {
/* 30 */   private static final Logger log = Logger.getLogger(ExecuteWrapper.class);
/*    */   
/*    */   private final Runnable runnable;
/*    */ 
/*    */   
/*    */   public ExecuteWrapper(Runnable runnable) {
/* 36 */     this.runnable = runnable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public final void run() {
/* 42 */     execute(this.runnable, getMaximumRuntimeInMillisecWithoutWarning());
/*    */   }
/*    */ 
/*    */   
/*    */   protected long getMaximumRuntimeInMillisecWithoutWarning() {
/* 47 */     return Long.MAX_VALUE;
/*    */   }
/*    */ 
/*    */   
/*    */   public static void execute(Runnable runnable) {
/* 52 */     execute(runnable, Long.MAX_VALUE);
/*    */   }
/*    */ 
/*    */   
/*    */   public static void execute(Runnable runnable, long maximumRuntimeInMillisecWithoutWarning) {
/* 57 */     long begin = System.nanoTime();
/*    */ 
/*    */     
/*    */     try {
/* 61 */       runnable.run();
/*    */     }
/* 63 */     catch (RuntimeException e) {
/*    */       
/* 65 */       log.warn("Exception in a Runnable execution:", e);
/*    */     }
/*    */     finally {
/*    */       
/* 69 */       long runtimeInNanosec = System.nanoTime() - begin;
/* 70 */       Class<? extends Runnable> clazz = (Class)runnable.getClass();
/*    */       
/* 72 */       RunnableStatsManager.handleStats(clazz, runtimeInNanosec);
/*    */       
/* 74 */       long runtimeInMillisec = TimeUnit.NANOSECONDS.toMillis(runtimeInNanosec);
/*    */       
/* 76 */       if (runtimeInMillisec > maximumRuntimeInMillisecWithoutWarning) {
/*    */         
/* 78 */         TextBuilder tb = TextBuilder.newInstance();
/*    */         
/* 80 */         tb.append(clazz);
/* 81 */         tb.append(" - execution time: ");
/* 82 */         tb.append(runtimeInMillisec);
/* 83 */         tb.append("msec");
/*    */         
/* 85 */         log.warn(tb.toString());
/*    */         
/* 87 */         TextBuilder.recycle(tb);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\concurrent\ExecuteWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */