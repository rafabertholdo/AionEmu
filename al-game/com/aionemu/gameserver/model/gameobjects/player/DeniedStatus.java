/*    */ package com.aionemu.gameserver.model.gameobjects.player;
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
/*    */ public enum DeniedStatus
/*    */ {
/* 25 */   VEIW_DETAIL(1),
/* 26 */   TRADE(2),
/* 27 */   GROUP(4),
/* 28 */   GUILD(8),
/* 29 */   FRIEND(16),
/* 30 */   DUEL(32);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   DeniedStatus(int id) {
/* 36 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 44 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\DeniedStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */