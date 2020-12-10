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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public final class GmAuditFilter
/*    */   extends Filter
/*    */ {
/*    */   public final int decide(LoggingEvent loggingEvent) {
/* 42 */     Object message = loggingEvent.getMessage();
/*    */     
/* 44 */     if (((String)message).startsWith("[ADMIN COMMAND]"))
/*    */     {
/* 46 */       return 1;
/*    */     }
/*    */     
/* 49 */     return -1;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\filters\GmAuditFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */