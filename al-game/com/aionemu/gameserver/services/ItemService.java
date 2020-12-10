/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.dao.ItemStoneListDAO;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.DescriptionId;
/*     */ import com.aionemu.gameserver.model.gameobjects.Item;
/*     */ import com.aionemu.gameserver.model.gameobjects.PersistentState;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Storage;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.StorageType;
/*     */ import com.aionemu.gameserver.model.items.ItemId;
/*     */ import com.aionemu.gameserver.model.items.ManaStone;
/*     */ import com.aionemu.gameserver.model.templates.item.GodstoneInfo;
/*     */ import com.aionemu.gameserver.model.templates.item.ItemTemplate;
/*     */ import com.aionemu.gameserver.model.templates.quest.QuestItems;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_WAREHOUSE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_WAREHOUSE_ITEM;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_UPDATE;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
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
/*     */ public class ItemService
/*     */ {
/*  58 */   private static Logger log = Logger.getLogger(ItemService.class);
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
/*     */   public static Item newItem(int itemId, long count) {
/*  70 */     ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
/*  71 */     if (itemTemplate == null) {
/*     */       
/*  73 */       log.error("Item was not populated correctly. Item template is missing for item id: " + itemId);
/*  74 */       return null;
/*     */     } 
/*     */     
/*  77 */     int maxStackCount = itemTemplate.getMaxStackCount();
/*  78 */     if (count > maxStackCount && maxStackCount != 0)
/*     */     {
/*  80 */       count = maxStackCount;
/*     */     }
/*     */ 
/*     */     
/*  84 */     return new Item(0, IDFactory.getInstance().nextId(), itemTemplate, count, false, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void loadItemStones(List<Item> itemList) {
/*  95 */     if (itemList == null)
/*     */       return; 
/*  97 */     ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).load(itemList);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void splitItem(Player player, int itemObjId, long splitAmount, int slotNum, int sourceStorageType, int destinationStorageType) {
/* 113 */     Storage sourceStorage = player.getStorage(sourceStorageType);
/* 114 */     Storage destinationStorage = player.getStorage(destinationStorageType);
/*     */     
/* 116 */     Item itemToSplit = sourceStorage.getItemByObjId(itemObjId);
/* 117 */     if (itemToSplit == null) {
/*     */       
/* 119 */       itemToSplit = sourceStorage.getKinahItem();
/* 120 */       if (itemToSplit.getObjectId() != itemObjId || itemToSplit == null) {
/*     */         
/* 122 */         log.warn(String.format("CHECKPOINT: attempt to split null item %d %d %d", new Object[] { Integer.valueOf(itemObjId), Long.valueOf(splitAmount), Integer.valueOf(slotNum) }));
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     if (itemToSplit.getItemTemplate().isKinah()) {
/*     */       
/* 131 */       if (!decreaseKinah(player, sourceStorage, splitAmount))
/*     */         return; 
/* 133 */       increaseKinah(player, destinationStorage, splitAmount);
/*     */       
/*     */       return;
/*     */     } 
/* 137 */     long oldItemCount = itemToSplit.getItemCount() - splitAmount;
/*     */     
/* 139 */     if (itemToSplit.getItemCount() < splitAmount || oldItemCount == 0L) {
/*     */       return;
/*     */     }
/* 142 */     Item newItem = newItem(itemToSplit.getItemId(), splitAmount);
/* 143 */     newItem.setEquipmentSlot(slotNum);
/*     */     
/* 145 */     if (addFullItem(player, destinationStorage, newItem, false))
/*     */     {
/* 147 */       decreaseItemCount(player, sourceStorage, itemToSplit, splitAmount);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean decreaseKinah(Player player, long amount) {
/* 153 */     return decreaseKinah(player, player.getInventory(), amount);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean decreaseKinah(Player player, Storage storage, long amount) {
/* 158 */     boolean operationResult = storage.decreaseKinah(amount);
/* 159 */     if (operationResult)
/*     */     {
/* 161 */       sendUpdateItemPacket(player, storage.getStorageType(), storage.getKinahItem());
/*     */     }
/* 163 */     return operationResult;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void increaseItemCount(Player player, Storage storage, Item item, long amount) {
/* 168 */     item.increaseItemCount(amount);
/* 169 */     sendUpdateItemPacket(player, storage.getStorageType(), item);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void increaseKinah(Player player, Storage storage, long amount) {
/* 174 */     storage.increaseKinah(amount);
/* 175 */     sendUpdateItemPacket(player, storage.getStorageType(), storage.getKinahItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void increaseKinah(Player player, long amount) {
/* 180 */     increaseKinah(player, player.getInventory(), amount);
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
/*     */   
/*     */   public static long decreaseItemCount(Player player, Item item, long count) {
/* 194 */     return decreaseItemCount(player, player.getInventory(), item, count);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean decreaseItemCountByItemId(Player player, int itemId, long count) {
/* 199 */     return decreaseItemCountByItemId(player, player.getInventory(), itemId, count);
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
/*     */   
/*     */   public static boolean decreaseItemCountByItemId(Player player, Storage storage, int itemId, long count) {
/* 213 */     if (count < 1L) {
/* 214 */       return false;
/*     */     }
/* 216 */     List<Item> items = storage.getItemsByItemId(itemId);
/*     */     
/* 218 */     for (Item item : items) {
/*     */       
/* 220 */       count = decreaseItemCount(player, storage, item, count);
/*     */       
/* 222 */       if (count == 0L)
/*     */         break; 
/*     */     } 
/* 225 */     return (count >= 0L);
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
/*     */   
/*     */   public static long decreaseItemCount(Player player, Storage storage, Item item, long count) {
/* 239 */     long itemCount = item.getItemCount();
/* 240 */     if (itemCount >= count) {
/*     */       
/* 242 */       item.decreaseItemCount(count);
/* 243 */       count = 0L;
/*     */     }
/*     */     else {
/*     */       
/* 247 */       item.decreaseItemCount(itemCount);
/* 248 */       count -= itemCount;
/*     */     } 
/* 250 */     if (item.getItemCount() == 0L)
/*     */     {
/* 252 */       removeItem(player, storage, item, true);
/*     */     }
/* 254 */     sendUpdateItemPacket(player, storage.getStorageType(), item);
/*     */     
/* 256 */     return count;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean removeItemFromInventory(Player player, Item item, boolean persist) {
/* 261 */     return removeItem(player, player.getInventory(), item, persist);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean removeItemFromInventoryByItemId(Player player, int itemId) {
/* 266 */     Storage storage = player.getInventory();
/* 267 */     boolean sucess = false;
/* 268 */     List<Item> items = storage.getItemsByItemId(itemId);
/*     */     
/* 270 */     for (Item item : items)
/*     */     {
/* 272 */       sucess |= removeItem(player, player.getInventory(), item, true);
/*     */     }
/* 274 */     return sucess;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean removeItemFromInventory(Player player, Item item) {
/* 279 */     return removeItem(player, player.getInventory(), item, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean removeItem(Player player, Storage storage, Item item, boolean persist) {
/* 284 */     return removeItem(player, storage, item, persist, true);
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
/*     */   
/*     */   public static boolean removeItem(Player player, Storage storage, Item item, boolean persist, boolean sendPacket) {
/* 298 */     if (item == null) {
/*     */       
/* 300 */       log.warn("An item from player '" + player.getName() + "' that should be removed doesn't exist.");
/* 301 */       return false;
/*     */     } 
/* 303 */     storage.removeFromBag(item, persist);
/* 304 */     if (sendPacket)
/* 305 */       sendDeleteItemPacket(player, storage.getStorageType(), item.getObjectId()); 
/* 306 */     return true;
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
/*     */ 
/*     */   
/*     */   public static boolean removeItemByObjectId(Player player, int itemObjId, boolean persist) {
/* 321 */     return removeItemByObjectId(player, player.getInventory(), itemObjId, persist);
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
/*     */ 
/*     */   
/*     */   public static boolean removeItemByObjectId(Player player, Storage storage, int itemObjId, boolean persist) {
/* 336 */     Item item = storage.getItemByObjId(itemObjId);
/* 337 */     if (item == null) {
/*     */       
/* 339 */       log.warn("An item from player '" + player.getName() + "' that should be removed doesn't exist.");
/* 340 */       return false;
/*     */     } 
/* 342 */     return removeItem(player, storage, item, persist);
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
/*     */   
/*     */   public static void mergeItems(Player player, int sourceItemObjId, long itemAmount, int destinationObjId, int sourceStorageType, int destinationStorageType) {
/* 356 */     if (itemAmount == 0L) {
/*     */       return;
/*     */     }
/* 359 */     if (sourceItemObjId == destinationObjId) {
/*     */       return;
/*     */     }
/* 362 */     Storage sourceStorage = player.getStorage(sourceStorageType);
/* 363 */     Storage destinationStorage = player.getStorage(destinationStorageType);
/*     */     
/* 365 */     Item sourceItem = sourceStorage.getItemByObjId(sourceItemObjId);
/* 366 */     Item destinationItem = destinationStorage.getItemByObjId(destinationObjId);
/*     */     
/* 368 */     if (sourceItem == null || destinationItem == null) {
/*     */       return;
/*     */     }
/* 371 */     if (sourceItem.getItemTemplate().getTemplateId() != destinationItem.getItemTemplate().getTemplateId()) {
/*     */       return;
/*     */     }
/* 374 */     if (sourceItem.getItemCount() < itemAmount) {
/*     */       return;
/*     */     }
/* 377 */     decreaseItemCount(player, sourceStorage, sourceItem, itemAmount);
/* 378 */     increaseItemCount(player, destinationStorage, destinationItem, itemAmount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void switchStoragesItems(Player player, int sourceStorageType, int sourceItemObjId, int replaceStorageType, int replaceItemObjId) {
/* 384 */     Storage sourceStorage = player.getStorage(sourceStorageType);
/* 385 */     Storage replaceStorage = player.getStorage(replaceStorageType);
/*     */     
/* 387 */     Item sourceItem = sourceStorage.getItemByObjId(sourceItemObjId);
/* 388 */     if (sourceItem == null) {
/*     */       return;
/*     */     }
/* 391 */     Item replaceItem = replaceStorage.getItemByObjId(replaceItemObjId);
/* 392 */     if (replaceItem == null) {
/*     */       return;
/*     */     }
/* 395 */     int sourceSlot = sourceItem.getEquipmentSlot();
/* 396 */     int replaceSlot = replaceItem.getEquipmentSlot();
/*     */     
/* 398 */     sourceItem.setEquipmentSlot(replaceSlot);
/* 399 */     replaceItem.setEquipmentSlot(sourceSlot);
/* 400 */     removeItem(player, sourceStorage, sourceItem, false);
/* 401 */     removeItem(player, replaceStorage, replaceItem, false);
/*     */     
/* 403 */     addFullItem(player, sourceStorage, replaceItem);
/* 404 */     addFullItem(player, replaceStorage, sourceItem);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean addItem(Player player, int itemId, long count) {
/* 420 */     if (count < 1L)
/* 421 */       return false; 
/* 422 */     if (OptionsConfig.LOG_ITEM)
/* 423 */       log.info(String.format("[ITEM] ID/Count - %d/%d to player %s.", new Object[] { Integer.valueOf(itemId), Long.valueOf(count), player.getName() })); 
/* 424 */     if (itemId == ItemId.KINAH.value()) {
/*     */       
/* 426 */       increaseKinah(player, count);
/* 427 */       return true;
/*     */     } 
/* 429 */     Item item = newItem(itemId, count);
/* 430 */     if (item == null)
/* 431 */       return false; 
/* 432 */     return addFullItem(player, player.getInventory(), item, true);
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
/*     */   public static boolean addFullItem(Player player, Storage storage, Item item) {
/* 445 */     return addFullItem(player, storage, item, true);
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
/*     */   public static boolean addFullItem(Player player, Storage storage, Item item, boolean merge) {
/* 458 */     ItemTemplate itemTemplate = item.getItemTemplate();
/* 459 */     if (itemTemplate == null) {
/* 460 */       return false;
/*     */     }
/* 462 */     int maxStackCount = itemTemplate.getMaxStackCount();
/* 463 */     int itemId = item.getItemId();
/*     */     
/* 465 */     if (itemId == ItemId.KINAH.value()) {
/*     */       
/* 467 */       increaseKinah(player, item.getItemCount());
/* 468 */       return true;
/*     */     } 
/*     */     
/* 471 */     if (merge) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 476 */       List<Item> existingItems = storage.getAllItemsByItemId(itemId);
/*     */       
/* 478 */       for (Item existingItem : existingItems) {
/*     */         
/* 480 */         if (item.getItemCount() < 1L) {
/*     */           break;
/*     */         }
/* 483 */         long freeCount = maxStackCount - existingItem.getItemCount();
/* 484 */         if (item.getItemCount() <= freeCount) {
/*     */           
/* 486 */           increaseItemCount(player, storage, existingItem, item.getItemCount());
/* 487 */           item.setItemCount(0L);
/*     */           
/*     */           continue;
/*     */         } 
/* 491 */         increaseItemCount(player, storage, existingItem, freeCount);
/* 492 */         item.decreaseItemCount(freeCount);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 500 */     while (!storage.isFull() && item.getItemCount() > 0L) {
/*     */ 
/*     */       
/* 503 */       if (item.getItemCount() > maxStackCount) {
/*     */         
/* 505 */         Item newItem = newItem(itemId, maxStackCount);
/* 506 */         item.decreaseItemCount(maxStackCount);
/* 507 */         storage.putToBag(newItem);
/* 508 */         addItemPacket(player, storage.getStorageType(), newItem);
/*     */         
/*     */         continue;
/*     */       } 
/* 512 */       storage.putToBag(item);
/* 513 */       addItemPacket(player, storage.getStorageType(), item);
/* 514 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 518 */     if (storage.isFull() && item.getItemCount() > 0L) {
/*     */       
/* 520 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_INVEN_ERROR);
/* 521 */       return false;
/*     */     } 
/*     */     
/* 524 */     if (item.getItemCount() < 1L) {
/*     */       
/* 526 */       item.setPersistentState(PersistentState.DELETED);
/* 527 */       ItemUpdater.getInstance().add(item);
/*     */     } 
/* 529 */     return true;
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
/*     */   
/*     */   public static void moveItem(Player player, int itemObjId, int sourceStorageType, int destinationStorageType, int slot) {
/* 543 */     Storage sourceStorage = player.getStorage(sourceStorageType);
/* 544 */     Item item = player.getStorage(sourceStorageType).getItemByObjId(itemObjId);
/*     */     
/* 546 */     if (item == null) {
/*     */       return;
/*     */     }
/* 549 */     item.setEquipmentSlot(slot);
/*     */     
/* 551 */     if (sourceStorageType == destinationStorageType) {
/*     */       
/* 553 */       sendUpdateItemPacket(player, sourceStorageType, item);
/* 554 */       sourceStorage.setPersistentState(PersistentState.UPDATE_REQUIRED);
/*     */       return;
/*     */     } 
/* 557 */     Storage destinationStorage = player.getStorage(destinationStorageType);
/* 558 */     if (removeItem(player, sourceStorage, item, false)) {
/* 559 */       addFullItem(player, destinationStorage, item);
/*     */     }
/*     */   }
/*     */   
/*     */   public static void addItemPacket(Player player, int storageType, Item item) {
/* 564 */     if (storageType == StorageType.CUBE.getId()) {
/* 565 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ADD_ITEMS(Collections.singletonList(item)));
/*     */     } else {
/* 567 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_UPDATE(item, storageType));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sendDeleteItemPacket(Player player, int storageType, int itemObjId) {
/* 572 */     if (storageType == StorageType.CUBE.getId()) {
/* 573 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DELETE_ITEM(itemObjId));
/*     */     } else {
/* 575 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DELETE_WAREHOUSE_ITEM(storageType, itemObjId));
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void sendUpdateItemPacket(Player player, int storageType, Item item) {
/* 580 */     if (storageType == StorageType.CUBE.getId()) {
/* 581 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
/*     */     } else {
/* 583 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_WAREHOUSE_ITEM(item, storageType));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ManaStone addManaStone(Item item, int itemId) {
/* 592 */     if (item == null) {
/* 593 */       return null;
/*     */     }
/* 595 */     Set<ManaStone> manaStones = item.getItemStones();
/*     */ 
/*     */     
/* 598 */     if (manaStones.size() > 6) {
/* 599 */       return null;
/*     */     }
/* 601 */     int nextSlot = 0;
/* 602 */     boolean slotFound = false;
/*     */     
/* 604 */     Iterator<ManaStone> iterator = manaStones.iterator();
/* 605 */     while (iterator.hasNext()) {
/*     */       
/* 607 */       ManaStone manaStone = iterator.next();
/* 608 */       if (nextSlot != manaStone.getSlot()) {
/*     */         
/* 610 */         slotFound = true;
/*     */         break;
/*     */       } 
/* 613 */       nextSlot++;
/*     */     } 
/*     */     
/* 616 */     if (!slotFound) {
/* 617 */       nextSlot = manaStones.size();
/*     */     }
/* 619 */     ManaStone stone = new ManaStone(item.getObjectId(), itemId, nextSlot, PersistentState.NEW);
/* 620 */     manaStones.add(stone);
/*     */     
/* 622 */     return stone;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeManastone(Player player, int itemObjId, int slotNum) {
/* 632 */     Storage inventory = player.getInventory();
/* 633 */     Item item = inventory.getItemByObjId(itemObjId);
/* 634 */     if (item == null) {
/*     */       
/* 636 */       log.warn("Item not found during manastone remove");
/*     */       
/*     */       return;
/*     */     } 
/* 640 */     if (!item.hasManaStones()) {
/*     */       
/* 642 */       log.warn("Item stone list is empty");
/*     */       
/*     */       return;
/*     */     } 
/* 646 */     Set<ManaStone> itemStones = item.getItemStones();
/*     */     
/* 648 */     if (itemStones.size() <= slotNum) {
/*     */       return;
/*     */     }
/* 651 */     int counter = 0;
/* 652 */     Iterator<ManaStone> iterator = itemStones.iterator();
/* 653 */     while (iterator.hasNext()) {
/*     */       
/* 655 */       ManaStone manaStone = iterator.next();
/* 656 */       if (counter == slotNum) {
/*     */         
/* 658 */         manaStone.setPersistentState(PersistentState.DELETED);
/* 659 */         iterator.remove();
/* 660 */         ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(Collections.singleton(manaStone));
/*     */         break;
/*     */       } 
/* 663 */       counter++;
/*     */     } 
/* 665 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeAllManastone(Player player, Item item) {
/* 674 */     if (item == null) {
/*     */       
/* 676 */       log.warn("Item not found during manastone remove");
/*     */       
/*     */       return;
/*     */     } 
/* 680 */     if (!item.hasManaStones()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 685 */     Set<ManaStone> itemStones = item.getItemStones();
/* 686 */     Iterator<ManaStone> iterator = itemStones.iterator();
/* 687 */     while (iterator.hasNext()) {
/*     */       
/* 689 */       ManaStone manaStone = iterator.next();
/* 690 */       manaStone.setPersistentState(PersistentState.DELETED);
/* 691 */       iterator.remove();
/* 692 */       ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(Collections.singleton(manaStone));
/*     */     } 
/*     */     
/* 695 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void socketGodstone(Player player, int weaponId, int stoneId) {
/* 705 */     long socketPrice = player.getPrices().getPriceForService(100000L);
/*     */     
/* 707 */     Item weaponItem = player.getInventory().getItemByObjId(weaponId);
/* 708 */     if (weaponItem == null) {
/*     */       
/* 710 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_CANNOT_GIVE_PROC_TO_EQUIPPED_ITEM);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 715 */     Item godstone = player.getInventory().getItemByObjId(stoneId);
/*     */     
/* 717 */     int godStoneItemId = godstone.getItemTemplate().getTemplateId();
/* 718 */     ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(godStoneItemId);
/* 719 */     GodstoneInfo godstoneInfo = itemTemplate.getGodstoneInfo();
/*     */     
/* 721 */     if (godstoneInfo == null) {
/*     */       
/* 723 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_NO_PROC_GIVE_ITEM);
/* 724 */       log.warn("Godstone info missing for itemid " + godStoneItemId);
/*     */       
/*     */       return;
/*     */     } 
/* 728 */     if (!decreaseKinah(player, socketPrice))
/*     */       return; 
/* 730 */     weaponItem.addGodStone(godStoneItemId);
/* 731 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_ENCHANTED_TARGET_ITEM(new DescriptionId(Integer.parseInt(weaponItem.getName()))));
/*     */     
/* 733 */     decreaseItemCount(player, godstone, 1L);
/*     */     
/* 735 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(weaponItem));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean addItems(Player player, List<QuestItems> questItems) {
/* 740 */     int needSlot = 0;
/* 741 */     for (QuestItems qi : questItems) {
/*     */       
/* 743 */       if (qi.getItemId().intValue() != ItemId.KINAH.value() && qi.getCount().intValue() != 0) {
/*     */         
/* 745 */         int stackCount = DataManager.ITEM_DATA.getItemTemplate(qi.getItemId().intValue()).getMaxStackCount();
/* 746 */         int count = qi.getCount().intValue() / stackCount;
/* 747 */         if (qi.getCount().intValue() % stackCount != 0)
/* 748 */           count++; 
/* 749 */         needSlot += count;
/*     */       } 
/*     */     } 
/* 752 */     if (needSlot > player.getInventory().getNumberOfFreeSlots()) {
/*     */       
/* 754 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);
/* 755 */       return false;
/*     */     } 
/* 757 */     for (QuestItems qi : questItems)
/* 758 */       addItem(player, qi.getItemId().intValue(), qi.getCount().intValue()); 
/* 759 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void restoreKinah(Player player) {
/* 768 */     if (player.getStorage(StorageType.CUBE.getId()).getKinahItem() == null) {
/*     */       
/* 770 */       Item kinahItem = newItem(182400001, 0L);
/*     */       
/* 772 */       onLoadHandler(player, player.getStorage(StorageType.CUBE.getId()), kinahItem);
/*     */     } 
/*     */     
/* 775 */     if (player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()).getKinahItem() == null) {
/*     */       
/* 777 */       Item kinahItem = newItem(182400001, 0L);
/* 778 */       kinahItem.setItemLocation(StorageType.ACCOUNT_WAREHOUSE.getId());
/* 779 */       onLoadHandler(player, player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()), kinahItem);
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
/*     */ 
/*     */   
/*     */   public static void onLoadHandler(Player player, Storage storage, Item item) {
/* 793 */     if (player != null && item.isEquipped()) {
/*     */       
/* 795 */       player.getEquipment().onLoadHandler(item);
/*     */     }
/* 797 */     else if (item.getItemTemplate().isKinah()) {
/*     */       
/* 799 */       storage.setKinahItem(item);
/*     */     }
/*     */     else {
/*     */       
/* 803 */       storage.putToNextAvailableSlot(item);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ItemService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */