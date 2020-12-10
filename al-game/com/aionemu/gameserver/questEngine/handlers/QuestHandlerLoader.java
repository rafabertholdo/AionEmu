/*     */ package com.aionemu.gameserver.questEngine.handlers;
/*     */ 
/*     */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*     */ import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
/*     */ import com.aionemu.commons.utils.ClassUtils;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ public class QuestHandlerLoader
/*     */   extends DefaultClassListener
/*     */   implements ClassListener
/*     */ {
/*  34 */   private static final Logger logger = Logger.getLogger(QuestHandlerLoader.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void postLoad(Class<?>[] classes) {
/*  45 */     for (Class<?> c : classes) {
/*     */       
/*  47 */       if (logger.isDebugEnabled()) {
/*  48 */         logger.debug("Load class " + c.getName());
/*     */       }
/*  50 */       if (isValidClass(c))
/*     */       {
/*     */         
/*  53 */         if (ClassUtils.isSubclass(c, QuestHandler.class)) {
/*     */           
/*     */           try {
/*     */             
/*  57 */             Class<? extends QuestHandler> tmp = (Class)c;
/*  58 */             if (tmp != null) {
/*  59 */               QuestEngine.getInstance().addQuestHandler(tmp.newInstance());
/*     */             }
/*  61 */           } catch (InstantiationException e) {
/*     */ 
/*     */             
/*  64 */             e.printStackTrace();
/*     */           }
/*  66 */           catch (IllegalAccessException e) {
/*     */ 
/*     */             
/*  69 */             e.printStackTrace();
/*     */           } 
/*     */         }
/*     */       }
/*     */     } 
/*     */     
/*  75 */     super.postLoad(classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preUnload(Class<?>[] classes) {
/*  82 */     if (logger.isDebugEnabled()) {
/*  83 */       for (Class<?> c : classes) {
/*  84 */         logger.debug("Unload class " + c.getName());
/*     */       }
/*     */     }
/*  87 */     super.preUnload(classes);
/*     */     
/*  89 */     QuestEngine.getInstance().clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidClass(Class<?> clazz) {
/*  94 */     int modifiers = clazz.getModifiers();
/*     */     
/*  96 */     if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
/*  97 */       return false;
/*     */     }
/*  99 */     if (!Modifier.isPublic(modifiers)) {
/* 100 */       return false;
/*     */     }
/* 102 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\QuestHandlerLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */