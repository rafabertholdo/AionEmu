/*    */ package com.aionemu.gameserver.model.gameobjects.stats;
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
/*    */ public enum StatEffectType
/*    */ {
/* 25 */   SKILL_EFFECT(1),
/* 26 */   ITEM_EFFECT(2),
/* 27 */   TITLE_EFFECT(3),
/* 28 */   STONE_EFFECT(4),
/* 29 */   ENCHANT_EFFECT(6),
/* 30 */   ITEM_SET_EFFECT(5);
/*    */   
/*    */   private int value;
/*    */ 
/*    */   
/*    */   StatEffectType(int value) {
/* 36 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 41 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\StatEffectType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */