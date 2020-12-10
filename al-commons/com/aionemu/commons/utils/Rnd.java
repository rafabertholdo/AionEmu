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
/*    */ public class Rnd
/*    */ {
/* 25 */   private static final MTRandom rnd = new MTRandom();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static float get() {
/* 33 */     return rnd.nextFloat();
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
/*    */   public static int get(int n) {
/* 45 */     return (int)Math.floor(rnd.nextDouble() * n);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int get(int min, int max) {
/* 56 */     return min + (int)Math.floor(rnd.nextDouble() * (max - min + 1));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int nextInt(int n) {
/* 65 */     return (int)Math.floor(rnd.nextDouble() * n);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int nextInt() {
/* 73 */     return rnd.nextInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double nextDouble() {
/* 81 */     return rnd.nextDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static double nextGaussian() {
/* 89 */     return rnd.nextGaussian();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean nextBoolean() {
/* 97 */     return rnd.nextBoolean();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\common\\utils\Rnd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */