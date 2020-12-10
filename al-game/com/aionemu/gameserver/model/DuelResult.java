/*    */ package com.aionemu.gameserver.model;
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
/*    */ public enum DuelResult
/*    */ {
/* 25 */   DUEL_WON(1300098, (byte)2),
/* 26 */   DUEL_LOST(1300099, (byte)0);
/*    */   
/*    */   private int msgId;
/*    */   private byte resultId;
/*    */   
/*    */   DuelResult(int msgId, byte resultId) {
/* 32 */     this.msgId = msgId;
/* 33 */     this.resultId = resultId;
/*    */   }
/*    */   
/*    */   public int getMsgId() {
/* 37 */     return this.msgId;
/*    */   }
/*    */   
/*    */   public byte getResultId() {
/* 41 */     return this.resultId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\DuelResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */