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
/*    */ public enum SiegeRace
/*    */ {
/* 25 */   ELYOS(0),
/* 26 */   ASMODIANS(1),
/* 27 */   BALAUR(2);
/*    */   
/*    */   private int raceId;
/*    */   
/*    */   SiegeRace(int id) {
/* 32 */     this.raceId = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRaceId() {
/* 37 */     return this.raceId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\SiegeRace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */