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
/*    */ public enum CreatureSeeState
/*    */ {
/* 25 */   NORMAL(0),
/* 26 */   SEARCH1(1),
/* 27 */   SEARCH2(2);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   CreatureSeeState(int id) {
/* 33 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 41 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureSeeState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */