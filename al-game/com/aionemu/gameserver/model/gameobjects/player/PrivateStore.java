/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.model.trade.TradePSItem;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashMap;
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
/*     */ public class PrivateStore
/*     */ {
/*     */   private Player owner;
/*     */   private LinkedHashMap<Integer, TradePSItem> items;
/*     */   private String storeMessage;
/*     */   
/*     */   public PrivateStore(Player owner) {
/*  39 */     this.owner = owner;
/*  40 */     this.items = new LinkedHashMap<Integer, TradePSItem>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getOwner() {
/*  50 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LinkedHashMap<Integer, TradePSItem> getSoldItems() {
/*  60 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItemToSell(int itemObjId, TradePSItem tradeItem) {
/*  71 */     this.items.put(Integer.valueOf(itemObjId), tradeItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeItem(int itemObjId) {
/*  81 */     if (this.items.containsKey(Integer.valueOf(itemObjId))) {
/*     */       
/*  83 */       LinkedHashMap<Integer, TradePSItem> newItems = new LinkedHashMap<Integer, TradePSItem>();
/*  84 */       for (Iterator<Integer> i$ = this.items.keySet().iterator(); i$.hasNext(); ) { int itemObjIds = ((Integer)i$.next()).intValue();
/*     */         
/*  86 */         if (itemObjId != itemObjIds)
/*  87 */           newItems.put(Integer.valueOf(itemObjIds), this.items.get(Integer.valueOf(itemObjIds)));  }
/*     */       
/*  89 */       this.items = newItems;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TradePSItem getTradeItemById(int itemObjId) {
/*  99 */     if (this.items.containsKey(Integer.valueOf(itemObjId)))
/* 100 */       return this.items.get(Integer.valueOf(itemObjId)); 
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setStoreMessage(String storeMessage) {
/* 110 */     this.storeMessage = storeMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStoreMessage() {
/* 118 */     return this.storeMessage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 123 */     this.owner = null;
/* 124 */     this.items.clear();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\PrivateStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */