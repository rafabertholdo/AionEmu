/*    */ package com.aionemu.gameserver.model.group;
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
/*    */ public enum LootDistribution
/*    */ {
/* 27 */   NORMAL(0),
/* 28 */   ROLL_DICE(2),
/* 29 */   BID(3);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   LootDistribution(int id) {
/* 35 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootDistribution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */