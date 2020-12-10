/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.PrivateStore;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.trade.TradeItem;
/*     */ import com.aionemu.gameserver.model.trade.TradeList;
/*     */ import com.aionemu.gameserver.model.trade.TradePSItem;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE_NAME;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.Iterator;
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
/*     */ public class PrivateStoreService
/*     */ {
/*     */   public static void addItem(Player activePlayer, TradePSItem[] tradePSItems) {
/*  49 */     if (CreatureState.ACTIVE.getId() != activePlayer.getState()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  55 */     if (activePlayer.getStore() == null) {
/*  56 */       createStore(activePlayer);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  61 */     PrivateStore store = activePlayer.getStore();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  66 */     for (int i = 0; i < tradePSItems.length; i++) {
/*     */       
/*  68 */       Item item = getItemByObjId(activePlayer, tradePSItems[i].getItemObjId());
/*  69 */       if (item != null && item.isTradeable()) {
/*     */         
/*  71 */         if (!validateItem(item, tradePSItems[i].getItemId(), tradePSItems[i].getCount())) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  76 */         store.addItemToSell(tradePSItems[i].getItemObjId(), tradePSItems[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validateItem(Item item, int itemId, long itemAmount) {
/*  88 */     return (item.getItemTemplate().getTemplateId() == itemId && itemAmount <= item.getItemCount());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createStore(Player activePlayer) {
/*  98 */     activePlayer.setStore(new PrivateStore(activePlayer));
/*  99 */     activePlayer.setState(CreatureState.PRIVATE_SHOP);
/* 100 */     PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_EMOTION((Creature)activePlayer, EmotionType.OPEN_PRIVATESHOP, 0, 0), true);
/*     */     
/* 102 */     if (CreatureState.PRIVATE_SHOP.getId() != activePlayer.getState()) {
/*     */       return;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void closePrivateStore(Player activePlayer) {
/* 113 */     activePlayer.setStore(null);
/* 114 */     activePlayer.unsetState(CreatureState.PRIVATE_SHOP);
/* 115 */     PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_EMOTION((Creature)activePlayer, EmotionType.CLOSE_PRIVATESHOP, 0, 0), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sellStoreItem(Player seller, Player buyer, TradeList tradeList) {
/* 127 */     if (!validateParticipants(seller, buyer)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 133 */     PrivateStore store = seller.getStore();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     tradeList = loadObjIds(seller, tradeList);
/* 139 */     if (tradeList == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 145 */     Storage inventory = buyer.getInventory();
/* 146 */     int freeSlots = inventory.getLimit() - inventory.getAllItems().size() + 1;
/* 147 */     if (freeSlots < tradeList.size()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 153 */     long price = getTotalPrice(store, tradeList);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 158 */     if (!ItemService.decreaseKinah(buyer, price)) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 163 */     ItemService.increaseKinah(seller, price);
/*     */     
/* 165 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 167 */       Item item = getItemByObjId(seller, tradeItem.getItemId());
/* 168 */       if (item != null) {
/*     */         
/* 170 */         TradePSItem storeItem = store.getTradeItemById(tradeItem.getItemId());
/* 171 */         if (item.getItemCount() == tradeItem.getCount()) {
/*     */           
/* 173 */           ItemService.removeItem(seller, seller.getInventory(), item, false);
/* 174 */           ItemService.addFullItem(buyer, buyer.getInventory(), item);
/* 175 */           store.removeItem(storeItem.getItemObjId());
/*     */           
/*     */           continue;
/*     */         } 
/* 179 */         ItemService.decreaseItemCount(seller, item, tradeItem.getCount());
/* 180 */         ItemService.addItem(buyer, item.getItemId(), tradeItem.getCount());
/* 181 */         store.getTradeItemById(storeItem.getItemObjId()).decreaseCount(tradeItem.getCount());
/* 182 */         if (store.getTradeItemById(storeItem.getItemObjId()).getCount() == 0L) {
/* 183 */           store.removeItem(storeItem.getItemObjId());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (store.getSoldItems().size() == 0) {
/* 192 */       closePrivateStore(seller);
/*     */     } else {
/* 194 */       PacketSendUtility.sendPacket(buyer, (AionServerPacket)new SM_PRIVATE_STORE(store));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static TradeList loadObjIds(Player seller, TradeList tradeList) {
/* 205 */     PrivateStore store = seller.getStore();
/* 206 */     TradeList newTradeList = new TradeList();
/*     */     
/* 208 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 210 */       int i = 0;
/* 211 */       for (Iterator<Integer> i$ = store.getSoldItems().keySet().iterator(); i$.hasNext(); ) { int itemObjId = ((Integer)i$.next()).intValue();
/*     */         
/* 213 */         if (i == tradeItem.getItemId())
/* 214 */           newTradeList.addPSItem(itemObjId, tradeItem.getCount()); 
/* 215 */         i++; }
/*     */     
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 222 */     if (newTradeList.size() == 0 || !validateBuyItems(seller, newTradeList)) {
/* 223 */       return null;
/*     */     }
/* 225 */     return newTradeList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validateParticipants(Player itemOwner, Player newOwner) {
/* 234 */     return (itemOwner != null && newOwner != null && itemOwner.isOnline() && newOwner.isOnline());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean validateBuyItems(Player seller, TradeList tradeList) {
/* 242 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 244 */       Item item = seller.getInventory().getItemByObjId(tradeItem.getItemId());
/*     */ 
/*     */       
/* 247 */       if (item == null) {
/* 248 */         return false;
/*     */       }
/*     */     } 
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Item getItemByObjId(Player seller, int itemObjId) {
/* 263 */     return seller.getInventory().getItemByObjId(itemObjId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static long getTotalPrice(PrivateStore store, TradeList tradeList) {
/* 275 */     long totalprice = 0L;
/* 276 */     for (TradeItem tradeItem : tradeList.getTradeItems()) {
/*     */       
/* 278 */       TradePSItem item = store.getTradeItemById(tradeItem.getItemId());
/* 279 */       totalprice += item.getPrice() * tradeItem.getCount();
/*     */     } 
/* 281 */     return totalprice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void openPrivateStore(Player activePlayer, String name) {
/* 289 */     if (name != null) {
/*     */       
/* 291 */       activePlayer.getStore().setStoreMessage(name);
/* 292 */       PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_PRIVATE_STORE_NAME(activePlayer.getObjectId(), name), true);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 297 */       PacketSendUtility.broadcastPacket(activePlayer, (AionServerPacket)new SM_PRIVATE_STORE_NAME(activePlayer.getObjectId(), ""), true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PrivateStoreService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */