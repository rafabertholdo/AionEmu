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
/*    */ 
/*    */ public class SM_WAREHOUSE_UPDATE
/*    */   extends InventoryPacket
/*    */ {
/*    */   private int warehouseType;
/*    */   private Item item;
/*    */   
/*    */   public SM_WAREHOUSE_UPDATE(Item item, int warehouseType) {
/* 27 */     this.warehouseType = warehouseType;
/* 28 */     this.item = item;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/* 34 */     writeC(buf, this.warehouseType);
/* 35 */     writeH(buf, 13);
/* 36 */     writeH(buf, 1);
/*    */     
/* 38 */     writeGeneralInfo(buf, this.item);
/*    */     
/* 40 */     ItemTemplate itemTemplate = this.item.getItemTemplate();
/*    */     
/* 42 */     if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*    */       
/* 44 */       writeKinah(buf, this.item, false);
/*    */     }
/* 46 */     else if (itemTemplate.isWeapon()) {
/*    */       
/* 48 */       writeWeaponInfo(buf, this.item, false);
/*    */     }
/* 50 */     else if (itemTemplate.isArmor()) {
/*    */       
/* 52 */       writeArmorInfo(buf, this.item, false, false, false);
/*    */     }
/*    */     else {
/*    */       
/* 56 */       writeGeneralItemInfo(buf, this.item, false, false);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/* 64 */     writeD(buf, item.getObjectId());
/* 65 */     ItemTemplate itemTemplate = item.getItemTemplate();
/* 66 */     writeD(buf, itemTemplate.getTemplateId());
/* 67 */     writeC(buf, 0);
/* 68 */     writeH(buf, 36);
/* 69 */     writeD(buf, itemTemplate.getNameId());
/* 70 */     writeH(buf, 0);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_WAREHOUSE_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */