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
/*    */ public enum FIRE_RESIST
/*    */ {
/* 26 */   WARRIOR(0),
/* 27 */   GLADIATOR(0),
/* 28 */   TEMPLAR(0),
/* 29 */   SCOUT(0),
/* 30 */   ASSASSIN(0),
/* 31 */   RANGER(0),
/* 32 */   MAGE(0),
/* 33 */   SORCERER(0),
/* 34 */   SPIRIT_MASTER(0),
/* 35 */   PRIEST(0),
/* 36 */   CLERIC(0),
/* 37 */   CHANTER(0);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   FIRE_RESIST(int value) {
/* 43 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 48 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\FIRE_RESIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */