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
/*    */ public enum LootRuleType
/*    */ {
/* 27 */   FREEFORALL(0),
/* 28 */   ROUNDROBIN(1),
/* 29 */   LEADER(2);
/*    */   
/*    */   private int id;
/*    */ 
/*    */   
/*    */   LootRuleType(int id) {
/* 35 */     this.id = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 40 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootRuleType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */