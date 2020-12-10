/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.items.ItemId;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_UPDATE_ITEM
/*     */   extends InventoryPacket
/*     */ {
/*     */   private Item item;
/*     */   private boolean isWeaponSwitch = false;
/*     */   
/*     */   public SM_UPDATE_ITEM(Item item) {
/*  39 */     this.item = item;
/*     */   }
/*     */ 
/*     */   
/*     */   public SM_UPDATE_ITEM(Item item, boolean isWeaponSwitch) {
/*  44 */     this.item = item;
/*  45 */     this.isWeaponSwitch = isWeaponSwitch;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/*  51 */     writeD(buf, item.getObjectId());
/*  52 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*  53 */     writeH(buf, 36);
/*  54 */     writeD(buf, itemTemplate.getNameId());
/*  55 */     writeH(buf, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  62 */     writeGeneralInfo(buf, this.item);
/*     */     
/*  64 */     ItemTemplate itemTemplate = this.item.getItemTemplate();
/*     */     
/*  66 */     if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*     */       
/*  68 */       writeKinah(buf, this.item, true);
/*     */     }
/*  70 */     else if (itemTemplate.isWeapon()) {
/*     */       
/*  72 */       writeWeaponInfo(buf, this.item, true, this.isWeaponSwitch, false, false);
/*     */     }
/*  74 */     else if (itemTemplate.isArmor()) {
/*     */       
/*  76 */       writeArmorInfo(buf, this.item, true, false, false);
/*     */     }
/*  78 */     else if (itemTemplate.isStigma()) {
/*     */       
/*  80 */       writeStigmaInfo(buf, this.item);
/*     */     }
/*     */     else {
/*     */       
/*  84 */       writeGeneralItemInfo(buf, this.item);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeGeneralItemInfo(ByteBuffer buf, Item item) {
/*  90 */     writeH(buf, 22);
/*  91 */     writeC(buf, 0);
/*  92 */     writeH(buf, item.getItemMask());
/*  93 */     writeD(buf, (int)item.getItemCount());
/*  94 */     writeD(buf, 0);
/*  95 */     writeD(buf, 0);
/*  96 */     writeD(buf, 0);
/*  97 */     writeH(buf, 0);
/*  98 */     writeC(buf, 0);
/*  99 */     writeH(buf, 0);
/* 100 */     writeH(buf, item.getEquipmentSlot());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeStigmaInfo(ByteBuffer buf, Item item) {
/* 106 */     int itemSlotId = item.getEquipmentSlot();
/* 107 */     writeH(buf, 5);
/* 108 */     writeC(buf, 6);
/* 109 */     writeD(buf, item.isEquipped() ? itemSlotId : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
/* 115 */     writeH(buf, 22);
/* 116 */     writeC(buf, 0);
/* 117 */     writeH(buf, item.getItemMask());
/* 118 */     writeQ(buf, item.getItemCount());
/* 119 */     writeD(buf, 0);
/* 120 */     writeD(buf, 0);
/* 121 */     writeH(buf, 0);
/* 122 */     writeC(buf, 0);
/* 123 */     writeC(buf, 26);
/* 124 */     writeC(buf, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_UPDATE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */