/*     */ package com.aionemu.commons.configuration;
/*     */ 
/*     */ import com.aionemu.commons.configuration.transformers.BooleanTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.ByteTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.CharTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.ClassTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.DoubleTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.EnumTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.FileTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.FloatTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.InetSocketAddressTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.IntegerTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.LongTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.PatternTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.ShortTransformer;
/*     */ import com.aionemu.commons.configuration.transformers.StringTransformer;
/*     */ import com.aionemu.commons.utils.ClassUtils;
/*     */ import java.io.File;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.regex.Pattern;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PropertyTransformerFactory
/*     */ {
/*     */   public static PropertyTransformer newTransformer(Class<Boolean> clazzToTransform, Class<? extends PropertyTransformer> tc) throws TransformationException {
/*  65 */     if (tc == PropertyTransformer.class)
/*     */     {
/*  67 */       tc = null;
/*     */     }
/*     */     
/*  70 */     if (tc != null) {
/*     */       
/*     */       try {
/*     */         
/*  74 */         return tc.newInstance();
/*     */       }
/*  76 */       catch (Exception e) {
/*     */         
/*  78 */         throw new TransformationException("Can't instantiate property transfromer", e);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*  83 */     if (clazzToTransform == Boolean.class || clazzToTransform == boolean.class)
/*     */     {
/*  85 */       return (PropertyTransformer)BooleanTransformer.SHARED_INSTANCE;
/*     */     }
/*  87 */     if (clazzToTransform == Byte.class || clazzToTransform == byte.class)
/*     */     {
/*  89 */       return (PropertyTransformer)ByteTransformer.SHARED_INSTANCE;
/*     */     }
/*  91 */     if (clazzToTransform == Character.class || clazzToTransform == char.class)
/*     */     {
/*  93 */       return (PropertyTransformer)CharTransformer.SHARED_INSTANCE;
/*     */     }
/*  95 */     if (clazzToTransform == Double.class || clazzToTransform == double.class)
/*     */     {
/*  97 */       return (PropertyTransformer)DoubleTransformer.SHARED_INSTANCE;
/*     */     }
/*  99 */     if (clazzToTransform == Float.class || clazzToTransform == float.class)
/*     */     {
/* 101 */       return (PropertyTransformer)FloatTransformer.SHARED_INSTANCE;
/*     */     }
/* 103 */     if (clazzToTransform == Integer.class || clazzToTransform == int.class)
/*     */     {
/* 105 */       return (PropertyTransformer)IntegerTransformer.SHARED_INSTANCE;
/*     */     }
/* 107 */     if (clazzToTransform == Long.class || clazzToTransform == long.class)
/*     */     {
/* 109 */       return (PropertyTransformer)LongTransformer.SHARED_INSTANCE;
/*     */     }
/* 111 */     if (clazzToTransform == Short.class || clazzToTransform == short.class)
/*     */     {
/* 113 */       return (PropertyTransformer)ShortTransformer.SHARED_INSTANCE;
/*     */     }
/* 115 */     if (clazzToTransform == String.class)
/*     */     {
/* 117 */       return (PropertyTransformer)StringTransformer.SHARED_INSTANCE;
/*     */     }
/* 119 */     if (clazzToTransform.isEnum())
/*     */     {
/* 121 */       return (PropertyTransformer)EnumTransformer.SHARED_INSTANCE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     if (clazzToTransform == File.class)
/*     */     {
/* 131 */       return (PropertyTransformer)FileTransformer.SHARED_INSTANCE;
/*     */     }
/* 133 */     if (ClassUtils.isSubclass(clazzToTransform, InetSocketAddress.class))
/*     */     {
/* 135 */       return (PropertyTransformer)InetSocketAddressTransformer.SHARED_INSTANCE;
/*     */     }
/* 137 */     if (clazzToTransform == Pattern.class)
/*     */     {
/* 139 */       return (PropertyTransformer)PatternTransformer.SHARED_INSTANCE;
/*     */     }
/* 141 */     if (clazzToTransform == Class.class)
/*     */     {
/* 143 */       return (PropertyTransformer)ClassTransformer.SHARED_INSTANCE;
/*     */     }
/*     */ 
/*     */     
/* 147 */     throw new TransformationException("Transformer not found for class " + clazzToTransform.getName());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\PropertyTransformerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */