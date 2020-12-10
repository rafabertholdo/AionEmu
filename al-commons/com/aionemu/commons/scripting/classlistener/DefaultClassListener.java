/*    */ package com.aionemu.commons.scripting.classlistener;
/*    */ 
/*    */ import com.aionemu.commons.scripting.metadata.OnClassLoad;
/*    */ import com.aionemu.commons.scripting.metadata.OnClassUnload;
/*    */ import java.lang.annotation.Annotation;
/*    */ import java.lang.reflect.InvocationTargetException;
/*    */ import java.lang.reflect.Method;
/*    */ import java.lang.reflect.Modifier;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class DefaultClassListener
/*    */   implements ClassListener
/*    */ {
/* 37 */   private static final Logger log = Logger.getLogger(DefaultClassListener.class);
/*    */ 
/*    */ 
/*    */   
/*    */   public void postLoad(Class<?>[] classes) {
/* 42 */     for (Class<?> c : classes)
/*    */     {
/* 44 */       doMethodInvoke(c.getDeclaredMethods(), (Class)OnClassLoad.class);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void preUnload(Class<?>[] classes) {
/* 51 */     for (Class<?> c : classes)
/*    */     {
/* 53 */       doMethodInvoke(c.getDeclaredMethods(), (Class)OnClassUnload.class);
/*    */     }
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
/*    */   protected final void doMethodInvoke(Method[] methods, Class<? extends Annotation> annotationClass) {
/* 67 */     for (Method m : methods) {
/*    */       
/* 69 */       if (Modifier.isStatic(m.getModifiers())) {
/*    */ 
/*    */         
/* 72 */         boolean accessible = m.isAccessible();
/* 73 */         m.setAccessible(true);
/*    */         
/* 75 */         if (m.getAnnotation(annotationClass) != null) {
/*    */           
/*    */           try {
/*    */             
/* 79 */             m.invoke((Object)null, new Object[0]);
/*    */           }
/* 81 */           catch (IllegalAccessException e) {
/*    */             
/* 83 */             log.error("Can't access method " + m.getName() + " of class " + m.getDeclaringClass().getName(), e);
/*    */           }
/* 85 */           catch (InvocationTargetException e) {
/*    */             
/* 87 */             log.error("Can't invoke method " + m.getName() + " of class " + m.getDeclaringClass().getName(), e);
/*    */           } 
/*    */         }
/*    */         
/* 91 */         m.setAccessible(accessible);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\scripting\classlistener\DefaultClassListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */