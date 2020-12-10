/*    */ package com.aionemu.gameserver.controllers;
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
/*    */ public enum ReviveType
/*    */ {
/* 24 */   BIND_REVIVE(0),
/*    */ 
/*    */ 
/*    */   
/* 28 */   REBIRTH_REVIVE(1),
/*    */ 
/*    */ 
/*    */   
/* 32 */   ITEM_SELF_REVIVE(2),
/*    */ 
/*    */ 
/*    */   
/* 36 */   SKILL_REVIVE(3),
/*    */ 
/*    */ 
/*    */   
/* 40 */   KISK_REVIVE(4);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private int typeId;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   ReviveType(int typeId) {
/* 52 */     this.typeId = typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getReviveTypeId() {
/* 57 */     return this.typeId;
/*    */   }
/*    */ 
/*    */   
/*    */   public static ReviveType getReviveTypeById(int id) {
/* 62 */     for (ReviveType rt : values()) {
/*    */       
/* 64 */       if (rt.typeId == id)
/* 65 */         return rt; 
/*    */     } 
/* 67 */     throw new IllegalArgumentException("Unsupported revive type: " + id);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\ReviveType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */