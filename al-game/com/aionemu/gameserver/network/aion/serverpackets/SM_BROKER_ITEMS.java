/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SimpleModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.model.items.GodStone;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.Set;
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
/*     */ public class SM_BROKER_ITEMS
/*     */   extends AionServerPacket
/*     */ {
/*     */   private BrokerItem[] brokerItems;
/*     */   private int itemsCount;
/*     */   private int startPage;
/*     */   
/*     */   public SM_BROKER_ITEMS(BrokerItem[] brokerItems, int itemsCount, int startPage) {
/*  43 */     this.brokerItems = brokerItems;
/*  44 */     this.itemsCount = itemsCount;
/*  45 */     this.startPage = startPage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  52 */     writeD(buf, this.itemsCount);
/*  53 */     writeC(buf, 0);
/*  54 */     writeH(buf, this.startPage);
/*  55 */     writeH(buf, this.brokerItems.length);
/*     */     
/*  57 */     for (BrokerItem item : this.brokerItems) {
/*     */       
/*  59 */       if (item.getItem().getItemTemplate().isArmor() || item.getItem().getItemTemplate().isWeapon()) {
/*  60 */         writeArmorWeaponInfo(buf, item);
/*     */       } else {
/*  62 */         writeCommonInfo(buf, item);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeArmorWeaponInfo(ByteBuffer buf, BrokerItem item) {
/*  68 */     writeD(buf, item.getItem().getObjectId());
/*  69 */     writeD(buf, item.getItem().getItemTemplate().getTemplateId());
/*  70 */     writeQ(buf, item.getPrice());
/*  71 */     writeQ(buf, item.getItem().getItemCount());
/*  72 */     writeC(buf, 0);
/*  73 */     writeC(buf, item.getItem().getEnchantLevel());
/*  74 */     writeD(buf, item.getItem().getItemSkinTemplate().getTemplateId());
/*  75 */     writeC(buf, 0);
/*     */     
/*  77 */     writeItemStones(buf, item.getItem());
/*     */     
/*  79 */     GodStone godStone = item.getItem().getGodStone();
/*  80 */     writeD(buf, (godStone == null) ? 0 : godStone.getItemId());
/*     */     
/*  82 */     writeC(buf, 0);
/*  83 */     writeD(buf, 0);
/*  84 */     writeD(buf, 0);
/*  85 */     writeS(buf, item.getSeller());
/*  86 */     writeS(buf, "");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeItemStones(ByteBuffer buf, Item item) {
/*  92 */     int count = 0;
/*     */     
/*  94 */     if (item.hasManaStones()) {
/*     */       
/*  96 */       Set<ManaStone> itemStones = item.getItemStones();
/*     */       
/*  98 */       for (ManaStone itemStone : itemStones) {
/*     */         
/* 100 */         if (count == 6) {
/*     */           break;
/*     */         }
/* 103 */         StatModifier modifier = itemStone.getFirstModifier();
/* 104 */         if (modifier != null) {
/*     */           
/* 106 */           count++;
/* 107 */           writeC(buf, modifier.getStat().getItemStoneMask());
/*     */         } 
/*     */       } 
/* 110 */       writeB(buf, new byte[6 - count]);
/* 111 */       count = 0;
/* 112 */       for (ManaStone itemStone : itemStones) {
/*     */         
/* 114 */         if (count == 6) {
/*     */           break;
/*     */         }
/* 117 */         StatModifier modifier = itemStone.getFirstModifier();
/* 118 */         if (modifier != null) {
/*     */           
/* 120 */           count++;
/* 121 */           writeH(buf, ((SimpleModifier)modifier).getValue());
/*     */         } 
/*     */       } 
/* 124 */       writeB(buf, new byte[(6 - count) * 2]);
/*     */     }
/*     */     else {
/*     */       
/* 128 */       writeB(buf, new byte[18]);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeCommonInfo(ByteBuffer buf, BrokerItem item) {
/* 136 */     writeD(buf, item.getItem().getObjectId());
/* 137 */     writeD(buf, item.getItem().getItemTemplate().getTemplateId());
/* 138 */     writeQ(buf, item.getPrice());
/* 139 */     writeQ(buf, item.getItem().getItemCount());
/* 140 */     writeD(buf, 0);
/* 141 */     writeD(buf, 0);
/* 142 */     writeD(buf, 0);
/* 143 */     writeD(buf, 0);
/* 144 */     writeD(buf, 0);
/* 145 */     writeD(buf, 0);
/* 146 */     writeD(buf, 0);
/* 147 */     writeD(buf, 0);
/* 148 */     writeD(buf, 0);
/* 149 */     writeH(buf, 0);
/* 150 */     writeS(buf, item.getSeller());
/* 151 */     writeS(buf, "");
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_ITEMS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */