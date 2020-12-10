/*    */ package com.aionemu.gameserver.ai.events.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.events.Event;
/*    */ import com.aionemu.gameserver.ai.state.AIState;
/*    */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
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
/*    */ public class NotSeePlayerEventHandler
/*    */   implements EventHandler
/*    */ {
/*    */   public Event getEvent() {
/* 34 */     return Event.NOT_SEE_PLAYER;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleEvent(Event event, AI<?> ai) {
/* 40 */     int playerCount = 0;
/* 41 */     for (VisibleObject visibleObject : ai.getOwner().getKnownList().getKnownObjects().values()) {
/*    */       
/* 43 */       if (visibleObject instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
/* 44 */         playerCount++; 
/*    */     } 
/* 46 */     if (playerCount == 0) {
/*    */       
/* 48 */       ai.getOwner().getKnownList().clearKnownList();
/* 49 */       ai.setAiState(AIState.THINKING);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\events\handler\NotSeePlayerEventHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */