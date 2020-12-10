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
/*    */ public enum CreatureVisualState
/*    */ {
/* 25 */   VISIBLE(0),
/* 26 */   HIDE1(1),
/* 27 */   HIDE2(2),
/* 28 */   HIDE3(3),
/* 29 */   HIDE10(10),
/* 30 */   HIDE13(13),
/* 31 */   HIDE20(20),
/* 32 */   BLINKING(64);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   CreatureVisualState(int id) {
/* 38 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 46 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureVisualState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */