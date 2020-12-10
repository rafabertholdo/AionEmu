/*     */ package com.aionemu.commons.log4j;
/*     */ 
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.LogRecord;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.apache.log4j.helpers.LogLog;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JuliToLog4JHandler
/*     */   extends Handler
/*     */ {
/*     */   public void publish(LogRecord record) {
/*  58 */     String loggerName = record.getLoggerName();
/*  59 */     if (loggerName == null)
/*     */     {
/*  61 */       loggerName = "";
/*     */     }
/*     */     
/*  64 */     Logger log = Logger.getLogger(loggerName);
/*  65 */     Level level = toLog4jLevel(record.getLevel());
/*     */     
/*  67 */     log.log(Logger.class.getName(), (Priority)level, record.getMessage(), record.getThrown());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Level toLog4jLevel(Level juliLevel) {
/*  80 */     if (Level.OFF.equals(juliLevel))
/*     */     {
/*  82 */       return Level.OFF;
/*     */     }
/*  84 */     if (Level.SEVERE.equals(juliLevel))
/*     */     {
/*  86 */       return Level.ERROR;
/*     */     }
/*  88 */     if (Level.WARNING.equals(juliLevel))
/*     */     {
/*  90 */       return Level.WARN;
/*     */     }
/*  92 */     if (Level.INFO.equals(juliLevel))
/*     */     {
/*  94 */       return Level.INFO;
/*     */     }
/*  96 */     if (Level.CONFIG.equals(juliLevel) || Level.FINE.equals(juliLevel))
/*     */     {
/*  98 */       return Level.DEBUG;
/*     */     }
/* 100 */     if (Level.FINER.equals(juliLevel) || Level.FINEST.equals(juliLevel))
/*     */     {
/* 102 */       return Level.TRACE;
/*     */     }
/* 104 */     if (Level.ALL.equals(juliLevel))
/*     */     {
/* 106 */       return Level.ALL;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 111 */     LogLog.warn("Warning: usage of custom JULI level: " + juliLevel.getName(), new Exception());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return new CustomLog4jLevel(juliLevel.intValue(), juliLevel.getName(), juliLevel.intValue());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws SecurityException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static class CustomLog4jLevel
/*     */     extends Level
/*     */   {
/*     */     private static final long serialVersionUID = 4014557380173323844L;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected CustomLog4jLevel(int level, String levelStr, int syslogEquivalent) {
/* 166 */       super(level, levelStr, syslogEquivalent);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\log4j\JuliToLog4JHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */