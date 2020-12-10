/*    */ package com.aionemu.gameserver.ai.state;
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
/*    */ public enum AIState
/*    */ {
/* 25 */   THINKING(5),
/* 26 */   TALKING(4),
/* 27 */   AGGRO(3),
/* 28 */   ACTIVE(3),
/* 29 */   USESKILL(3),
/* 30 */   ATTACKING(2),
/* 31 */   RESTING(1),
/* 32 */   MOVINGTOHOME(1),
/* 33 */   NONE(0);
/*    */   
/*    */   private int priority;
/*    */ 
/*    */   
/*    */   AIState(int priority) {
/* 39 */     this.priority = priority;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 44 */     return this.priority;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ai\state\AIState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */