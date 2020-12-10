/*    */ package com.aionemu.gameserver.model.siege;
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
/*    */ public enum SiegeType
/*    */ {
/* 26 */   FORTRESS(0),
/* 27 */   ARTIFACT(1),
/*    */ 
/*    */   
/* 30 */   BOSSRAID_LIGHT(2),
/* 31 */   BOSSRAID_DARK(3),
/*    */ 
/*    */   
/* 34 */   INDUN(4),
/* 35 */   UNDERPASS(5);
/*    */   
/*    */   private int typeId;
/*    */   
/*    */   SiegeType(int id) {
/* 40 */     this.typeId = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTypeId() {
/* 45 */     return this.typeId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\SiegeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */