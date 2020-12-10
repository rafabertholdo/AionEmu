/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.trade.Exchange;
/*     */ import com.aionemu.gameserver.model.trade.ExchangeItem;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_ADD_KINAH;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_CONFIRMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EXCHANGE_REQUEST;
/*     */ import com.aionemu.gameserver.restrictions.RestrictionsManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class ExchangeService
/*     */ {
/*  42 */   private static final Logger log = Logger.getLogger(ExchangeService.class);
/*     */   
/*     */   private Map<Integer, Exchange> exchanges;
/*     */ 
/*     */   
/*     */   public static final ExchangeService getInstance() {
/*  48 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ExchangeService() {
/*  56 */     this.exchanges = new HashMap<Integer, Exchange>();
/*  57 */     log.info("ExchangeService: Initialized.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerExchange(Player player1, Player player2) {
/*  66 */     if (!validateParticipants(player1, player2)) {
/*     */       return;
/*     */     }
/*  69 */     player1.setTrading(true);
/*  70 */     player2.setTrading(true);
/*     */     
/*  72 */     this.exchanges.put(Integer.valueOf(player1.getObjectId()), new Exchange(player1, player2));
/*  73 */     this.exchanges.put(Integer.valueOf(player2.getObjectId()), new Exchange(player2, player1));
/*     */     
/*  75 */     PacketSendUtility.sendPacket(player2, (AionServerPacket)new SM_EXCHANGE_REQUEST(player1.getName()));
/*  76 */     PacketSendUtility.sendPacket(player1, (AionServerPacket)new SM_EXCHANGE_REQUEST(player2.getName()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validateParticipants(Player player1, Player player2) {
/*  85 */     return (RestrictionsManager.canTrade(player1) && RestrictionsManager.canTrade(player2));
/*     */   }
/*     */ 
/*     */   
/*     */   private Player getCurrentParter(Player player) {
/*  90 */     Exchange exchange = this.exchanges.get(Integer.valueOf(player.getObjectId()));
/*  91 */     return (exchange != null) ? exchange.getTargetPlayer() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Exchange getCurrentExchange(Player player) {
/* 100 */     return this.exchanges.get(Integer.valueOf(player.getObjectId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Exchange getCurrentParnterExchange(Player player) {
/* 110 */     Player partner = getCurrentParter(player);
/* 111 */     return (partner != null) ? getCurrentExchange(partner) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKinah(Player activePlayer, long itemCount) {
/* 120 */     Exchange currentExchange = getCurrentExchange(activePlayer);
/* 121 */     if (currentExchange.isLocked()) {
/*     */       return;
/*     */     }
/* 124 */     if (itemCount < 1L) {
/*     */       return;
/*     */     }
/*     */     
/* 128 */     long availableCount = activePlayer.getInventory().getKinahItem().getItemCount();
/*     */ 
/*     */     
/* 131 */     availableCount -= currentExchange.getKinahCount();
/*     */     
/* 133 */     long countToAdd = (availableCount > itemCount) ? itemCount : availableCount;
/*     */     
/* 135 */     if (countToAdd > 0L) {
/*     */       
/* 137 */       Player partner = getCurrentParter(activePlayer);
/* 138 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_EXCHANGE_ADD_KINAH(countToAdd, 0));
/* 139 */       PacketSendUtility.sendPacket(partner, (AionServerPacket)new SM_EXCHANGE_ADD_KINAH(countToAdd, 1));
/* 140 */       currentExchange.addKinah(countToAdd);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addItem(Player activePlayer, int itemObjId, long itemCount) {
/* 151 */     Item item = activePlayer.getInventory().getItemByObjId(itemObjId);
/* 152 */     if (item == null) {
/*     */       return;
/*     */     }
/*     */     
/* 156 */     if (!item.isTradeable()) {
/*     */       return;
/*     */     }
/* 159 */     if (itemCount < 1L) {
/*     */       return;
/*     */     }
/* 162 */     if (itemCount > item.getItemCount()) {
/*     */       return;
/*     */     }
/* 165 */     Player partner = getCurrentParter(activePlayer);
/* 166 */     Exchange currentExchange = getCurrentExchange(activePlayer);
/*     */     
/* 168 */     if (currentExchange == null) {
/*     */       return;
/*     */     }
/* 171 */     if (currentExchange.isLocked()) {
/*     */       return;
/*     */     }
/* 174 */     if (currentExchange.isExchangeListFull()) {
/*     */       return;
/*     */     }
/* 177 */     ExchangeItem exchangeItem = (ExchangeItem)currentExchange.getItems().get(Integer.valueOf(item.getObjectId()));
/*     */     
/* 179 */     long actuallAddCount = 0L;
/*     */     
/* 181 */     if (exchangeItem == null) {
/*     */       
/* 183 */       if (item.getItemCount() == itemCount) {
/* 184 */         exchangeItem = new ExchangeItem(itemObjId, itemCount, item);
/*     */       } else {
/*     */         
/* 187 */         Item newItem = ItemService.newItem(item.getItemId(), itemCount);
/* 188 */         newItem.setPersistentState(PersistentState.NOACTION);
/* 189 */         exchangeItem = new ExchangeItem(itemObjId, itemCount, newItem);
/*     */       } 
/* 191 */       currentExchange.addItem(itemObjId, exchangeItem);
/* 192 */       actuallAddCount = itemCount;
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 199 */       if (item.getItemCount() == exchangeItem.getItemCount()) {
/*     */         return;
/*     */       }
/* 202 */       long possibleToAdd = item.getItemCount() - exchangeItem.getItemCount();
/* 203 */       actuallAddCount = (itemCount > possibleToAdd) ? possibleToAdd : itemCount;
/* 204 */       exchangeItem.addCount(actuallAddCount);
/*     */     } 
/*     */     
/* 207 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_EXCHANGE_ADD_ITEM(0, exchangeItem.getItem()));
/* 208 */     PacketSendUtility.sendPacket(partner, (AionServerPacket)new SM_EXCHANGE_ADD_ITEM(1, exchangeItem.getItem()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void lockExchange(Player activePlayer) {
/* 216 */     Exchange exchange = getCurrentExchange(activePlayer);
/* 217 */     if (exchange != null) {
/*     */       
/* 219 */       exchange.lock();
/* 220 */       Player currentParter = getCurrentParter(activePlayer);
/* 221 */       PacketSendUtility.sendPacket(currentParter, (AionServerPacket)new SM_EXCHANGE_CONFIRMATION(3));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelExchange(Player activePlayer) {
/* 230 */     Player currentParter = getCurrentParter(activePlayer);
/* 231 */     cleanupExchanges(activePlayer, currentParter);
/* 232 */     if (currentParter != null) {
/* 233 */       PacketSendUtility.sendPacket(currentParter, (AionServerPacket)new SM_EXCHANGE_CONFIRMATION(1));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void confirmExchange(Player activePlayer) {
/* 241 */     Exchange currentExchange = getCurrentExchange(activePlayer);
/* 242 */     currentExchange.confirm();
/*     */     
/* 244 */     Player currentPartner = getCurrentParter(activePlayer);
/* 245 */     PacketSendUtility.sendPacket(currentPartner, (AionServerPacket)new SM_EXCHANGE_CONFIRMATION(2));
/*     */     
/* 247 */     if (getCurrentExchange(currentPartner).isConfirmed())
/*     */     {
/* 249 */       performTrade(activePlayer, currentPartner);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void performTrade(Player activePlayer, Player currentPartner) {
/* 261 */     if (!validateExchange(activePlayer, currentPartner)) {
/*     */       return;
/*     */     }
/* 264 */     PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_EXCHANGE_CONFIRMATION(0));
/* 265 */     PacketSendUtility.sendPacket(currentPartner, (AionServerPacket)new SM_EXCHANGE_CONFIRMATION(0));
/*     */     
/* 267 */     doExchanges(activePlayer, currentPartner);
/* 268 */     doExchanges(currentPartner, activePlayer);
/*     */     
/* 270 */     cleanupExchanges(activePlayer, currentPartner);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void cleanupExchanges(Player activePlayer, Player currentPartner) {
/* 280 */     if (activePlayer != null) {
/*     */       
/* 282 */       Exchange exchange = this.exchanges.remove(Integer.valueOf(activePlayer.getObjectId()));
/* 283 */       if (exchange != null)
/* 284 */         exchange.clear(); 
/* 285 */       activePlayer.setTrading(false);
/*     */     } 
/*     */     
/* 288 */     if (currentPartner != null) {
/*     */       
/* 290 */       Exchange exchange = this.exchanges.remove(Integer.valueOf(currentPartner.getObjectId()));
/* 291 */       if (exchange != null)
/* 292 */         exchange.clear(); 
/* 293 */       currentPartner.setTrading(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void doExchanges(Player sourcePlayer, Player targetPlayer) {
/* 299 */     Exchange exchange = getCurrentExchange(sourcePlayer);
/*     */     
/* 301 */     for (ExchangeItem exchangeItem : exchange.getItems().values()) {
/*     */       
/* 303 */       Item itemInInventory = sourcePlayer.getInventory().getItemByObjId(exchangeItem.getItemObjId());
/* 304 */       if (exchangeItem.getItemCount() == itemInInventory.getItemCount()) {
/*     */         
/* 306 */         ItemService.removeItem(sourcePlayer, sourcePlayer.getInventory(), itemInInventory, false);
/* 307 */         ItemService.addFullItem(targetPlayer, targetPlayer.getInventory(), itemInInventory);
/*     */         
/*     */         continue;
/*     */       } 
/* 311 */       ItemService.decreaseItemCount(sourcePlayer, itemInInventory, exchangeItem.getItemCount());
/* 312 */       ItemService.addItem(targetPlayer, itemInInventory.getItemId(), exchangeItem.getItemCount());
/*     */     } 
/*     */     
/* 315 */     long kinah = exchange.getKinahCount();
/* 316 */     if (kinah > 0L) {
/*     */       
/* 318 */       ItemService.decreaseKinah(sourcePlayer, exchange.getKinahCount());
/* 319 */       ItemService.increaseKinah(targetPlayer, exchange.getKinahCount());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validateExchange(Player activePlayer, Player currentPartner) {
/* 330 */     Exchange exchange1 = getCurrentExchange(activePlayer);
/* 331 */     Exchange exchange2 = getCurrentExchange(currentPartner);
/*     */     
/* 333 */     return (validateInventorySize(activePlayer, exchange2) && validateInventorySize(currentPartner, exchange1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean validateInventorySize(Player activePlayer, Exchange exchange) {
/* 339 */     int numberOfFreeSlots = activePlayer.getInventory().getNumberOfFreeSlots();
/* 340 */     return (numberOfFreeSlots >= exchange.getItems().size());
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 346 */     protected static final ExchangeService instance = new ExchangeService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ExchangeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */