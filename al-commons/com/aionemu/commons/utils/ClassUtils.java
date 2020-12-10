/*     */ package com.aionemu.commons.utils;
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
/*     */ public class ClassUtils
/*     */ {
/*     */   public static boolean isSubclass(Class<?> a, Class<?> b) {
/*  42 */     if (a == b)
/*     */     {
/*  44 */       return true;
/*     */     }
/*  46 */     if (a == null || b == null)
/*     */     {
/*  48 */       return false;
/*     */     }
/*  50 */     for (Class<?> x = a; x != null; x = x.getSuperclass()) {
/*     */       
/*  52 */       if (x == b)
/*     */       {
/*  54 */         return true;
/*     */       }
/*  56 */       if (b.isInterface()) {
/*     */         
/*  58 */         Class[] interfaces = x.getInterfaces();
/*  59 */         for (Class<?> anInterface : interfaces) {
/*     */           
/*  61 */           if (isSubclass(anInterface, b))
/*     */           {
/*  63 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  68 */     return false;
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
/*     */   public static boolean isPackageMember(Class<?> clazz, String packageName) {
/*  82 */     return isPackageMember(clazz.getName(), packageName);
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
/*     */   public static boolean isPackageMember(String className, String packageName) {
/*  96 */     if (!className.contains("."))
/*     */     {
/*  98 */       return (packageName == null || packageName.isEmpty());
/*     */     }
/*     */ 
/*     */     
/* 102 */     String classPackage = className.substring(0, className.lastIndexOf('.'));
/* 103 */     return packageName.equals(classPackage);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\ClassUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */