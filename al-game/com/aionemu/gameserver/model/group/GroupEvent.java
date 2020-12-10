/*    */ package com.aionemu.gameserver.model.group;
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
/*    */ public enum GroupEvent
/*    */ {
/* 27 */   LEAVE(0),
/* 28 */   MOVEMENT(1),
/* 29 */   ENTER(13),
/* 30 */   UPDATE(13),
/* 31 */   CHANGELEADER(13);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   GroupEvent(int id) {
/* 37 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 42 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\GroupEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */