/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.items.ItemId;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.InventoryPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
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
/*     */ public class SM_WAREHOUSE_INFO
/*     */   extends InventoryPacket
/*     */ {
/*     */   private int warehouseType;
/*     */   private List<Item> itemList;
/*     */   private boolean firstPacket;
/*     */   private int expandLvl;
/*     */   
/*     */   public SM_WAREHOUSE_INFO(List<Item> items, int warehouseType, int expandLvl, boolean firstPacket) {
/*  42 */     this.warehouseType = warehouseType;
/*  43 */     this.expandLvl = expandLvl;
/*  44 */     this.firstPacket = firstPacket;
/*  45 */     if (items == null) {
/*  46 */       this.itemList = Collections.emptyList();
/*     */     } else {
/*  48 */       this.itemList = items;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  54 */     writeC(buf, this.warehouseType);
/*  55 */     writeC(buf, this.firstPacket ? 1 : 0);
/*  56 */     writeC(buf, this.expandLvl);
/*  57 */     writeH(buf, 0);
/*  58 */     writeH(buf, this.itemList.size());
/*  59 */     for (Item item : this.itemList) {
/*     */       
/*  61 */       writeGeneralInfo(buf, item);
/*     */       
/*  63 */       ItemTemplate itemTemplate = item.getItemTemplate();
/*     */       
/*  65 */       if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*  66 */         writeKinah(buf, item, false); continue;
/*     */       } 
/*  68 */       if (itemTemplate.isWeapon()) {
/*  69 */         writeWeaponInfo(buf, item, false); continue;
/*     */       } 
/*  71 */       if (itemTemplate.isArmor()) {
/*  72 */         writeArmorInfo(buf, item, false, false, false);
/*     */         continue;
/*     */       } 
/*  75 */       writeGeneralItemInfo(buf, item, false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeGeneralInfo(ByteBuffer buf, Item item) {
/*  82 */     writeD(buf, item.getObjectId());
/*  83 */     ItemTemplate itemTemplate = item.getItemTemplate();
/*  84 */     writeD(buf, itemTemplate.getTemplateId());
/*  85 */     writeC(buf, 0);
/*  86 */     writeH(buf, 36);
/*  87 */     writeD(buf, itemTemplate.getNameId());
/*  88 */     writeH(buf, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeKinah(ByteBuffer buf, Item item, boolean isInventory) {
/*  94 */     writeH(buf, 22);
/*  95 */     writeC(buf, 0);
/*  96 */     writeH(buf, item.getItemMask());
/*  97 */     writeQ(buf, item.getItemCount());
/*  98 */     writeD(buf, 0);
/*  99 */     writeD(buf, 0);
/* 100 */     writeH(buf, 0);
/* 101 */     writeC(buf, 0);
/* 102 */     writeC(buf, 255);
/* 103 */     writeC(buf, 255);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_WAREHOUSE_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */