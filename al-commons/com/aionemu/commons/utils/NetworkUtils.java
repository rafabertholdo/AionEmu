/*    */ package com.aionemu.commons.utils;
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
/*    */ public class NetworkUtils
/*    */ {
/*    */   public static boolean checkIPMatching(String pattern, String address) {
/* 39 */     if (pattern.equals("*.*.*.*") || pattern.equals("*")) {
/* 40 */       return true;
/*    */     }
/* 42 */     String[] mask = pattern.split("\\.");
/* 43 */     String[] ip_address = address.split("\\.");
/* 44 */     for (int i = 0; i < mask.length; i++) {
/*    */       
/* 46 */       if (!mask[i].equals("*") && !mask[i].equals(ip_address[i]))
/*    */       {
/* 48 */         if (mask[i].contains("-")) {
/*    */           
/* 50 */           byte min = Byte.parseByte(mask[i].split("-")[0]);
/* 51 */           byte max = Byte.parseByte(mask[i].split("-")[1]);
/* 52 */           byte ip = Byte.parseByte(ip_address[i]);
/* 53 */           if (ip < min || ip > max) {
/* 54 */             return false;
/*    */           }
/*    */         } else {
/* 57 */           return false;
/*    */         }  } 
/* 59 */     }  return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\NetworkUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */