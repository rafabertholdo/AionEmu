/*    */ package com.aionemu.gameserver.model.drop;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DropTemplate
/*    */ {
/*    */   private int mobId;
/*    */   private int itemId;
/*    */   private int min;
/*    */   private int max;
/*    */   private float chance;
/*    */   
/*    */   public DropTemplate(int mobId, int itemId, int min, int max, float chance) {
/* 41 */     this.mobId = mobId;
/* 42 */     this.itemId = itemId;
/* 43 */     this.min = min;
/* 44 */     this.max = max;
/* 45 */     this.chance = chance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMobId() {
/* 53 */     return this.mobId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getItemId() {
/* 61 */     return this.itemId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMin() {
/* 69 */     return this.min;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMax() {
/* 77 */     return this.max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getChance() {
/* 85 */     return this.chance;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\drop\DropTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */