/*     */ package com.aionemu.commons.database.dao;
/*     */ 
/*     */ import com.aionemu.commons.scripting.classlistener.ClassListener;
/*     */ import com.aionemu.commons.scripting.classlistener.DefaultClassListener;
/*     */ import com.aionemu.commons.utils.ClassUtils;
/*     */ import java.lang.reflect.Modifier;
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
/*     */ class DAOLoader
/*     */   extends DefaultClassListener
/*     */   implements ClassListener
/*     */ {
/*     */   public void postLoad(Class<?>[] classes) {
/*  38 */     for (Class<?> clazz : classes) {
/*     */       
/*  40 */       if (isValidDAO(clazz)) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  45 */           DAOManager.registerDAO((Class)clazz);
/*     */         }
/*  47 */         catch (Exception e) {
/*     */           
/*  49 */           throw new Error("Can't register DAO class", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  54 */     super.postLoad(classes);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void preUnload(Class<?>[] classes) {
/*  62 */     super.postLoad(classes);
/*     */ 
/*     */     
/*  65 */     for (Class<?> clazz : classes) {
/*     */       
/*  67 */       if (isValidDAO(clazz)) {
/*     */         
/*     */         try {
/*     */ 
/*     */           
/*  72 */           DAOManager.unregisterDAO((Class)clazz);
/*     */         }
/*  74 */         catch (Exception e) {
/*     */           
/*  76 */           throw new Error("Can't unregister DAO class", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValidDAO(Class<?> clazz) {
/*  87 */     if (!ClassUtils.isSubclass(clazz, DAO.class)) {
/*  88 */       return false;
/*     */     }
/*  90 */     int modifiers = clazz.getModifiers();
/*     */     
/*  92 */     if (Modifier.isAbstract(modifiers) || Modifier.isInterface(modifiers)) {
/*  93 */       return false;
/*     */     }
/*  95 */     if (!Modifier.isPublic(modifiers)) {
/*  96 */       return false;
/*     */     }
/*  98 */     if (clazz.isAnnotationPresent((Class)DisabledDAO.class)) {
/*  99 */       return false;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\database\dao\DAOLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */