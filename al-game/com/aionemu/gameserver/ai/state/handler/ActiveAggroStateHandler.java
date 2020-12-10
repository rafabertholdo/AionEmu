/*    */ package com.aionemu.gameserver.ai.state.handler;
/*    */ 
/*    */ import com.aionemu.gameserver.ai.AI;
/*    */ import com.aionemu.gameserver.ai.desires.Desire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.AggressionDesire;
/*    */ import com.aionemu.gameserver.ai.desires.impl.WalkDesire;
/*    */ import com.aionemu.gameserver.ai.events.Event;
/*    */ import com.aionemu.gameserver.ai.state.AIState;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.Npc;
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
/*    */ public class ActiveAggroStateHandler
/*    */   extends StateHandler
/*    */ {
/*    */   public AIState getState() {
/* 37 */     return AIState.ACTIVE;
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
/* 48 */     ai.clearDesires();
/* 49 */     Npc owner = (Npc)ai.getOwner();
/*    */ 
/*    */     
/* 52 */     int creatureCount = 0;
/* 53 */     for (VisibleObject visibleObject : owner.getKnownList().getKnownObjects().values()) {
/*    */       
/* 55 */       if (visibleObject instanceof Creature)
/*    */       {
/* 57 */         if (owner.isAggressiveTo((Creature)visibleObject))
/* 58 */           creatureCount++; 
/*    */       }
/*    */     } 
/* 61 */     if (creatureCount > 0) {
/*    */       
/* 63 */       ai.addDesire((Desire)new AggressionDesire(owner, AIState.ACTIVE.getPriority()));
/*    */     }
/* 65 */     else if (owner.hasWalkRoutes()) {
/*    */       
/* 67 */       ai.addDesire((Desire)new WalkDesire(owner, AIState.ACTIVE.getPriority()));
/*    */     } 
/* 69 */     if (ai.desireQueueSize() == 0) {
/* 70 */       ai.handleEvent(Event.NOTHING_TODO);
/*    */     } else {
/* 72 */       ai.schedule();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\handler\ActiveAggroStateHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */