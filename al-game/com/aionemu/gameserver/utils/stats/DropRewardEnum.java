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
/*    */ public enum DropRewardEnum
/*    */ {
/* 24 */   MINUS_11(-11, 0),
/* 25 */   MINUS_10(-10, 1),
/* 26 */   MINUS_9(-9, 10),
/* 27 */   MINUS_8(-8, 20),
/* 28 */   MINUS_7(-7, 30),
/* 29 */   MINUS_6(-6, 40),
/* 30 */   MINUS_5(-5, 50),
/* 31 */   MINUS_4(-4, 60),
/* 32 */   MINUS_3(-3, 90),
/* 33 */   MINUS_2(-2, 100),
/* 34 */   MINUS_1(-1, 100),
/* 35 */   ZERO(0, 100);
/*    */   
/*    */   private int dropRewardPercent;
/*    */   
/*    */   private int levelDifference;
/*    */ 
/*    */   
/*    */   DropRewardEnum(int levelDifference, int dropRewardPercent) {
/* 43 */     this.levelDifference = levelDifference;
/* 44 */     this.dropRewardPercent = dropRewardPercent;
/*    */   }
/*    */ 
/*    */   
/*    */   public int rewardPercent() {
/* 49 */     return this.dropRewardPercent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static int dropRewardFrom(int levelDifference) {
/* 59 */     if (levelDifference < MINUS_11.levelDifference)
/*    */     {
/* 61 */       return MINUS_11.dropRewardPercent;
/*    */     }
/* 63 */     if (levelDifference > ZERO.levelDifference)
/*    */     {
/* 65 */       return ZERO.dropRewardPercent;
/*    */     }
/*    */     
/* 68 */     for (DropRewardEnum dropReward : values()) {
/*    */       
/* 70 */       if (dropReward.levelDifference == levelDifference)
/*    */       {
/* 72 */         return dropReward.dropRewardPercent;
/*    */       }
/*    */     } 
/*    */     
/* 76 */     throw new NoSuchElementException("Drop reward for such level difference was not found");
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\DropRewardEnum.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */