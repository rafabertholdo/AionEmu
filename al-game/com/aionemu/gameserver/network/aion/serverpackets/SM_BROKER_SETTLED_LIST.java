/*     */ package com.aionemu.gameserver.network.aion.serverpackets;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import java.nio.ByteBuffer;
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
/*     */ 
/*     */ 
/*     */ public class SM_BROKER_SETTLED_LIST
/*     */   extends AionServerPacket
/*     */ {
/*     */   List<BrokerItem> settledItems;
/*     */   private long totalKinah;
/*     */   private boolean isIconUpdate;
/*     */   private int haveItemsIcon;
/*     */   
/*     */   public SM_BROKER_SETTLED_LIST(List<BrokerItem> settledItems, long totalKinah) {
/*  45 */     this.isIconUpdate = false;
/*  46 */     this.settledItems = settledItems;
/*  47 */     this.totalKinah = totalKinah;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SM_BROKER_SETTLED_LIST(boolean haveItems) {
/*  56 */     this.isIconUpdate = true;
/*  57 */     if (haveItems) {
/*  58 */       this.haveItemsIcon = 1;
/*     */     } else {
/*  60 */       this.haveItemsIcon = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeImpl(AionConnection con, ByteBuffer buf) {
/*  66 */     if (this.isIconUpdate) {
/*     */       
/*  68 */       writeD(buf, 0);
/*  69 */       writeD(buf, this.haveItemsIcon);
/*  70 */       writeH(buf, 1);
/*  71 */       writeD(buf, 0);
/*  72 */       writeC(buf, 1);
/*  73 */       writeH(buf, 0);
/*     */     }
/*     */     else {
/*     */       
/*  77 */       writeQ(buf, this.totalKinah);
/*     */       
/*  79 */       writeH(buf, 1);
/*  80 */       writeD(buf, 0);
/*  81 */       writeC(buf, 0);
/*     */       
/*  83 */       writeH(buf, this.settledItems.size());
/*  84 */       for (int i = 0; i < this.settledItems.size(); i++) {
/*     */         
/*  86 */         writeD(buf, ((BrokerItem)this.settledItems.get(i)).getItemId());
/*  87 */         if (((BrokerItem)this.settledItems.get(i)).isSold()) {
/*  88 */           writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getPrice());
/*     */         } else {
/*  90 */           writeQ(buf, 0L);
/*  91 */         }  writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getItemCount());
/*  92 */         writeQ(buf, ((BrokerItem)this.settledItems.get(i)).getItemCount());
/*  93 */         writeD(buf, (int)((BrokerItem)this.settledItems.get(i)).getSettleTime().getTime() / 60000);
/*  94 */         writeH(buf, 0);
/*  95 */         writeD(buf, ((BrokerItem)this.settledItems.get(i)).getItemId());
/*  96 */         writeD(buf, 0);
/*  97 */         writeD(buf, 0);
/*  98 */         writeD(buf, 0);
/*  99 */         writeD(buf, 0);
/* 100 */         writeD(buf, 0);
/* 101 */         writeD(buf, 0);
/* 102 */         writeD(buf, 0);
/* 103 */         writeD(buf, 0);
/* 104 */         writeH(buf, 0);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_BROKER_SETTLED_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */