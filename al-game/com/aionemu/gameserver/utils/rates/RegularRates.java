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
/*    */ public class RegularRates
/*    */   extends Rates
/*    */ {
/*    */   public int getGroupXpRate() {
/* 29 */     return RateConfig.GROUPXP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDropRate() {
/* 35 */     return RateConfig.DROP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getApNpcRate() {
/* 41 */     return RateConfig.AP_NPC_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public float getApPlayerRate() {
/* 47 */     return RateConfig.AP_PLAYER_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuestKinahRate() {
/* 53 */     return RateConfig.QUEST_KINAH_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getQuestXpRate() {
/* 59 */     return RateConfig.QUEST_XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getXpRate() {
/* 65 */     return RateConfig.XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getCraftingXPRate() {
/* 74 */     return RateConfig.CRAFTING_XP_RATE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getGatheringXPRate() {
/* 83 */     return RateConfig.GATHERING_XP_RATE;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\rates\RegularRates.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */