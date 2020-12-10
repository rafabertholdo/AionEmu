/*     */ package com.aionemu.gameserver.model.trade;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class TradeList
/*     */ {
/*     */   private int sellerObjId;
/*  36 */   private List<TradeItem> tradeItems = new ArrayList<TradeItem>();
/*     */   
/*     */   private long requiredKinah;
/*     */   
/*     */   private int requiredAp;
/*     */   
/*  42 */   private Map<Integer, Integer> requiredItems = new HashMap<Integer, Integer>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBuyItem(int itemId, long count) {
/*  51 */     ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
/*  52 */     if (itemTemplate != null) {
/*     */       
/*  54 */       TradeItem tradeItem = new TradeItem(itemId, count);
/*  55 */       tradeItem.setItemTemplate(itemTemplate);
/*  56 */       this.tradeItems.add(tradeItem);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addPSItem(int itemId, long count) {
/*  66 */     TradeItem tradeItem = new TradeItem(itemId, count);
/*  67 */     this.tradeItems.add(tradeItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSellItem(int itemObjId, long count) {
/*  76 */     TradeItem tradeItem = new TradeItem(itemObjId, count);
/*  77 */     this.tradeItems.add(tradeItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean calculateBuyListPrice(Player player, int modifier) {
/*  85 */     long availableKinah = player.getInventory().getKinahItem().getItemCount();
/*  86 */     this.requiredKinah = 0L;
/*     */ 
/*     */     
/*  89 */     for (TradeItem tradeItem : this.tradeItems)
/*     */     {
/*  91 */       this.requiredKinah += player.getPrices().getKinahForBuy(tradeItem.getItemTemplate().getPrice()) * tradeItem.getCount() * modifier / 100L;
/*     */     }
/*     */     
/*  94 */     this.requiredKinah = player.getPrices().getKinahForBuy(this.requiredKinah);
/*     */     
/*  96 */     return (availableKinah >= this.requiredKinah);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean calculateAbyssBuyListPrice(Player player) {
/* 104 */     int ap = player.getAbyssRank().getAp();
/*     */     
/* 106 */     this.requiredAp = 0;
/* 107 */     this.requiredItems.clear();
/*     */     
/* 109 */     for (TradeItem tradeItem : this.tradeItems) {
/*     */       
/* 111 */       this.requiredAp = (int)(this.requiredAp + tradeItem.getItemTemplate().getAbyssPoints() * tradeItem.getCount());
/* 112 */       int itemId = tradeItem.getItemTemplate().getAbyssItem();
/*     */       
/* 114 */       Integer alreadyAddedCount = this.requiredItems.get(Integer.valueOf(itemId));
/* 115 */       if (alreadyAddedCount == null) {
/* 116 */         this.requiredItems.put(Integer.valueOf(itemId), Integer.valueOf(tradeItem.getItemTemplate().getAbyssItemCount())); continue;
/*     */       } 
/* 118 */       this.requiredItems.put(Integer.valueOf(itemId), Integer.valueOf(alreadyAddedCount.intValue() + tradeItem.getItemTemplate().getAbyssItemCount()));
/*     */     } 
/*     */     
/* 121 */     if (ap < this.requiredAp) {
/* 122 */       return false;
/*     */     }
/* 124 */     for (Integer itemId : this.requiredItems.keySet()) {
/*     */       
/* 126 */       long count = player.getInventory().getItemCountByItemId(itemId.intValue());
/* 127 */       if (count < ((Integer)this.requiredItems.get(itemId)).intValue()) {
/* 128 */         return false;
/*     */       }
/*     */     } 
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<TradeItem> getTradeItems() {
/* 140 */     return this.tradeItems;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 145 */     return this.tradeItems.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSellerObjId() {
/* 153 */     return this.sellerObjId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSellerObjId(int npcObjId) {
/* 161 */     this.sellerObjId = npcObjId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredAp() {
/* 169 */     return this.requiredAp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getRequiredKinah() {
/* 177 */     return this.requiredKinah;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, Integer> getRequiredItems() {
/* 185 */     return this.requiredItems;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\trade\TradeList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */