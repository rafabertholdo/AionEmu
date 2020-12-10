/*    */ package com.aionemu.gameserver.model.legion;
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
/*    */ public enum LegionHistoryType
/*    */ {
/* 25 */   CREATE(0),
/* 26 */   JOIN(1),
/* 27 */   KICK(2),
/* 28 */   LEVEL_UP(3),
/* 29 */   APPOINTED(4),
/* 30 */   EMBLEM_REGISTER(5),
/* 31 */   EMBLEM_MODIFIED(6);
/*    */   
/*    */   private byte historyType;
/*    */ 
/*    */   
/*    */   LegionHistoryType(int historyType) {
/* 37 */     this.historyType = (byte)historyType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getHistoryId() {
/* 47 */     return this.historyType;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionHistoryType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */