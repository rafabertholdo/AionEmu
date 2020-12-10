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
/*    */ public class BooleanTransformer
/*    */   implements PropertyTransformer<Boolean>
/*    */ {
/* 37 */   public static final BooleanTransformer SHARED_INSTANCE = new BooleanTransformer();
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
/*    */   public Boolean transform(String value, Field field) throws TransformationException {
/* 56 */     if ("true".equalsIgnoreCase(value) || "1".equals(value))
/*    */     {
/* 58 */       return Boolean.valueOf(true);
/*    */     }
/* 60 */     if ("false".equalsIgnoreCase(value) || "0".equals(value))
/*    */     {
/* 62 */       return Boolean.valueOf(false);
/*    */     }
/*    */ 
/*    */     
/* 66 */     throw new TransformationException("Invalid boolean string: " + value);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\BooleanTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */