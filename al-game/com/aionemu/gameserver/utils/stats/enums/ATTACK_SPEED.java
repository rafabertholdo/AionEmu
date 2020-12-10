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
/*    */ public enum ATTACK_SPEED
/*    */ {
/* 25 */   WARRIOR(1500),
/* 26 */   GLADIATOR(1500),
/* 27 */   TEMPLAR(1500),
/* 28 */   SCOUT(1500),
/* 29 */   ASSASSIN(1500),
/* 30 */   RANGER(1500),
/* 31 */   MAGE(1500),
/* 32 */   SORCERER(1500),
/* 33 */   SPIRIT_MASTER(1500),
/* 34 */   PRIEST(1500),
/* 35 */   CLERIC(1500),
/* 36 */   CHANTER(1500);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   ATTACK_SPEED(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\ATTACK_SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */