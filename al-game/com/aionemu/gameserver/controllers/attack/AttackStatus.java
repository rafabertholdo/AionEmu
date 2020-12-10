/*    */ package com.aionemu.gameserver.controllers.attack;
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
/*    */ public enum AttackStatus
/*    */ {
/* 25 */   DODGE(0),
/* 26 */   OFFHAND_DODGE(1),
/* 27 */   PARRY(2),
/* 28 */   OFFHAND_PARRY(3),
/* 29 */   BLOCK(4),
/* 30 */   OFFHAND_BLOCK(5),
/* 31 */   RESIST(6),
/* 32 */   OFFHAND_RESIST(7),
/* 33 */   BUF(8),
/* 34 */   OFFHAND_BUF(9),
/* 35 */   NORMALHIT(10),
/* 36 */   OFFHAND_NORMALHIT(11),
/* 37 */   CRITICAL_DODGE(-64),
/* 38 */   CRITICAL_PARRY(-62),
/* 39 */   CRITICAL_BLOCK(-60),
/* 40 */   CRITICAL_RESIST(-58),
/* 41 */   CRITICAL(-54),
/* 42 */   OFFHAND_CRITICAL_DODGE(-47),
/* 43 */   OFFHAND_CRITICAL_PARRY(-45),
/* 44 */   OFFHAND_CRITICAL_BLOCK(-43),
/* 45 */   OFFHAND_CRITICAL_RESIST(-41),
/* 46 */   OFFHAND_CRITICAL(-37);
/*    */   
/*    */   private int _type;
/*    */ 
/*    */   
/*    */   AttackStatus(int type) {
/* 52 */     this._type = type;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 57 */     return this._type;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AttackStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */