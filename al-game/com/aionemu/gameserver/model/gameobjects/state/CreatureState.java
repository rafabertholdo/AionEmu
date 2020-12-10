/*    */ package com.aionemu.gameserver.model.gameobjects.state;
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
/*    */ public enum CreatureState
/*    */ {
/* 25 */   NPC_IDLE(64),
/*    */   
/* 27 */   FLIGHT_TELEPORT(2),
/* 28 */   CHAIR(6),
/* 29 */   LOOTING(12),
/*    */ 
/*    */   
/* 32 */   ACTIVE(1),
/* 33 */   FLYING(2),
/* 34 */   RESTING(4),
/* 35 */   DEAD(6),
/*    */   
/* 37 */   PRIVATE_SHOP(10),
/*    */   
/* 39 */   WEAPON_EQUIPPED(32),
/* 40 */   WALKING(64),
/* 41 */   POWERSHARD(128),
/* 42 */   TREATMENT(256),
/* 43 */   GLIDING(512);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int id;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   CreatureState(int id) {
/* 56 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 64 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */