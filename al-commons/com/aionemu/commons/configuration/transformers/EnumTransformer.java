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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EnumTransformer
/*    */   implements PropertyTransformer<Enum<?>>
/*    */ {
/* 43 */   public static final EnumTransformer SHARED_INSTANCE = new EnumTransformer();
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
/*    */   public Enum<?> transform(String value, Field field) throws TransformationException {
/* 60 */     Class<? extends Enum> clazz = (Class)field.getType();
/*    */ 
/*    */     
/*    */     try {
/* 64 */       return Enum.valueOf((Class)clazz, value);
/*    */     }
/* 66 */     catch (Exception e) {
/*    */       
/* 68 */       throw new TransformationException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\EnumTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */