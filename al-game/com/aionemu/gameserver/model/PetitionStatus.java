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
/*    */ public enum PetitionStatus
/*    */ {
/* 25 */   PENDING(0),
/* 26 */   IN_PROGRESS(1),
/* 27 */   REPLIED(2);
/*    */   
/*    */   private int element;
/*    */   
/*    */   PetitionStatus(int id) {
/* 32 */     this.element = id;
/*    */   }
/*    */   
/*    */   public int getElementId() {
/* 36 */     return this.element;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\PetitionStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */