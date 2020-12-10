/*    */ package com.aionemu.gameserver.utils.stats;
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
/*    */ public enum XPLossEnum
/*    */ {
/* 25 */   LEVEL_8(8, 2.997997521D),
/* 26 */   LEVEL_9(9, 2.998974359D),
/* 27 */   LEVEL_10(10, 2.999872482D),
/* 28 */   LEVEL_16(16, 2.999258215D),
/* 29 */   LEVEL_20(20, 2.999859021D),
/* 30 */   LEVEL_21(21, 2.999782255D),
/* 31 */   LEVEL_22(22, 2.999856511D),
/* 32 */   LEVEL_24(24, 2.999925915D),
/* 33 */   LEVEL_33(33, 2.999791422D),
/* 34 */   LEVEL_41(41, 1.369142798D),
/* 35 */   LEVEL_44(44, 1.081953696D),
/* 36 */   LEVEL_50(50, 1.041314239D);
/*    */   
/*    */   private int level;
/*    */   
/*    */   private double param;
/*    */   
/*    */   XPLossEnum(int level, double param) {
/* 43 */     this.level = level;
/* 44 */     this.param = param;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLevel() {
/* 52 */     return this.level;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getParam() {
/* 60 */     return this.param;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static long getExpLoss(int level, long expNeed) {
/* 71 */     if (level < 8) {
/* 72 */       return 0L;
/*    */     }
/* 74 */     for (XPLossEnum xpLossEnum : values()) {
/*    */       
/* 76 */       if (level <= xpLossEnum.getLevel())
/* 77 */         return Math.round((expNeed / 100L) * xpLossEnum.getParam()); 
/*    */     } 
/* 79 */     return 0L;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\XPLossEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */