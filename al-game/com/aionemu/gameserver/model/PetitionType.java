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
/*    */ public enum PetitionType
/*    */ {
/* 25 */   CHARACTER_STUCK(256),
/* 26 */   CHARACTER_RESTORATION(512),
/* 27 */   BUG(768),
/* 28 */   QUEST(1024),
/* 29 */   UNACCEPTABLE_BEHAVIOR(1280),
/* 30 */   SUGGESTION(1536),
/* 31 */   INQUIRY(65280);
/*    */   
/*    */   private int element;
/*    */   
/*    */   PetitionType(int id) {
/* 36 */     this.element = id;
/*    */   }
/*    */   
/*    */   public int getElementId() {
/* 40 */     return this.element;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\PetitionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */