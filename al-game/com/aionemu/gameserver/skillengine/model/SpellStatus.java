/*    */ package com.aionemu.gameserver.skillengine.model;
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
/*    */ public enum SpellStatus
/*    */ {
/* 39 */   NONE(0),
/* 40 */   STUMBLE(1),
/* 41 */   STAGGER(2),
/* 42 */   OPENAERIAL(4),
/* 43 */   CLOSEAERIAL(8),
/* 44 */   SPIN(16),
/* 45 */   BLOCK(32),
/* 46 */   PARRY(64),
/* 47 */   DODGE(128),
/* 48 */   RESIST(256);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   SpellStatus(int id) {
/* 54 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 62 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SpellStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */