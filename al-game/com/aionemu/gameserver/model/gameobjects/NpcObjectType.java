/*    */ package com.aionemu.gameserver.model.gameobjects;
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
/*    */ public enum NpcObjectType
/*    */ {
/* 25 */   NORMAL(1),
/* 26 */   SUMMON(2),
/* 27 */   TRAP(32),
/* 28 */   SERVANT(1024);
/*    */   private int id;
/*    */   
/*    */   NpcObjectType(int id) {
/* 32 */     this.id = id;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getId() {
/* 41 */     return this.id;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\NpcObjectType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */