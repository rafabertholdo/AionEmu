package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.dao.ItemStoneListDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.DescriptionId;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;
import com.aionemu.gameserver.model.items.ItemId;
import com.aionemu.gameserver.model.items.ManaStone;
import com.aionemu.gameserver.model.templates.item.GodstoneInfo;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ADD_ITEMS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_WAREHOUSE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_WAREHOUSE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WAREHOUSE_UPDATE;
import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;






















public class ItemService
{
  private static Logger log = Logger.getLogger(ItemService.class);









  
  public static Item newItem(int itemId, long count) {
    ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(itemId);
    if (itemTemplate == null) {
      
      log.error("Item was not populated correctly. Item template is missing for item id: " + itemId);
      return null;
    } 
    
    int maxStackCount = itemTemplate.getMaxStackCount();
    if (count > maxStackCount && maxStackCount != 0)
    {
      count = maxStackCount;
    }

    
    return new Item(0, IDFactory.getInstance().nextId(), itemTemplate, count, false, 0);
  }







  
  public static void loadItemStones(List<Item> itemList) {
    if (itemList == null)
      return; 
    ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).load(itemList);
  }












  
  public static void splitItem(Player player, int itemObjId, long splitAmount, int slotNum, int sourceStorageType, int destinationStorageType) {
    Storage sourceStorage = player.getStorage(sourceStorageType);
    Storage destinationStorage = player.getStorage(destinationStorageType);
    
    Item itemToSplit = sourceStorage.getItemByObjId(itemObjId);
    if (itemToSplit == null) {
      
      itemToSplit = sourceStorage.getKinahItem();
      if (itemToSplit.getObjectId() != itemObjId || itemToSplit == null) {
        
        log.warn(String.format("CHECKPOINT: attempt to split null item %d %d %d", new Object[] { Integer.valueOf(itemObjId), Long.valueOf(splitAmount), Integer.valueOf(slotNum) }));

        
        return;
      } 
    } 
    
    if (itemToSplit.getItemTemplate().isKinah()) {
      
      if (!decreaseKinah(player, sourceStorage, splitAmount))
        return; 
      increaseKinah(player, destinationStorage, splitAmount);
      
      return;
    } 
    long oldItemCount = itemToSplit.getItemCount() - splitAmount;
    
    if (itemToSplit.getItemCount() < splitAmount || oldItemCount == 0L) {
      return;
    }
    Item newItem = newItem(itemToSplit.getItemId(), splitAmount);
    newItem.setEquipmentSlot(slotNum);
    
    if (addFullItem(player, destinationStorage, newItem, false))
    {
      decreaseItemCount(player, sourceStorage, itemToSplit, splitAmount);
    }
  }

  
  public static boolean decreaseKinah(Player player, long amount) {
    return decreaseKinah(player, player.getInventory(), amount);
  }

  
  public static boolean decreaseKinah(Player player, Storage storage, long amount) {
    boolean operationResult = storage.decreaseKinah(amount);
    if (operationResult)
    {
      sendUpdateItemPacket(player, storage.getStorageType(), storage.getKinahItem());
    }
    return operationResult;
  }

  
  public static void increaseItemCount(Player player, Storage storage, Item item, long amount) {
    item.increaseItemCount(amount);
    sendUpdateItemPacket(player, storage.getStorageType(), item);
  }

  
  public static void increaseKinah(Player player, Storage storage, long amount) {
    storage.increaseKinah(amount);
    sendUpdateItemPacket(player, storage.getStorageType(), storage.getKinahItem());
  }

  
  public static void increaseKinah(Player player, long amount) {
    increaseKinah(player, player.getInventory(), amount);
  }










  
  public static long decreaseItemCount(Player player, Item item, long count) {
    return decreaseItemCount(player, player.getInventory(), item, count);
  }

  
  public static boolean decreaseItemCountByItemId(Player player, int itemId, long count) {
    return decreaseItemCountByItemId(player, player.getInventory(), itemId, count);
  }










  
  public static boolean decreaseItemCountByItemId(Player player, Storage storage, int itemId, long count) {
    if (count < 1L) {
      return false;
    }
    List<Item> items = storage.getItemsByItemId(itemId);
    
    for (Item item : items) {
      
      count = decreaseItemCount(player, storage, item, count);
      
      if (count == 0L)
        break; 
    } 
    return (count >= 0L);
  }










  
  public static long decreaseItemCount(Player player, Storage storage, Item item, long count) {
    long itemCount = item.getItemCount();
    if (itemCount >= count) {
      
      item.decreaseItemCount(count);
      count = 0L;
    }
    else {
      
      item.decreaseItemCount(itemCount);
      count -= itemCount;
    } 
    if (item.getItemCount() == 0L)
    {
      removeItem(player, storage, item, true);
    }
    sendUpdateItemPacket(player, storage.getStorageType(), item);
    
    return count;
  }

  
  public static boolean removeItemFromInventory(Player player, Item item, boolean persist) {
    return removeItem(player, player.getInventory(), item, persist);
  }

  
  public static boolean removeItemFromInventoryByItemId(Player player, int itemId) {
    Storage storage = player.getInventory();
    boolean sucess = false;
    List<Item> items = storage.getItemsByItemId(itemId);
    
    for (Item item : items)
    {
      sucess |= removeItem(player, player.getInventory(), item, true);
    }
    return sucess;
  }

  
  public static boolean removeItemFromInventory(Player player, Item item) {
    return removeItem(player, player.getInventory(), item, true);
  }

  
  public static boolean removeItem(Player player, Storage storage, Item item, boolean persist) {
    return removeItem(player, storage, item, persist, true);
  }










  
  public static boolean removeItem(Player player, Storage storage, Item item, boolean persist, boolean sendPacket) {
    if (item == null) {
      
      log.warn("An item from player '" + player.getName() + "' that should be removed doesn't exist.");
      return false;
    } 
    storage.removeFromBag(item, persist);
    if (sendPacket)
      sendDeleteItemPacket(player, storage.getStorageType(), item.getObjectId()); 
    return true;
  }











  
  public static boolean removeItemByObjectId(Player player, int itemObjId, boolean persist) {
    return removeItemByObjectId(player, player.getInventory(), itemObjId, persist);
  }











  
  public static boolean removeItemByObjectId(Player player, Storage storage, int itemObjId, boolean persist) {
    Item item = storage.getItemByObjId(itemObjId);
    if (item == null) {
      
      log.warn("An item from player '" + player.getName() + "' that should be removed doesn't exist.");
      return false;
    } 
    return removeItem(player, storage, item, persist);
  }










  
  public static void mergeItems(Player player, int sourceItemObjId, long itemAmount, int destinationObjId, int sourceStorageType, int destinationStorageType) {
    if (itemAmount == 0L) {
      return;
    }
    if (sourceItemObjId == destinationObjId) {
      return;
    }
    Storage sourceStorage = player.getStorage(sourceStorageType);
    Storage destinationStorage = player.getStorage(destinationStorageType);
    
    Item sourceItem = sourceStorage.getItemByObjId(sourceItemObjId);
    Item destinationItem = destinationStorage.getItemByObjId(destinationObjId);
    
    if (sourceItem == null || destinationItem == null) {
      return;
    }
    if (sourceItem.getItemTemplate().getTemplateId() != destinationItem.getItemTemplate().getTemplateId()) {
      return;
    }
    if (sourceItem.getItemCount() < itemAmount) {
      return;
    }
    decreaseItemCount(player, sourceStorage, sourceItem, itemAmount);
    increaseItemCount(player, destinationStorage, destinationItem, itemAmount);
  }


  
  public static void switchStoragesItems(Player player, int sourceStorageType, int sourceItemObjId, int replaceStorageType, int replaceItemObjId) {
    Storage sourceStorage = player.getStorage(sourceStorageType);
    Storage replaceStorage = player.getStorage(replaceStorageType);
    
    Item sourceItem = sourceStorage.getItemByObjId(sourceItemObjId);
    if (sourceItem == null) {
      return;
    }
    Item replaceItem = replaceStorage.getItemByObjId(replaceItemObjId);
    if (replaceItem == null) {
      return;
    }
    int sourceSlot = sourceItem.getEquipmentSlot();
    int replaceSlot = replaceItem.getEquipmentSlot();
    
    sourceItem.setEquipmentSlot(replaceSlot);
    replaceItem.setEquipmentSlot(sourceSlot);
    removeItem(player, sourceStorage, sourceItem, false);
    removeItem(player, replaceStorage, replaceItem, false);
    
    addFullItem(player, sourceStorage, replaceItem);
    addFullItem(player, replaceStorage, sourceItem);
  }












  
  public static boolean addItem(Player player, int itemId, long count) {
    if (count < 1L)
      return false; 
    if (OptionsConfig.LOG_ITEM)
      log.info(String.format("[ITEM] ID/Count - %d/%d to player %s.", new Object[] { Integer.valueOf(itemId), Long.valueOf(count), player.getName() })); 
    if (itemId == ItemId.KINAH.value()) {
      
      increaseKinah(player, count);
      return true;
    } 
    Item item = newItem(itemId, count);
    if (item == null)
      return false; 
    return addFullItem(player, player.getInventory(), item, true);
  }









  
  public static boolean addFullItem(Player player, Storage storage, Item item) {
    return addFullItem(player, storage, item, true);
  }









  
  public static boolean addFullItem(Player player, Storage storage, Item item, boolean merge) {
    ItemTemplate itemTemplate = item.getItemTemplate();
    if (itemTemplate == null) {
      return false;
    }
    int maxStackCount = itemTemplate.getMaxStackCount();
    int itemId = item.getItemId();
    
    if (itemId == ItemId.KINAH.value()) {
      
      increaseKinah(player, item.getItemCount());
      return true;
    } 
    
    if (merge) {



      
      List<Item> existingItems = storage.getAllItemsByItemId(itemId);
      
      for (Item existingItem : existingItems) {
        
        if (item.getItemCount() < 1L) {
          break;
        }
        long freeCount = maxStackCount - existingItem.getItemCount();
        if (item.getItemCount() <= freeCount) {
          
          increaseItemCount(player, storage, existingItem, item.getItemCount());
          item.setItemCount(0L);
          
          continue;
        } 
        increaseItemCount(player, storage, existingItem, freeCount);
        item.decreaseItemCount(freeCount);
      } 
    } 




    
    while (!storage.isFull() && item.getItemCount() > 0L) {

      
      if (item.getItemCount() > maxStackCount) {
        
        Item newItem = newItem(itemId, maxStackCount);
        item.decreaseItemCount(maxStackCount);
        storage.putToBag(newItem);
        addItemPacket(player, storage.getStorageType(), newItem);
        
        continue;
      } 
      storage.putToBag(item);
      addItemPacket(player, storage.getStorageType(), item);
      return true;
    } 

    
    if (storage.isFull() && item.getItemCount() > 0L) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_DICE_INVEN_ERROR);
      return false;
    } 
    
    if (item.getItemCount() < 1L) {
      
      item.setPersistentState(PersistentState.DELETED);
      ItemUpdater.getInstance().add(item);
    } 
    return true;
  }










  
  public static void moveItem(Player player, int itemObjId, int sourceStorageType, int destinationStorageType, int slot) {
    Storage sourceStorage = player.getStorage(sourceStorageType);
    Item item = player.getStorage(sourceStorageType).getItemByObjId(itemObjId);
    
    if (item == null) {
      return;
    }
    item.setEquipmentSlot(slot);
    
    if (sourceStorageType == destinationStorageType) {
      
      sendUpdateItemPacket(player, sourceStorageType, item);
      sourceStorage.setPersistentState(PersistentState.UPDATE_REQUIRED);
      return;
    } 
    Storage destinationStorage = player.getStorage(destinationStorageType);
    if (removeItem(player, sourceStorage, item, false)) {
      addFullItem(player, destinationStorage, item);
    }
  }
  
  public static void addItemPacket(Player player, int storageType, Item item) {
    if (storageType == StorageType.CUBE.getId()) {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ADD_ITEMS(Collections.singletonList(item)));
    } else {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WAREHOUSE_UPDATE(item, storageType));
    } 
  }
  
  private static void sendDeleteItemPacket(Player player, int storageType, int itemObjId) {
    if (storageType == StorageType.CUBE.getId()) {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DELETE_ITEM(itemObjId));
    } else {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_DELETE_WAREHOUSE_ITEM(storageType, itemObjId));
    } 
  }
  
  private static void sendUpdateItemPacket(Player player, int storageType, Item item) {
    if (storageType == StorageType.CUBE.getId()) {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
    } else {
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_WAREHOUSE_ITEM(item, storageType));
    } 
  }




  
  public static ManaStone addManaStone(Item item, int itemId) {
    if (item == null) {
      return null;
    }
    Set<ManaStone> manaStones = item.getItemStones();

    
    if (manaStones.size() > 6) {
      return null;
    }
    int nextSlot = 0;
    boolean slotFound = false;
    
    Iterator<ManaStone> iterator = manaStones.iterator();
    while (iterator.hasNext()) {
      
      ManaStone manaStone = iterator.next();
      if (nextSlot != manaStone.getSlot()) {
        
        slotFound = true;
        break;
      } 
      nextSlot++;
    } 
    
    if (!slotFound) {
      nextSlot = manaStones.size();
    }
    ManaStone stone = new ManaStone(item.getObjectId(), itemId, nextSlot, PersistentState.NEW);
    manaStones.add(stone);
    
    return stone;
  }






  
  public static void removeManastone(Player player, int itemObjId, int slotNum) {
    Storage inventory = player.getInventory();
    Item item = inventory.getItemByObjId(itemObjId);
    if (item == null) {
      
      log.warn("Item not found during manastone remove");
      
      return;
    } 
    if (!item.hasManaStones()) {
      
      log.warn("Item stone list is empty");
      
      return;
    } 
    Set<ManaStone> itemStones = item.getItemStones();
    
    if (itemStones.size() <= slotNum) {
      return;
    }
    int counter = 0;
    Iterator<ManaStone> iterator = itemStones.iterator();
    while (iterator.hasNext()) {
      
      ManaStone manaStone = iterator.next();
      if (counter == slotNum) {
        
        manaStone.setPersistentState(PersistentState.DELETED);
        iterator.remove();
        ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(Collections.singleton(manaStone));
        break;
      } 
      counter++;
    } 
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
  }





  
  public static void removeAllManastone(Player player, Item item) {
    if (item == null) {
      
      log.warn("Item not found during manastone remove");
      
      return;
    } 
    if (!item.hasManaStones()) {
      return;
    }

    
    Set<ManaStone> itemStones = item.getItemStones();
    Iterator<ManaStone> iterator = itemStones.iterator();
    while (iterator.hasNext()) {
      
      ManaStone manaStone = iterator.next();
      manaStone.setPersistentState(PersistentState.DELETED);
      iterator.remove();
      ((ItemStoneListDAO)DAOManager.getDAO(ItemStoneListDAO.class)).store(Collections.singleton(manaStone));
    } 
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(item));
  }






  
  public static void socketGodstone(Player player, int weaponId, int stoneId) {
    long socketPrice = player.getPrices().getPriceForService(100000L);
    
    Item weaponItem = player.getInventory().getItemByObjId(weaponId);
    if (weaponItem == null) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_CANNOT_GIVE_PROC_TO_EQUIPPED_ITEM);
      
      return;
    } 
    
    Item godstone = player.getInventory().getItemByObjId(stoneId);
    
    int godStoneItemId = godstone.getItemTemplate().getTemplateId();
    ItemTemplate itemTemplate = DataManager.ITEM_DATA.getItemTemplate(godStoneItemId);
    GodstoneInfo godstoneInfo = itemTemplate.getGodstoneInfo();
    
    if (godstoneInfo == null) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_NO_PROC_GIVE_ITEM);
      log.warn("Godstone info missing for itemid " + godStoneItemId);
      
      return;
    } 
    if (!decreaseKinah(player, socketPrice))
      return; 
    weaponItem.addGodStone(godStoneItemId);
    PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_GIVE_ITEM_PROC_ENCHANTED_TARGET_ITEM(new DescriptionId(Integer.parseInt(weaponItem.getName()))));
    
    decreaseItemCount(player, godstone, 1L);
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_UPDATE_ITEM(weaponItem));
  }

  
  public static boolean addItems(Player player, List<QuestItems> questItems) {
    int needSlot = 0;
    for (QuestItems qi : questItems) {
      
      if (qi.getItemId().intValue() != ItemId.KINAH.value() && qi.getCount().intValue() != 0) {
        
        int stackCount = DataManager.ITEM_DATA.getItemTemplate(qi.getItemId().intValue()).getMaxStackCount();
        int count = qi.getCount().intValue() / stackCount;
        if (qi.getCount().intValue() % stackCount != 0)
          count++; 
        needSlot += count;
      } 
    } 
    if (needSlot > player.getInventory().getNumberOfFreeSlots()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.MSG_FULL_INVENTORY);
      return false;
    } 
    for (QuestItems qi : questItems)
      addItem(player, qi.getItemId().intValue(), qi.getCount().intValue()); 
    return true;
  }





  
  public static void restoreKinah(Player player) {
    if (player.getStorage(StorageType.CUBE.getId()).getKinahItem() == null) {
      
      Item kinahItem = newItem(182400001, 0L);
      
      onLoadHandler(player, player.getStorage(StorageType.CUBE.getId()), kinahItem);
    } 
    
    if (player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()).getKinahItem() == null) {
      
      Item kinahItem = newItem(182400001, 0L);
      kinahItem.setItemLocation(StorageType.ACCOUNT_WAREHOUSE.getId());
      onLoadHandler(player, player.getStorage(StorageType.ACCOUNT_WAREHOUSE.getId()), kinahItem);
    } 
  }









  
  public static void onLoadHandler(Player player, Storage storage, Item item) {
    if (player != null && item.isEquipped()) {
      
      player.getEquipment().onLoadHandler(item);
    }
    else if (item.getItemTemplate().isKinah()) {
      
      storage.setKinahItem(item);
    }
    else {
      
      storage.putToNextAvailableSlot(item);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ItemService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
