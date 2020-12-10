/*    */ package com.aionemu.commons.configuration.transformers;
/*    */ 
/*    */ import com.aionemu.commons.configuration.PropertyTransformer;
/*    */ import com.aionemu.commons.configuration.TransformationException;
/*    */ import java.lang.reflect.Field;
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
/*    */ public class ClassTransformer
/*    */   implements PropertyTransformer<Class<?>>
/*    */ {
/* 37 */   public static final ClassTransformer SHARED_INSTANCE = new ClassTransformer();
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Class<?> transform(String value, Field field) throws TransformationException {
/*    */     try {
/* 44 */       return Class.forName(value, false, getClass().getClassLoader());
/*    */     }
/* 46 */     catch (ClassNotFoundException e) {
/*    */       
/* 48 */       throw new TransformationException("Cannot find class with name '" + value + "'");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\ClassTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */