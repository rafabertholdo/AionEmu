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
/*    */ public enum StatModifierPriority
/*    */ {
/* 25 */   HIGH(0),
/* 26 */   MEDIUM(1),
/* 27 */   LOW(2);
/*    */   
/*    */   private int value;
/*    */   
/*    */   StatModifierPriority(int value) {
/* 32 */     this.value = value;
/*    */   }
/*    */   
/*    */   public int getValue() {
/* 36 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\StatModifierPriority.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */