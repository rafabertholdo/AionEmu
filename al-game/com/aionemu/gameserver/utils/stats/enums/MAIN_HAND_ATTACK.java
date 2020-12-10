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
/*    */ public enum MAIN_HAND_ATTACK
/*    */ {
/* 25 */   WARRIOR(19),
/* 26 */   GLADIATOR(19),
/* 27 */   TEMPLAR(19),
/* 28 */   SCOUT(18),
/* 29 */   ASSASSIN(19),
/* 30 */   RANGER(18),
/* 31 */   MAGE(16),
/* 32 */   SORCERER(16),
/* 33 */   SPIRIT_MASTER(16),
/* 34 */   PRIEST(17),
/* 35 */   CLERIC(19),
/* 36 */   CHANTER(19);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   MAIN_HAND_ATTACK(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAIN_HAND_ATTACK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */