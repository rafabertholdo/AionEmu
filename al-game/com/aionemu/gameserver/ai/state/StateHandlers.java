/*    */ package com.aionemu.gameserver.ai.state;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.state.handler.ActiveAggroStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.ActiveNpcStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.AttackingStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.MovingToHomeStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.NoneNpcStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.RestingStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.StateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.TalkingStateHandler;
/*    */ import com.aionemu.gameserver.ai.state.handler.ThinkingStateHandler;
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
/*    */ public enum StateHandlers
/*    */ {
/* 38 */   MOVINGTOHOME_SH((StateHandler)new MovingToHomeStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 42 */   NONE_MONSTER_SH((StateHandler)new NoneNpcStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 46 */   ATTACKING_SH((StateHandler)new AttackingStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 50 */   THINKING_SH((StateHandler)new ThinkingStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 54 */   ACTIVE_NPC_SH((StateHandler)new ActiveNpcStateHandler()),
/* 55 */   ACTIVE_AGGRO_SH((StateHandler)new ActiveAggroStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 59 */   RESTING_SH((StateHandler)new RestingStateHandler()),
/*    */ 
/*    */ 
/*    */   
/* 63 */   TALKING_SH((StateHandler)new TalkingStateHandler());
/*    */   
/*    */   private StateHandler stateHandler;
/*    */ 
/*    */   
/*    */   StateHandlers(StateHandler stateHandler) {
/* 69 */     this.stateHandler = stateHandler;
/*    */   }
/*    */ 
/*    */   
/*    */   public StateHandler getHandler() {
/* 74 */     return this.stateHandler;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\StateHandlers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */