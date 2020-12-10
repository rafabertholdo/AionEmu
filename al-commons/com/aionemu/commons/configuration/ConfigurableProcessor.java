/*     */ package com.aionemu.commons.configuration;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Properties;
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
/*     */ public class ConfigurableProcessor
/*     */ {
/*  35 */   private static final Logger log = Logger.getLogger(ConfigurableProcessor.class);
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
/*     */   public static void process(Object object, Properties... properties) {
/*     */     Class<?> clazz;
/*  55 */     if (object instanceof Class) {
/*     */       
/*  57 */       clazz = (Class)object;
/*  58 */       object = null;
/*     */     }
/*     */     else {
/*     */       
/*  62 */       clazz = object.getClass();
/*     */     } 
/*     */     
/*  65 */     process(clazz, object, properties);
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
/*     */   private static void process(Class clazz, Object obj, Properties[] props) {
/*  82 */     processFields(clazz, obj, props);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  87 */     if (obj == null)
/*     */     {
/*  89 */       for (Class<?> itf : clazz.getInterfaces())
/*     */       {
/*  91 */         process(itf, obj, props);
/*     */       }
/*     */     }
/*     */     
/*  95 */     Class<Object> superClass = clazz.getSuperclass();
/*  96 */     if (superClass != null && superClass != Object.class)
/*     */     {
/*  98 */       process(superClass, obj, props);
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processFields(Class clazz, Object obj, Properties[] props) {
/* 116 */     for (Field f : clazz.getDeclaredFields()) {
/*     */ 
/*     */       
/* 119 */       if (!Modifier.isStatic(f.getModifiers()) || obj == null)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 125 */         if (Modifier.isStatic(f.getModifiers()) || obj != null)
/*     */         {
/*     */ 
/*     */ 
/*     */           
/* 130 */           if (f.isAnnotationPresent((Class)Property.class)) {
/*     */ 
/*     */             
/* 133 */             if (Modifier.isFinal(f.getModifiers())) {
/*     */               
/* 135 */               RuntimeException re = new RuntimeException("Attempt to proceed final field " + f.getName() + " of class " + clazz.getName());
/*     */               
/* 137 */               log.error(re);
/* 138 */               throw re;
/*     */             } 
/*     */ 
/*     */             
/* 142 */             processField(f, obj, props);
/*     */           } 
/*     */         }
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void processField(Field f, Object obj, Properties[] props) {
/* 164 */     boolean oldAccessible = f.isAccessible();
/* 165 */     f.setAccessible(true);
/*     */     
/*     */     try {
/* 168 */       Property property = f.<Property>getAnnotation(Property.class);
/* 169 */       if (!"DO_NOT_OVERWRITE_INITIALIAZION_VALUE".equals(property.defaultValue()) || isKeyPresent(property.key(), props))
/*     */       {
/* 171 */         f.set(obj, getFieldValue(f, props));
/*     */       }
/* 173 */       else if (log.isDebugEnabled())
/*     */       {
/* 175 */         log.debug("Field " + f.getName() + " of class " + f.getDeclaringClass().getName() + " wasn't modified");
/*     */       }
/*     */     
/* 178 */     } catch (Exception e) {
/*     */       
/* 180 */       RuntimeException re = new RuntimeException("Can't transform field " + f.getName() + " of class " + f.getDeclaringClass(), e);
/*     */       
/* 182 */       log.error(re);
/* 183 */       throw re;
/*     */     } 
/* 185 */     f.setAccessible(oldAccessible);
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
/*     */   private static Object getFieldValue(Field field, Properties[] props) throws TransformationException {
/* 204 */     Property property = field.<Property>getAnnotation(Property.class);
/* 205 */     String defaultValue = property.defaultValue();
/* 206 */     String key = property.key();
/* 207 */     String value = null;
/*     */     
/* 209 */     if (key.isEmpty()) {
/*     */       
/* 211 */       log.warn("Property " + field.getName() + " of class " + field.getDeclaringClass().getName() + " has empty key");
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 216 */       value = findPropertyByKey(key, props);
/*     */     } 
/*     */     
/* 219 */     if (value == null) {
/*     */       
/* 221 */       value = defaultValue;
/* 222 */       log.warn("Using default value for field " + field.getName() + " of class " + field.getDeclaringClass().getName());
/*     */     } 
/*     */ 
/*     */     
/* 226 */     PropertyTransformer pt = PropertyTransformerFactory.newTransformer(field.getType(), property.propertyTransformer());
/*     */     
/* 228 */     return pt.transform(value, field);
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
/*     */   private static String findPropertyByKey(String key, Properties[] props) {
/* 242 */     for (Properties p : props) {
/*     */       
/* 244 */       if (p.containsKey(key))
/*     */       {
/* 246 */         return p.getProperty(key);
/*     */       }
/*     */     } 
/*     */     
/* 250 */     return null;
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
/*     */   private static boolean isKeyPresent(String key, Properties[] props) {
/* 264 */     return (findPropertyByKey(key, props) != null);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\ConfigurableProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */