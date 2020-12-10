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
/*    */ public enum HEALTH
/*    */ {
/* 25 */   WARRIOR(110),
/* 26 */   GLADIATOR(115),
/* 27 */   TEMPLAR(100),
/* 28 */   SCOUT(100),
/* 29 */   ASSASSIN(100),
/* 30 */   RANGER(90),
/* 31 */   MAGE(90),
/* 32 */   SORCERER(90),
/* 33 */   SPIRIT_MASTER(90),
/* 34 */   PRIEST(95),
/* 35 */   CLERIC(110),
/* 36 */   CHANTER(105);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   HEALTH(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\HEALTH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */