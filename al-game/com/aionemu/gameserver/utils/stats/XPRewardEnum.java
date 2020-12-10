/*    */ package com.aionemu.gameserver.utils.stats;
/*    */ 
/*    */ import java.util.NoSuchElementException;
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
/*    */ public enum XPRewardEnum
/*    */ {
/* 27 */   MINUS_11(-11, 0),
/* 28 */   MINUS_10(-10, 1),
/* 29 */   MINUS_9(-9, 10),
/* 30 */   MINUS_8(-8, 20),
/* 31 */   MINUS_7(-7, 30),
/* 32 */   MINUS_6(-6, 40),
/* 33 */   MINUS_5(-5, 50),
/* 34 */   MINUS_4(-4, 60),
/* 35 */   MINUS_3(-3, 90),
/* 36 */   MINUS_2(-2, 100),
/* 37 */   MINUS_1(-1, 100),
/* 38 */   ZERO(0, 100),
/* 39 */   PLUS_1(1, 105),
/* 40 */   PLUS_2(2, 110),
/* 41 */   PLUS_3(3, 115),
/* 42 */   PLUS_4(4, 120);
/*    */ 
/*    */   
/*    */   private int xpRewardPercent;
/*    */   
/*    */   private int levelDifference;
/*    */ 
/*    */   
/*    */   XPRewardEnum(int levelDifference, int xpRewardPercent) {
/* 51 */     this.levelDifference = levelDifference;
/* 52 */     this.xpRewardPercent = xpRewardPercent;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rewardPercent() {
/* 57 */     return this.xpRewardPercent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int xpRewardFrom(int levelDifference) {
/* 67 */     if (levelDifference < MINUS_11.levelDifference)
/*    */     {
/* 69 */       return MINUS_11.xpRewardPercent;
/*    */     }
/* 71 */     if (levelDifference > PLUS_4.levelDifference)
/*    */     {
/* 73 */       return PLUS_4.xpRewardPercent;
/*    */     }
/*    */     
/* 76 */     for (XPRewardEnum xpReward : values()) {
/*    */       
/* 78 */       if (xpReward.levelDifference == levelDifference)
/*    */       {
/* 80 */         return xpReward.xpRewardPercent;
/*    */       }
/*    */     } 
/*    */     
/* 84 */     throw new NoSuchElementException("XP reward for such level difference was not found");
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\XPRewardEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */