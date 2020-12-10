/*    */ package com.aionemu.gameserver.network.aion.serverpackets;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Item;
/*    */ import com.aionemu.gameserver.model.items.ItemId;
/*    */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*    */ import java.nio.ByteBuffer;
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
/*    */ public class SM_UPDATE_WAREHOUSE_ITEM
/*    */   extends InventoryPacket
/*    */ {
/*    */   Item item;
/*    */   int warehouseType;
/*    */   
/*    */   public SM_UPDATE_WAREHOUSE_ITEM(Item item, int warehouseType) {
/* 26 */     this.item = item;
/* 27 */     this.warehouseType = warehouseType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 33 */     writeGeneralInfo(buf, this.item);
/*    */     
/* 35 */     ItemTemplate itemTemplate = this.item.getItemTemplate();
/*    */     
/* 37 */     if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*    */       
/* 39 */       writeKinah(buf, this.item, false);
/*    */     }
/* 41 */     else if (itemTemplate.isWeapon()) {
/*    */       
/* 43 */       writeWeaponInfo(buf, this.item, false);
/*    */     }
/* 45 */     else if (itemTemplate.isArmor()) {
/*    */       
/* 47 */       writeArmorInfo(buf, this.item, false, false, false);
/*    */     }
/*    */     else {
/*    */       
/* 51 */       writeGeneralItemInfo(buf, this.item, false, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/* 58 */     writeD(buf, item.getObjectId());
/* 59 */     writeC(buf, this.warehouseType);
/* 60 */     ItemTemplate itemTemplate = item.getItemTemplate();
/* 61 */     writeH(buf, 36);
/* 62 */     writeD(buf, itemTemplate.getNameId());
/* 63 */     writeH(buf, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
/* 69 */     writeH(buf, 22);
/* 70 */     writeC(buf, 0);
/* 71 */     writeH(buf, item.getItemMask());
/* 72 */     writeQ(buf, item.getItemCount());
/* 73 */     writeD(buf, 0);
/* 74 */     writeD(buf, 0);
/* 75 */     writeH(buf, 0);
/* 76 */     writeC(buf, 0);
/* 77 */     writeC(buf, 255);
/* 78 */     writeC(buf, 255);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_UPDATE_WAREHOUSE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */