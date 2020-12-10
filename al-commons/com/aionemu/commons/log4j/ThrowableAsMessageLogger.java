/*    */ package com.aionemu.commons.log4j;
/*    */ 
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThrowableAsMessageLogger
/*    */   extends Logger
/*    */ {
/*    */   protected ThrowableAsMessageLogger(String name) {
/* 45 */     super(name);
/*    */   }
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
/*    */   protected void forcedLog(String fqcn, Priority level, Object message, Throwable t) {
/* 65 */     if (message instanceof Throwable && t == null) {
/*    */       
/* 67 */       t = (Throwable)message;
/* 68 */       message = t.getLocalizedMessage();
/*    */     } 
/*    */     
/* 71 */     super.forcedLog(fqcn, level, message, t);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\ThrowableAsMessageLogger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */