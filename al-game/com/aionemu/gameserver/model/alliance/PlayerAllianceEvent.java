/*    */ package com.aionemu.gameserver.model.alliance;
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
/*    */ public enum PlayerAllianceEvent
/*    */ {
/* 25 */   LEAVE(0),
/* 26 */   LEAVE_TIMEOUT(0),
/* 27 */   BANNED(0),
/*    */   
/* 29 */   MOVEMENT(1),
/*    */   
/* 31 */   DISCONNECTED(3),
/*    */ 
/*    */   
/* 34 */   UNK(9),
/*    */   
/* 36 */   RECONNECT(13),
/* 37 */   ENTER(13),
/* 38 */   UPDATE(13),
/* 39 */   MEMBER_GROUP_CHANGE(13),
/*    */ 
/*    */   
/* 42 */   APPOINT_VICE_CAPTAIN(13),
/* 43 */   DEMOTE_VICE_CAPTAIN(13),
/* 44 */   APPOINT_CAPTAIN(13);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   PlayerAllianceEvent(int id) {
/* 50 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 55 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\alliance\PlayerAllianceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */