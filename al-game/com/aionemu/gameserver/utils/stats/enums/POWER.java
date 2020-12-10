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
/*    */ 
/*    */ public enum POWER
/*    */ {
/* 26 */   WARRIOR(110),
/* 27 */   GLADIATOR(115),
/* 28 */   TEMPLAR(115),
/* 29 */   SCOUT(100),
/* 30 */   ASSASSIN(110),
/* 31 */   RANGER(90),
/* 32 */   MAGE(90),
/* 33 */   SORCERER(90),
/* 34 */   SPIRIT_MASTER(90),
/* 35 */   PRIEST(95),
/* 36 */   CLERIC(105),
/* 37 */   CHANTER(110);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   POWER(int value) {
/* 43 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 48 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\POWER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */