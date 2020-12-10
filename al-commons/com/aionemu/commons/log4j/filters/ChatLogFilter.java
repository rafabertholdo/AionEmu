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
/*    */ 
/*    */ 
/*    */ public final class ChatLogFilter
/*    */   extends Filter
/*    */ {
/*    */   public final int decide(LoggingEvent loggingEvent) {
/* 37 */     Object message = loggingEvent.getMessage();
/*    */     
/* 39 */     if (((String)message).startsWith("[MESSAGE]"))
/*    */     {
/* 41 */       return 1;
/*    */     }
/*    */     
/* 44 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\filters\ChatLogFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */