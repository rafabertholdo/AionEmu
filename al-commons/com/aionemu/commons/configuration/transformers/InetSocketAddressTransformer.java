/*    */ package com.aionemu.commons.configuration.transformers;
/*    */ 
/*    */ import com.aionemu.commons.configuration.PropertyTransformer;
/*    */ import com.aionemu.commons.configuration.TransformationException;
/*    */ import java.lang.reflect.Field;
/*    */ import java.net.InetAddress;
/*    */ import java.net.InetSocketAddress;
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
/*    */ public class InetSocketAddressTransformer
/*    */   implements PropertyTransformer<InetSocketAddress>
/*    */ {
/* 40 */   public static final InetSocketAddressTransformer SHARED_INSTANCE = new InetSocketAddressTransformer();
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
/*    */   public InetSocketAddress transform(String value, Field field) throws TransformationException {
/* 56 */     String[] parts = value.split(":");
/*    */     
/* 58 */     if (parts.length != 2)
/*    */     {
/* 60 */       throw new TransformationException("Can't transform property, must be in format \"address:port\"");
/*    */     }
/*    */ 
/*    */     
/*    */     try {
/* 65 */       if ("*".equals(parts[0]))
/*    */       {
/* 67 */         return new InetSocketAddress(Integer.parseInt(parts[1]));
/*    */       }
/*    */ 
/*    */       
/* 71 */       InetAddress address = InetAddress.getByName(parts[0]);
/* 72 */       int port = Integer.parseInt(parts[1]);
/* 73 */       return new InetSocketAddress(address, port);
/*    */     
/*    */     }
/* 76 */     catch (Exception e) {
/*    */       
/* 78 */       throw new TransformationException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\configuration\transformers\InetSocketAddressTransformer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */