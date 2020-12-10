/*    */ package com.aionemu.commons.log4j.filters;
/*    */ 
/*    */ import org.apache.log4j.spi.Filter;
/*    */ import org.apache.log4j.spi.LoggingEvent;
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
/*    */ public final class UnknownPacketsFilter
/*    */   extends Filter
/*    */ {
/*    */   public final int decide(LoggingEvent loggingEvent) {
/* 35 */     Object message = loggingEvent.getMessage();
/*    */     
/* 37 */     if (((String)message).startsWith("[UNKNOWN PACKET]"))
/*    */     {
/* 39 */       return 1;
/*    */     }
/*    */     
/* 42 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\filters\UnknownPacketsFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */