/*     */ package com.aionemu.commons.services;
/*     */ 
/*     */ import com.aionemu.commons.log4j.JuliToLog4JHandler;
/*     */ import com.aionemu.commons.log4j.ThrowableAsMessageAwareFactory;
/*     */ import com.aionemu.commons.log4j.exceptions.Log4jInitializationError;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Field;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.util.logging.Handler;
/*     */ import java.util.logging.LogManager;
/*     */ import java.util.logging.Logger;
/*     */ import org.apache.log4j.Hierarchy;
/*     */ import org.apache.log4j.LogManager;
/*     */ import org.apache.log4j.xml.DOMConfigurator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoggingService
/*     */ {
/*     */   public static final String LOGGER_FACTORY_CLASS_PROPERTY = "log4j.loggerfactory";
/*     */   public static final String LOGGER_CONFIG_FILE = "config/log4j.xml";
/*     */   private static boolean initialized;
/*     */   
/*     */   public static void init() throws Log4jInitializationError {
/*  71 */     File f = new File("config/log4j.xml");
/*     */     
/*  73 */     if (!f.exists())
/*     */     {
/*  75 */       throw new Log4jInitializationError("Missing file " + f.getPath());
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/*  80 */       init(f.toURI().toURL());
/*     */     }
/*  82 */     catch (MalformedURLException e) {
/*     */       
/*  84 */       throw new Log4jInitializationError("Can't initalize logging", e);
/*     */     } 
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
/*     */   public static void init(URL url) throws Log4jInitializationError {
/*  98 */     synchronized (LoggingService.class) {
/*     */       
/* 100 */       if (initialized) {
/*     */         return;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 106 */       initialized = true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 112 */       DOMConfigurator.configure(url);
/*     */     }
/* 114 */     catch (Exception e) {
/*     */       
/* 116 */       throw new Log4jInitializationError("Can't initialize logging", e);
/*     */     } 
/*     */     
/* 119 */     overrideDefaultLoggerFactory();
/*     */ 
/*     */     
/* 122 */     Logger logger = LogManager.getLogManager().getLogger("");
/* 123 */     for (Handler h : logger.getHandlers())
/*     */     {
/* 125 */       logger.removeHandler(h);
/*     */     }
/*     */     
/* 128 */     logger.addHandler((Handler)new JuliToLog4JHandler());
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
/*     */ 
/*     */ 
/*     */   
/*     */   private static void overrideDefaultLoggerFactory() {
/* 144 */     Hierarchy lr = (Hierarchy)LogManager.getLoggerRepository();
/*     */     
/*     */     try {
/* 147 */       Field field = lr.getClass().getDeclaredField("defaultFactory");
/* 148 */       field.setAccessible(true);
/* 149 */       String cn = System.getProperty("log4j.loggerfactory", ThrowableAsMessageAwareFactory.class.getName());
/*     */       
/* 151 */       Class<?> c = Class.forName(cn);
/* 152 */       field.set(lr, c.newInstance());
/* 153 */       field.setAccessible(false);
/*     */     }
/* 155 */     catch (NoSuchFieldException e) {
/*     */ 
/*     */       
/* 158 */       e.printStackTrace();
/*     */     }
/* 160 */     catch (IllegalAccessException e) {
/*     */ 
/*     */       
/* 163 */       e.printStackTrace();
/*     */     }
/* 165 */     catch (ClassNotFoundException e) {
/*     */       
/* 167 */       throw new Log4jInitializationError("Can't found log4j logger factory class", e);
/*     */     }
/* 169 */     catch (InstantiationException e) {
/*     */       
/* 171 */       throw new Log4jInitializationError("Can't instantiate log4j logger factory", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\services\LoggingService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */