/*    */ package com.aionemu.gameserver.utils.stats.enums;
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
/*    */ public enum FLY_SPEED
/*    */ {
/* 25 */   WARRIOR(9),
/* 26 */   GLADIATOR(9),
/* 27 */   TEMPLAR(9),
/* 28 */   SCOUT(9),
/* 29 */   ASSASSIN(9),
/* 30 */   RANGER(9),
/* 31 */   MAGE(9),
/* 32 */   SORCERER(9),
/* 33 */   SPIRIT_MASTER(9),
/* 34 */   PRIEST(9),
/* 35 */   CLERIC(9),
/* 36 */   CHANTER(9);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   FLY_SPEED(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\FLY_SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */