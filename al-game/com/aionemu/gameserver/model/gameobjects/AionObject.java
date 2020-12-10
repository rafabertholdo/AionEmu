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
/*    */ public abstract class AionObject
/*    */ {
/*    */   private int objectId;
/*    */   
/*    */   public AionObject(Integer objId) {
/* 38 */     this.objectId = objId.intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getObjectId() {
/* 48 */     return this.objectId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract String getName();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 65 */     if (this == obj)
/* 66 */       return true; 
/* 67 */     if (obj == null)
/* 68 */       return false; 
/* 69 */     if (getClass() != obj.getClass())
/* 70 */       return false; 
/* 71 */     AionObject other = (AionObject)obj;
/* 72 */     if (this.objectId != other.objectId)
/* 73 */       return false; 
/* 74 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\AionObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */