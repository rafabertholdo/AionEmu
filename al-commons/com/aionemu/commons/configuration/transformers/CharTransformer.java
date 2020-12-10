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
/*    */ public class CharTransformer
/*    */   implements PropertyTransformer<Character>
/*    */ {
/* 32 */   public static final CharTransformer SHARED_INSTANCE = new CharTransformer();
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
/*    */   public Character transform(String value, Field field) throws TransformationException {
/*    */     try {
/* 50 */       char[] chars = value.toCharArray();
/* 51 */       if (chars.length > 1)
/*    */       {
/* 53 */         throw new TransformationException("To many characters in the value");
/*    */       }
/*    */       
/* 56 */       return Character.valueOf(chars[0]);
/*    */     }
/* 58 */     catch (Exception e) {
/*    */       
/* 60 */       throw new TransformationException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\CharTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */