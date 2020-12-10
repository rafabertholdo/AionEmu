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
/*    */ public enum SPEED
/*    */ {
/* 25 */   WARRIOR(6),
/* 26 */   GLADIATOR(6),
/* 27 */   TEMPLAR(6),
/* 28 */   SCOUT(6),
/* 29 */   ASSASSIN(6),
/* 30 */   RANGER(6),
/* 31 */   MAGE(6),
/* 32 */   SORCERER(6),
/* 33 */   SPIRIT_MASTER(6),
/* 34 */   PRIEST(6),
/* 35 */   CLERIC(6),
/* 36 */   CHANTER(6);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   SPEED(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */