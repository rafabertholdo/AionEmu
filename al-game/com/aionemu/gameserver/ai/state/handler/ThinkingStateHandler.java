/*    */ package com.aionemu.gameserver.ai.state.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.state.AIState;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
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
/*    */ public class ThinkingStateHandler
/*    */   extends StateHandler
/*    */ {
/*    */   public AIState getState() {
/* 32 */     return AIState.THINKING;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleState(AIState state, AI<?> ai) {
/* 43 */     ai.clearDesires();
/*    */     
/* 45 */     Npc owner = (Npc)ai.getOwner();
/* 46 */     if (owner.getAggroList().getMostHated() != null) {
/*    */       
/* 48 */       ai.setAiState(AIState.ATTACKING);
/*    */       return;
/*    */     } 
/* 51 */     if (!owner.isAtSpawnLocation()) {
/*    */       
/* 53 */       ai.setAiState(AIState.MOVINGTOHOME);
/*    */       return;
/*    */     } 
/* 56 */     if (!owner.getLifeStats().isFullyRestoredHp()) {
/*    */       
/* 58 */       ai.setAiState(AIState.RESTING);
/*    */       return;
/*    */     } 
/* 61 */     ai.setAiState(AIState.ACTIVE);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\ThinkingStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */