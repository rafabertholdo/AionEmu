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
/*    */ public enum PARRY
/*    */ {
/* 25 */   WARRIOR(0),
/* 26 */   GLADIATOR(0),
/* 27 */   TEMPLAR(0),
/* 28 */   SCOUT(0),
/* 29 */   ASSASSIN(0),
/* 30 */   RANGER(0),
/* 31 */   MAGE(0),
/* 32 */   SORCERER(0),
/* 33 */   SPIRIT_MASTER(0),
/* 34 */   PRIEST(0),
/* 35 */   CLERIC(0),
/* 36 */   CHANTER(0);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   PARRY(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\PARRY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */