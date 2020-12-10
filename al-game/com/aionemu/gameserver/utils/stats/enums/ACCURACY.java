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
/*    */ public enum ACCURACY
/*    */ {
/* 25 */   WARRIOR(100),
/* 26 */   GLADIATOR(100),
/* 27 */   TEMPLAR(100),
/* 28 */   SCOUT(110),
/* 29 */   ASSASSIN(110),
/* 30 */   RANGER(100),
/* 31 */   MAGE(95),
/* 32 */   SORCERER(100),
/* 33 */   SPIRIT_MASTER(100),
/* 34 */   PRIEST(100),
/* 35 */   CLERIC(100),
/* 36 */   CHANTER(90);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   ACCURACY(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\ACCURACY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */