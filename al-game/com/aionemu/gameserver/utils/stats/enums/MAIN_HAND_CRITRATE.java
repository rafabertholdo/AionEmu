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
/*    */ public enum MAIN_HAND_CRITRATE
/*    */ {
/* 25 */   WARRIOR(2),
/* 26 */   GLADIATOR(2),
/* 27 */   TEMPLAR(2),
/* 28 */   SCOUT(3),
/* 29 */   ASSASSIN(3),
/* 30 */   RANGER(3),
/* 31 */   MAGE(1),
/* 32 */   SORCERER(2),
/* 33 */   SPIRIT_MASTER(2),
/* 34 */   PRIEST(2),
/* 35 */   CLERIC(2),
/* 36 */   CHANTER(1);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   MAIN_HAND_CRITRATE(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAIN_HAND_CRITRATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */