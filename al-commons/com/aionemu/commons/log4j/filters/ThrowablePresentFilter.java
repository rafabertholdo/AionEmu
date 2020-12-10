/*    */ package com.aionemu.commons.log4j.filters;
/*    */ 
/*    */ import org.apache.log4j.spi.Filter;
/*    */ import org.apache.log4j.spi.LoggingEvent;
/*    */ import org.apache.log4j.spi.ThrowableInformation;
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
/*    */ public class ThrowablePresentFilter
/*    */   extends Filter
/*    */ {
/*    */   public int decide(LoggingEvent loggingEvent) {
/* 43 */     Object message = loggingEvent.getMessage();
/*    */     
/* 45 */     if (message instanceof Throwable)
/*    */     {
/* 47 */       return 1;
/*    */     }
/*    */     
/* 50 */     ThrowableInformation information = loggingEvent.getThrowableInformation();
/*    */ 
/*    */     
/* 53 */     if (information != null && information.getThrowable() != null)
/*    */     {
/* 55 */       return 1;
/*    */     }
/*    */     
/* 58 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\filters\ThrowablePresentFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */