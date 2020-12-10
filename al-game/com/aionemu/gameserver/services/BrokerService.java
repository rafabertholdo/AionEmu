/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.BrokerDAO;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.broker.BrokerItemMask;
/*     */ import com.aionemu.gameserver.model.broker.BrokerMessages;
/*     */ import com.aionemu.gameserver.model.broker.BrokerPlayerCache;
/*     */ import com.aionemu.gameserver.model.broker.BrokerRace;
/*     */ import com.aionemu.gameserver.model.gameobjects.BrokerItem;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_ITEMS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_REGISTERED_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_REGISTRATION_SERVICE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BROKER_SETTLED_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
/*     */ import org.apache.commons.lang.ArrayUtils;
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
/*     */ 
/*     */ 
/*     */ public class BrokerService
/*     */ {
/*  61 */   private Map<Integer, BrokerItem> elyosBrokerItems = (Map<Integer, BrokerItem>)(new FastMap()).shared();
/*  62 */   private Map<Integer, BrokerItem> elyosSettledItems = (Map<Integer, BrokerItem>)(new FastMap()).shared();
/*  63 */   private Map<Integer, BrokerItem> asmodianBrokerItems = (Map<Integer, BrokerItem>)(new FastMap()).shared();
/*  64 */   private Map<Integer, BrokerItem> asmodianSettledItems = (Map<Integer, BrokerItem>)(new FastMap()).shared();
/*     */   
/*  66 */   private static final Logger log = Logger.getLogger(BrokerService.class);
/*     */   
/*  68 */   private final int DELAY_BROKER_SAVE = 6000;
/*  69 */   private final int DELAY_BROKER_CHECK = 60000;
/*     */   
/*     */   private BrokerPeriodicTaskManager saveManager;
/*     */   
/*  73 */   private Map<Integer, BrokerPlayerCache> playerBrokerCache = (Map<Integer, BrokerPlayerCache>)(new FastMap()).shared();
/*     */ 
/*     */   
/*     */   public static final BrokerService getInstance() {
/*  77 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   public BrokerService() {
/*  82 */     initBrokerService();
/*     */     
/*  84 */     this.saveManager = new BrokerPeriodicTaskManager(6000);
/*  85 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  90 */             BrokerService.this.checkExpiredItems();
/*     */           }
/*     */         },  60000L, 60000L);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initBrokerService() {
/*  97 */     log.info("Loading broker...");
/*  98 */     int loadedBrokerItemsCount = 0;
/*  99 */     int loadedSettledItemsCount = 0;
/*     */     
/* 101 */     List<BrokerItem> brokerItems = ((BrokerDAO)DAOManager.getDAO(BrokerDAO.class)).loadBroker();
/*     */     
/* 103 */     for (BrokerItem item : brokerItems) {
/*     */       
/* 105 */       if (item.getItemBrokerRace() == BrokerRace.ASMODIAN) {
/*     */         
/* 107 */         if (item.isSettled()) {
/*     */           
/* 109 */           this.asmodianSettledItems.put(Integer.valueOf(item.getItemUniqueId()), item);
/* 110 */           loadedSettledItemsCount++;
/*     */           
/*     */           continue;
/*     */         } 
/* 114 */         this.asmodianBrokerItems.put(Integer.valueOf(item.getItemUniqueId()), item);
/* 115 */         loadedBrokerItemsCount++;
/*     */         continue;
/*     */       } 
/* 118 */       if (item.getItemBrokerRace() == BrokerRace.ELYOS) {
/*     */         
/* 120 */         if (item.isSettled()) {
/*     */           
/* 122 */           this.elyosSettledItems.put(Integer.valueOf(item.getItemUniqueId()), item);
/* 123 */           loadedSettledItemsCount++;
/*     */           
/*     */           continue;
/*     */         } 
/* 127 */         this.elyosBrokerItems.put(Integer.valueOf(item.getItemUniqueId()), item);
/* 128 */         loadedBrokerItemsCount++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 133 */     log.info("Broker loaded with " + loadedBrokerItemsCount + " broker items, " + loadedSettledItemsCount + " settled items.");
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
/*     */   
/*     */   public void showRequestedItems(Player player, int clientMask, int sortType, int startPage) {
/* 146 */     BrokerItem[] searchItems = null;
/* 147 */     int playerBrokerMaskCache = getPlayerMask(player);
/* 148 */     BrokerItemMask brokerMaskById = BrokerItemMask.getBrokerMaskById(clientMask);
/* 149 */     boolean isChidrenMask = brokerMaskById.isChildrenMask(playerBrokerMaskCache);
/* 150 */     if ((getFilteredItems(player)).length == 0 || !isChidrenMask) {
/*     */       
/* 152 */       searchItems = getItemsByMask(player, clientMask, false);
/*     */     }
/* 154 */     else if (isChidrenMask) {
/*     */       
/* 156 */       searchItems = getItemsByMask(player, clientMask, true);
/*     */     } else {
/*     */       
/* 159 */       searchItems = getFilteredItems(player);
/*     */     } 
/* 161 */     if (searchItems == null || searchItems.length < 0) {
/*     */       return;
/*     */     }
/* 164 */     int totalSearchItemsCount = searchItems.length;
/*     */     
/* 166 */     getPlayerCache(player).setBrokerSortTypeCache(sortType);
/* 167 */     getPlayerCache(player).setBrokerStartPageCache(startPage);
/*     */     
/* 169 */     sortBrokerItems(searchItems, sortType);
/* 170 */     searchItems = getRequestedPage(searchItems, startPage);
/*     */     
/* 172 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_ITEMS(searchItems, totalSearchItemsCount, startPage));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BrokerItem[] getItemsByMask(Player player, int clientMask, boolean cached) {
/* 183 */     List<BrokerItem> searchItems = new ArrayList<BrokerItem>();
/*     */     
/* 185 */     BrokerItemMask brokerMask = BrokerItemMask.getBrokerMaskById(clientMask);
/*     */     
/* 187 */     if (cached) {
/*     */       
/* 189 */       BrokerItem[] brokerItems = getFilteredItems(player);
/* 190 */       if (brokerItems == null) {
/* 191 */         return null;
/*     */       }
/* 193 */       for (BrokerItem item : brokerItems) {
/*     */         
/* 195 */         if (item != null && item.getItem() != null)
/*     */         {
/*     */           
/* 198 */           if (brokerMask.isMatches(item.getItem()))
/*     */           {
/* 200 */             searchItems.add(item);
/*     */           }
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       
/* 206 */       Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());
/* 207 */       if (brokerItems == null)
/* 208 */         return null; 
/* 209 */       for (BrokerItem item : brokerItems.values()) {
/*     */         
/* 211 */         if (item == null || item.getItem() == null) {
/*     */           continue;
/*     */         }
/* 214 */         if (brokerMask.isMatches(item.getItem()))
/*     */         {
/* 216 */           searchItems.add(item);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 221 */     BrokerItem[] items = searchItems.<BrokerItem>toArray(new BrokerItem[searchItems.size()]);
/* 222 */     getPlayerCache(player).setBrokerListCache(items);
/* 223 */     getPlayerCache(player).setBrokerMaskCache(clientMask);
/*     */     
/* 225 */     return items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortBrokerItems(BrokerItem[] brokerItems, int sortType) {
/* 236 */     Arrays.sort(brokerItems, BrokerItem.getComparatoryByType(sortType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BrokerItem[] getRequestedPage(BrokerItem[] brokerItems, int startPage) {
/* 247 */     List<BrokerItem> page = new ArrayList<BrokerItem>();
/* 248 */     int startingElement = startPage * 9;
/*     */     
/* 250 */     for (int i = startingElement, limit = 0; i < brokerItems.length && limit < 45; i++, limit++)
/*     */     {
/* 252 */       page.add(brokerItems[i]);
/*     */     }
/*     */     
/* 255 */     return page.<BrokerItem>toArray(new BrokerItem[page.size()]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Integer, BrokerItem> getRaceBrokerItems(Race race) {
/* 265 */     switch (race) {
/*     */       
/*     */       case ASMODIAN:
/* 268 */         return this.elyosBrokerItems;
/*     */       case ELYOS:
/* 270 */         return this.asmodianBrokerItems;
/*     */     } 
/* 272 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Map<Integer, BrokerItem> getRaceBrokerSettledItems(Race race) {
/* 283 */     switch (race) {
/*     */       
/*     */       case ASMODIAN:
/* 286 */         return this.elyosSettledItems;
/*     */       case ELYOS:
/* 288 */         return this.asmodianSettledItems;
/*     */     } 
/* 290 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buyBrokerItem(Player player, int itemUniqueId) {
/* 301 */     if (player.getInventory().isFull()) {
/*     */       
/* 303 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);
/*     */       
/*     */       return;
/*     */     } 
/* 307 */     boolean isEmptyCache = ((getFilteredItems(player)).length == 0);
/* 308 */     Race playerRace = player.getCommonData().getRace();
/*     */     
/* 310 */     BrokerItem buyingItem = getRaceBrokerItems(playerRace).get(Integer.valueOf(itemUniqueId));
/*     */     
/* 312 */     if (buyingItem == null) {
/*     */       return;
/*     */     }
/* 315 */     Item item = buyingItem.getItem();
/* 316 */     long price = buyingItem.getPrice();
/*     */     
/* 318 */     if (player.getInventory().getKinahItem().getItemCount() < price) {
/*     */       return;
/*     */     }
/* 321 */     getRaceBrokerItems(playerRace).remove(Integer.valueOf(itemUniqueId));
/* 322 */     putToSettled(playerRace, buyingItem, true);
/*     */     
/* 324 */     if (!isEmptyCache) {
/*     */       
/* 326 */       BrokerItem[] newCache = (BrokerItem[])ArrayUtils.removeElement((Object[])getFilteredItems(player), buyingItem);
/* 327 */       getPlayerCache(player).setBrokerListCache(newCache);
/*     */     } 
/*     */     
/* 330 */     ItemService.decreaseKinah(player, price);
/* 331 */     ItemService.addFullItem(player, player.getInventory(), item);
/*     */     
/* 333 */     BrokerOpSaveTask bost = new BrokerOpSaveTask(buyingItem);
/* 334 */     this.saveManager.add(bost);
/*     */     
/* 336 */     showRequestedItems(player, getPlayerCache(player).getBrokerMaskCache(), getPlayerCache(player).getBrokerSortTypeCache(), getPlayerCache(player).getBrokerStartPageCache());
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
/*     */   private void putToSettled(Race race, BrokerItem brokerItem, boolean isSold) {
/* 348 */     if (isSold) {
/* 349 */       brokerItem.removeItem();
/*     */     } else {
/* 351 */       brokerItem.setSettled();
/*     */     } 
/* 353 */     brokerItem.setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */     
/* 355 */     switch (race) {
/*     */       
/*     */       case ELYOS:
/* 358 */         this.asmodianSettledItems.put(Integer.valueOf(brokerItem.getItemUniqueId()), brokerItem);
/*     */         break;
/*     */       
/*     */       case ASMODIAN:
/* 362 */         this.elyosSettledItems.put(Integer.valueOf(brokerItem.getItemUniqueId()), brokerItem);
/*     */         break;
/*     */     } 
/*     */     
/* 366 */     Player seller = World.getInstance().findPlayer(brokerItem.getSellerId());
/*     */     
/* 368 */     this.saveManager.add(new BrokerOpSaveTask(brokerItem));
/*     */     
/* 370 */     if (seller != null)
/*     */     {
/* 372 */       PacketSendUtility.sendPacket(seller, (AionServerPacket)new SM_BROKER_SETTLED_LIST(true));
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
/*     */   public void registerItem(Player player, int itemUniqueId, int price) {
/*     */     BrokerRace brRace;
/* 385 */     Item itemToRegister = player.getInventory().getItemByObjId(itemUniqueId);
/* 386 */     Race playerRace = player.getCommonData().getRace();
/*     */     
/* 388 */     if (itemToRegister == null) {
/*     */       return;
/*     */     }
/*     */     
/* 392 */     if (!itemToRegister.isTradeable()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 397 */     if (playerRace == Race.ASMODIANS) {
/* 398 */       brRace = BrokerRace.ASMODIAN;
/* 399 */     } else if (playerRace == Race.ELYOS) {
/* 400 */       brRace = BrokerRace.ELYOS;
/*     */     } else {
/*     */       return;
/*     */     } 
/* 404 */     int registrationCommition = Math.round(price * 0.02F);
/*     */     
/* 406 */     if (registrationCommition < 10) {
/* 407 */       registrationCommition = 10;
/*     */     }
/* 409 */     if (player.getInventory().getKinahItem().getItemCount() < registrationCommition) {
/*     */       
/* 411 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_REGISTRATION_SERVICE(BrokerMessages.NO_ENOUGHT_KINAH.getId()));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 416 */     ItemService.decreaseKinah(player, registrationCommition);
/* 417 */     ItemService.removeItemFromInventory(player, itemToRegister, false);
/*     */     
/* 419 */     itemToRegister.setItemLocation(126);
/*     */     
/* 421 */     BrokerItem newBrokerItem = new BrokerItem(itemToRegister, price, player.getName(), player.getObjectId(), brRace);
/*     */     
/* 423 */     switch (brRace) {
/*     */       
/*     */       case ASMODIAN:
/* 426 */         this.asmodianBrokerItems.put(Integer.valueOf(newBrokerItem.getItemUniqueId()), newBrokerItem);
/*     */         break;
/*     */       
/*     */       case ELYOS:
/* 430 */         this.elyosBrokerItems.put(Integer.valueOf(newBrokerItem.getItemUniqueId()), newBrokerItem);
/*     */         break;
/*     */     } 
/*     */     
/* 434 */     BrokerOpSaveTask bost = new BrokerOpSaveTask(newBrokerItem);
/* 435 */     this.saveManager.add(bost);
/*     */     
/* 437 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_REGISTRATION_SERVICE(newBrokerItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showRegisteredItems(Player player) {
/* 446 */     Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());
/*     */     
/* 448 */     List<BrokerItem> registeredItems = new ArrayList<BrokerItem>();
/* 449 */     int playerId = player.getObjectId();
/*     */     
/* 451 */     for (BrokerItem item : brokerItems.values()) {
/*     */       
/* 453 */       if (item != null && item.getItem() != null && playerId == item.getSellerId()) {
/* 454 */         registeredItems.add(item);
/*     */       }
/*     */     } 
/* 457 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_REGISTERED_LIST(registeredItems));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelRegisteredItem(Player player, int brokerItemId) {
/* 467 */     Map<Integer, BrokerItem> brokerItems = getRaceBrokerItems(player.getCommonData().getRace());
/* 468 */     BrokerItem brokerItem = brokerItems.get(Integer.valueOf(brokerItemId));
/*     */     
/* 470 */     if (brokerItem != null) {
/*     */       
/* 472 */       ItemService.addFullItem(player, player.getInventory(), brokerItem.getItem());
/* 473 */       brokerItem.setPersistentState(PersistentState.DELETED);
/* 474 */       this.saveManager.add(new BrokerOpSaveTask(brokerItem));
/* 475 */       brokerItems.remove(Integer.valueOf(brokerItemId));
/*     */     } 
/* 477 */     showRegisteredItems(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void showSettledItems(Player player) {
/* 486 */     Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(player.getCommonData().getRace());
/*     */     
/* 488 */     List<BrokerItem> settledItems = new ArrayList<BrokerItem>();
/*     */     
/* 490 */     int playerId = player.getObjectId();
/* 491 */     int totalKinah = 0;
/*     */     
/* 493 */     for (BrokerItem item : brokerSettledItems.values()) {
/*     */       
/* 495 */       if (item != null && playerId == item.getSellerId()) {
/*     */         
/* 497 */         settledItems.add(item);
/*     */         
/* 499 */         if (item.isSold()) {
/* 500 */           totalKinah = (int)(totalKinah + item.getPrice());
/*     */         }
/*     */       } 
/*     */     } 
/* 504 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_SETTLED_LIST(settledItems, totalKinah));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void settleAccount(Player player) {
/* 513 */     Race playerRace = player.getCommonData().getRace();
/* 514 */     Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(playerRace);
/* 515 */     List<BrokerItem> collectedItems = new ArrayList<BrokerItem>();
/* 516 */     int playerId = player.getObjectId();
/* 517 */     int kinahCollect = 0;
/* 518 */     boolean itemsLeft = false;
/*     */     
/* 520 */     for (BrokerItem item : brokerSettledItems.values()) {
/*     */       
/* 522 */       if (item.getSellerId() == playerId) {
/* 523 */         collectedItems.add(item);
/*     */       }
/*     */     } 
/* 526 */     for (BrokerItem item : collectedItems) {
/*     */       
/* 528 */       if (item.isSold()) {
/*     */         
/* 530 */         boolean result = false;
/* 531 */         switch (playerRace) {
/*     */           
/*     */           case ELYOS:
/* 534 */             result = (this.asmodianSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
/*     */             break;
/*     */           case ASMODIAN:
/* 537 */             result = (this.elyosSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
/*     */             break;
/*     */         } 
/*     */         
/* 541 */         if (result) {
/*     */           
/* 543 */           item.setPersistentState(PersistentState.DELETED);
/* 544 */           this.saveManager.add(new BrokerOpSaveTask(item));
/* 545 */           kinahCollect = (int)(kinahCollect + item.getPrice());
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 550 */       if (item.getItem() != null) {
/*     */         
/* 552 */         Item resultItem = player.getInventory().putToBag(item.getItem());
/* 553 */         if (resultItem != null) {
/*     */           
/* 555 */           boolean result = false;
/* 556 */           switch (playerRace) {
/*     */             
/*     */             case ELYOS:
/* 559 */               result = (this.asmodianSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
/*     */               break;
/*     */             case ASMODIAN:
/* 562 */               result = (this.elyosSettledItems.remove(Integer.valueOf(item.getItemUniqueId())) != null);
/*     */               break;
/*     */           } 
/*     */           
/* 566 */           if (result) {
/*     */             
/* 568 */             item.setPersistentState(PersistentState.DELETED);
/* 569 */             this.saveManager.add(new BrokerOpSaveTask(item));
/* 570 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ADD_ITEMS(Collections.singletonList(resultItem)));
/*     */           } 
/*     */           
/*     */           continue;
/*     */         } 
/* 575 */         itemsLeft = true;
/*     */         
/*     */         continue;
/*     */       } 
/* 579 */       log.warn("Broker settled item missed. ObjID: " + item.getItemUniqueId());
/*     */     } 
/*     */ 
/*     */     
/* 583 */     ItemService.increaseKinah(player, kinahCollect);
/* 584 */     showSettledItems(player);
/*     */     
/* 586 */     if (!itemsLeft) {
/* 587 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_SETTLED_LIST(false));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkExpiredItems() {
/* 593 */     Map<Integer, BrokerItem> asmoBrokerItems = getRaceBrokerItems(Race.ASMODIANS);
/* 594 */     Map<Integer, BrokerItem> elyosBrokerItems = getRaceBrokerItems(Race.ELYOS);
/*     */     
/* 596 */     Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
/*     */     
/* 598 */     for (BrokerItem item : asmoBrokerItems.values()) {
/*     */       
/* 600 */       if (item != null && item.getExpireTime().getTime() <= currentTime.getTime()) {
/*     */         
/* 602 */         putToSettled(Race.ASMODIANS, item, false);
/* 603 */         this.asmodianBrokerItems.remove(Integer.valueOf(item.getItemUniqueId()));
/*     */       } 
/*     */     } 
/*     */     
/* 607 */     for (BrokerItem item : elyosBrokerItems.values()) {
/*     */       
/* 609 */       if (item != null && item.getExpireTime().getTime() <= currentTime.getTime()) {
/*     */         
/* 611 */         putToSettled(Race.ELYOS, item, false);
/* 612 */         this.elyosBrokerItems.remove(Integer.valueOf(item.getItemUniqueId()));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerLogin(Player player) {
/* 623 */     Map<Integer, BrokerItem> brokerSettledItems = getRaceBrokerSettledItems(player.getCommonData().getRace());
/*     */     
/* 625 */     int playerId = player.getObjectId();
/*     */     
/* 627 */     for (BrokerItem item : brokerSettledItems.values()) {
/*     */       
/* 629 */       if (item != null && playerId == item.getSellerId()) {
/*     */         
/* 631 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_BROKER_SETTLED_LIST(true));
/*     */         break;
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
/*     */   private BrokerPlayerCache getPlayerCache(Player player) {
/* 644 */     BrokerPlayerCache cacheEntry = this.playerBrokerCache.get(Integer.valueOf(player.getObjectId()));
/* 645 */     if (cacheEntry == null) {
/*     */       
/* 647 */       cacheEntry = new BrokerPlayerCache();
/* 648 */       this.playerBrokerCache.put(Integer.valueOf(player.getObjectId()), cacheEntry);
/*     */     } 
/* 650 */     return cacheEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerCache(Player player) {
/* 655 */     this.playerBrokerCache.remove(Integer.valueOf(player.getObjectId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getPlayerMask(Player player) {
/* 664 */     return getPlayerCache(player).getBrokerMaskCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BrokerItem[] getFilteredItems(Player player) {
/* 674 */     return getPlayerCache(player).getBrokerListCache();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class BrokerPeriodicTaskManager
/*     */     extends AbstractFIFOPeriodicTaskManager<BrokerOpSaveTask>
/*     */   {
/*     */     private static final String CALLED_METHOD_NAME = "brokerOperation()";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BrokerPeriodicTaskManager(int period) {
/* 689 */       super(period);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void callTask(BrokerService.BrokerOpSaveTask task) {
/* 695 */       task.run();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected String getCalledMethodName() {
/* 701 */       return "brokerOperation()";
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final class BrokerOpSaveTask
/*     */     implements Runnable
/*     */   {
/*     */     private BrokerItem brokerItem;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public BrokerOpSaveTask(BrokerItem brokerItem) {
/* 717 */       this.brokerItem = brokerItem;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 723 */       if (this.brokerItem != null) {
/* 724 */         ((BrokerDAO)DAOManager.getDAO(BrokerDAO.class)).store(this.brokerItem);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 731 */     protected static final BrokerService instance = new BrokerService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\BrokerService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */