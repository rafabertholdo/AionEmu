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
/*    */ public enum KNOWLEDGE
/*    */ {
/* 25 */   WARRIOR(90),
/* 26 */   GLADIATOR(90),
/* 27 */   TEMPLAR(90),
/* 28 */   SCOUT(90),
/* 29 */   ASSASSIN(90),
/* 30 */   RANGER(120),
/* 31 */   MAGE(115),
/* 32 */   SORCERER(120),
/* 33 */   SPIRIT_MASTER(115),
/* 34 */   PRIEST(100),
/* 35 */   CLERIC(105),
/* 36 */   CHANTER(105);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   KNOWLEDGE(int value) {
/* 42 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 47 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\KNOWLEDGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */