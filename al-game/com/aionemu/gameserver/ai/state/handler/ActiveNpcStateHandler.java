/*    */ package com.aionemu.gameserver.ai.state.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.Desire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.WalkDesire;
/*    */ import com.aionemu.gameserver.ai.events.Event;
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
/*    */ public class ActiveNpcStateHandler
/*    */   extends StateHandler
/*    */ {
/*    */   public AIState getState() {
/* 33 */     return AIState.ACTIVE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void handleState(AIState state, AI<?> ai) {
/* 43 */     ai.clearDesires();
/* 44 */     Npc owner = (Npc)ai.getOwner();
/* 45 */     if (owner.hasWalkRoutes())
/*    */     {
/* 47 */       ai.addDesire((Desire)new WalkDesire(owner, AIState.ACTIVE.getPriority()));
/*    */     }
/*    */     
/* 50 */     if (ai.desireQueueSize() == 0) {
/* 51 */       ai.handleEvent(Event.NOTHING_TODO);
/*    */     } else {
/* 53 */       ai.schedule();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\ActiveNpcStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */