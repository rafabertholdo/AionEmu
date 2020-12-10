package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.items.ItemStorage;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Storage {
  private int ownerId;
  protected ItemStorage storage;
  private Item kinahItem;
  protected int storageType;
  protected Queue<Item> deletedItems = new ConcurrentLinkedQueue<Item>();

  private PersistentState persistentState = PersistentState.UPDATED;

  public Storage(StorageType storageType) {
    switch (storageType) {

      case CUBE:
        this.storage = new ItemStorage(109);
        this.storageType = storageType.getId();
        break;
      case REGULAR_WAREHOUSE:
        this.storage = new ItemStorage(104);
        this.storageType = storageType.getId();
        break;
      case ACCOUNT_WAREHOUSE:
        this.storage = new ItemStorage(17);
        this.storageType = storageType.getId();
        break;
      case LEGION_WAREHOUSE:
        this.storage = new ItemStorage(25);
        this.storageType = storageType.getId();
        break;
    }
  }

  public int getOwnerId() {
    return this.ownerId;
  }

  public void setOwnerId(int ownerId) {
    this.ownerId = ownerId;
  }

  public Item getKinahItem() {
    return this.kinahItem;
  }

  public void setKinahItem(Item kinahItem) {
    this.kinahItem = kinahItem;
  }

  public int getStorageType() {
    return this.storageType;
  }

  public void increaseKinah(long amount) {
    this.kinahItem.increaseItemCount(amount);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public boolean decreaseKinah(long amount) {
    boolean operationResult = this.kinahItem.decreaseItemCount(amount);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
    return operationResult;
  }

  public Item putToBag(Item item) {
    Item resultItem = this.storage.putToNextAvailableSlot(item);
    if (resultItem != null) {
      resultItem.setItemLocation(this.storageType);
    }
    item.setOwnerId(this.ownerId);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
    return resultItem;
  }

  public void removeFromBag(Item item, boolean persist) {
    boolean operationResult = this.storage.removeItemFromStorage(item);
    if (operationResult && persist) {

      item.setPersistentState(PersistentState.DELETED);
      this.deletedItems.add(item);
      setPersistentState(PersistentState.UPDATE_REQUIRED);
    }
  }

  public Item getFirstItemByItemId(int itemId) {
    List<Item> items = this.storage.getItemsFromStorageByItemId(itemId);
    if (items.size() == 0)
      return null;
    return items.get(0);
  }

  public List<Item> getAllItems() {
    List<Item> allItems = new ArrayList<Item>();
    if (this.kinahItem != null)
      allItems.add(this.kinahItem);
    allItems.addAll(this.storage.getStorageItems());
    return allItems;
  }

  public Queue<Item> getDeletedItems() {
    return this.deletedItems;
  }

  public List<Item> getAllItemsByItemId(int itemId) {
    List<Item> allItemsByItemId = new ArrayList<Item>();

    for (Item item : this.storage.getStorageItems()) {

      if (item.getItemTemplate().getTemplateId() == itemId)
        allItemsByItemId.add(item);
    }
    return allItemsByItemId;
  }

  public List<Item> getStorageItems() {
    return this.storage.getStorageItems();
  }

  public Item getItemByObjId(int value) {
    return this.storage.getItemFromStorageByItemObjId(value);
  }

  public List<Item> getItemsByItemId(int value) {
    return this.storage.getItemsFromStorageByItemId(value);
  }

  public long getItemCountByItemId(int itemId) {
    List<Item> items = getItemsByItemId(itemId);
    long count = 0L;
    for (Item item : items) {
      count += item.getItemCount();
    }
    return count;
  }

  public boolean isFull() {
    return this.storage.isFull();
  }

  public int getNumberOfFreeSlots() {
    return this.storage.getNumberOfFreeSlots();
  }

  public void setLimit(int limit) {
    this.storage.setLimit(limit);
  }

  public int getLimit() {
    return this.storage.getLimit();
  }

  public PersistentState getPersistentState() {
    return this.persistentState;
  }

  public void setPersistentState(PersistentState persistentState) {
    this.persistentState = persistentState;
  }

  public void increaseItemCount(Item item, long count) {
    item.increaseItemCount(count);
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }

  public Item putToNextAvailableSlot(Item item) {
    return this.storage.putToNextAvailableSlot(item);
  }

  public int size() {
    return this.storage.size();
  }
}
