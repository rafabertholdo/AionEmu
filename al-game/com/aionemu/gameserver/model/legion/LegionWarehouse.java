/*    */ package com.aionemu.gameserver.model.legion;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
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
/*    */ public class LegionWarehouse
/*    */   extends Storage
/*    */ {
/*    */   private Legion legion;
/*    */   
/*    */   public LegionWarehouse(Legion legion) {
/* 31 */     super(StorageType.LEGION_WAREHOUSE);
/* 32 */     this.legion = legion;
/* 33 */     setLimit(legion.getWarehouseSlots());
/* 34 */     setOwnerId(legion.getLegionId());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Legion getLegion() {
/* 42 */     return this.legion;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setOwnerLegion(Legion legion) {
/* 51 */     this.legion = legion;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionWarehouse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */