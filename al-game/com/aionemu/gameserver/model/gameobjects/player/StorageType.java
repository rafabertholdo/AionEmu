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
/*    */ public enum StorageType
/*    */ {
/* 14 */   CUBE(0),
/* 15 */   REGULAR_WAREHOUSE(1),
/* 16 */   ACCOUNT_WAREHOUSE(2),
/* 17 */   LEGION_WAREHOUSE(3),
/* 18 */   BROKER(126),
/* 19 */   MAILBOX(127);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   StorageType(int id) {
/* 25 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 30 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\StorageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */