/*     */ package com.aionemu.commons.database.dao;
/*     */ 
/*     */ import com.aionemu.commons.database.DatabaseConfig;
/*     */ import com.aionemu.commons.database.DatabaseFactory;
/*     */ import com.aionemu.commons.scripting.scriptmanager.ScriptManager;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class DAOManager
/*     */ {
/*  41 */   private static final Logger log = Logger.getLogger(DAOManager.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  46 */   private static final Map<String, DAO> daoMap = new HashMap<String, DAO>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ScriptManager scriptManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void init() {
/*     */     try {
/*  60 */       scriptManager = new ScriptManager();
/*  61 */       scriptManager.setGlobalClassListener(new DAOLoader());
/*  62 */       scriptManager.load(DatabaseConfig.DATABASE_SCRIPTCONTEXT_DESCRIPTOR);
/*     */     }
/*  64 */     catch (Exception e) {
/*     */       
/*  66 */       throw new Error("Can't load database script context: " + DatabaseConfig.DATABASE_SCRIPTCONTEXT_DESCRIPTOR, e);
/*     */     } 
/*     */ 
/*     */     
/*  70 */     log.info("Loaded " + daoMap.size() + " DAO implementations.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void shutdown() {
/*  78 */     scriptManager.shutdown();
/*  79 */     daoMap.clear();
/*  80 */     scriptManager = null;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T extends DAO> T getDAO(Class<T> clazz) throws DAONotFoundException {
/* 102 */     DAO result = daoMap.get(clazz.getName());
/*     */     
/* 104 */     if (result == null) {
/*     */       
/* 106 */       String s = "DAO for class " + clazz.getName() + " not implemented";
/* 107 */       log.error(s);
/* 108 */       throw new DAONotFoundException(s);
/*     */     } 
/*     */     
/* 111 */     return (T)result;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerDAO(Class<? extends DAO> daoClass) throws DAOAlreadyRegisteredException, IllegalAccessException, InstantiationException {
/* 134 */     DAO dao = daoClass.newInstance();
/*     */     
/* 136 */     if (!dao.supports(DatabaseFactory.getDatabaseName(), DatabaseFactory.getDatabaseMajorVersion(), DatabaseFactory.getDatabaseMinorVersion())) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 141 */     synchronized (DAOManager.class) {
/*     */       
/* 143 */       DAO oldDao = daoMap.get(dao.getClassName());
/* 144 */       if (oldDao != null) {
/*     */         
/* 146 */         StringBuilder sb = new StringBuilder();
/* 147 */         sb.append("DAO with className ").append(dao.getClassName()).append(" is used by ");
/* 148 */         sb.append(oldDao.getClass().getName()).append(". Can't override with ");
/* 149 */         sb.append(daoClass.getName()).append(".");
/* 150 */         String s = sb.toString();
/* 151 */         log.error(s);
/* 152 */         throw new DAOAlreadyRegisteredException(s);
/*     */       } 
/*     */ 
/*     */       
/* 156 */       daoMap.put(dao.getClassName(), dao);
/*     */     } 
/*     */ 
/*     */     
/* 160 */     if (log.isDebugEnabled()) {
/* 161 */       log.debug("DAO " + dao.getClassName() + " was successfuly registered.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void unregisterDAO(Class<? extends DAO> daoClass) {
/* 172 */     synchronized (DAOManager.class) {
/*     */       
/* 174 */       for (DAO dao : daoMap.values()) {
/*     */         
/* 176 */         if (dao.getClass() == daoClass) {
/*     */           
/* 178 */           daoMap.remove(dao.getClassName());
/*     */           
/* 180 */           if (log.isDebugEnabled())
/* 181 */             log.debug("DAO " + dao.getClassName() + " was successfuly unregistered."); 
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\dao\DAOManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */