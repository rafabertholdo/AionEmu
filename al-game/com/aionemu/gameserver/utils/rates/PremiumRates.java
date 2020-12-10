/*    */ package com.aionemu.gameserver.utils.rates;
/*    */ 
/*    */ import com.aionemu.gameserver.configs.main.RateConfig;
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
/*    */ public class PremiumRates
/*    */   extends Rates
/*    */ {
/*    */   public int getGroupXpRate() {
/* 29 */     return RateConfig.PREMIUM_GROUPXP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getApNpcRate() {
/* 35 */     return RateConfig.PREMIUM_AP_NPC_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getApPlayerRate() {
/* 41 */     return RateConfig.PREMIUM_AP_PLAYER_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropRate() {
/* 47 */     return RateConfig.PREMIUM_DROP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuestKinahRate() {
/* 53 */     return RateConfig.PREMIUM_QUEST_KINAH_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuestXpRate() {
/* 59 */     return RateConfig.PREMIUM_QUEST_XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getXpRate() {
/* 65 */     return RateConfig.PREMIUM_XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getCraftingXPRate() {
/* 74 */     return RateConfig.PREMIUM_CRAFTING_XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getGatheringXPRate() {
/* 83 */     return RateConfig.PREMIUM_GATHERING_XP_RATE;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\rates\PremiumRates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */