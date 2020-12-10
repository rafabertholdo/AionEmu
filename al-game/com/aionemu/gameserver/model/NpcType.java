/*    */ package com.aionemu.gameserver.model;
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
/*    */ public enum NpcType
/*    */ {
/* 26 */   ATTACKABLE(0),
/*    */   
/* 28 */   AGGRESSIVE(8),
/*    */   
/* 30 */   NON_ATTACKABLE(38),
/*    */   
/* 32 */   RESURRECT(38),
/*    */   
/* 34 */   POSTBOX(38),
/*    */   
/* 36 */   USEITEM(38),
/*    */   
/* 38 */   PORTAL(38),
/*    */   
/* 40 */   ARTIFACT(38),
/*    */   
/* 42 */   ARTIFACT_PROTECTOR(0);
/*    */   
/*    */   private int someClientSideId;
/*    */ 
/*    */   
/*    */   NpcType(int id) {
/* 48 */     this.someClientSideId = id;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getId() {
/* 53 */     return this.someClientSideId;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\NpcType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */