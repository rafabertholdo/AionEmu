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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SM_INVENTORY_INFO
/*     */   extends InventoryPacket
/*     */ {
/*     */   public static final int EMPTY = 0;
/*     */   public static final int FULL = 1;
/*  42 */   public int CUBE = 0;
/*     */   
/*     */   private List<Item> items;
/*     */   
/*     */   private int size;
/*  47 */   public int packetType = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_INVENTORY_INFO(List<Item> items, int cubesize) {
/*  55 */     items.removeAll(Collections.singletonList(null));
/*  56 */     this.items = items;
/*  57 */     this.size = items.size();
/*  58 */     this.CUBE = cubesize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_INVENTORY_INFO() {
/*  66 */     this.packetType = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  75 */     if (this.packetType == 0) {
/*     */       
/*  77 */       writeD(buf, 0);
/*  78 */       writeH(buf, 0);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  83 */     writeC(buf, 1);
/*  84 */     writeC(buf, this.CUBE);
/*  85 */     writeC(buf, 0);
/*  86 */     writeC(buf, 0);
/*  87 */     writeH(buf, this.size);
/*     */     
/*  89 */     for (Item item : this.items) {
/*     */       
/*  91 */       writeGeneralInfo(buf, item);
/*     */       
/*  93 */       ItemTemplate itemTemplate = item.getItemTemplate();
/*     */       
/*  95 */       if (itemTemplate.getTemplateId() == ItemId.KINAH.value()) {
/*     */         
/*  97 */         writeKinah(buf, item, true); continue;
/*     */       } 
/*  99 */       if (itemTemplate.isWeapon()) {
/*     */         
/* 101 */         writeWeaponInfo(buf, item, true); continue;
/*     */       } 
/* 103 */       if (itemTemplate.isArmor()) {
/*     */         
/* 105 */         writeArmorInfo(buf, item, true, false, false); continue;
/*     */       } 
/* 107 */       if (itemTemplate.isStigma()) {
/*     */         
/* 109 */         writeStigmaInfo(buf, item);
/*     */         
/*     */         continue;
/*     */       } 
/* 113 */       writeGeneralItemInfo(buf, item, false, false);
/* 114 */       writeC(buf, 0);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_INVENTORY_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */