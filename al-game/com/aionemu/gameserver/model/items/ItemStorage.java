package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javolution.util.FastList;

public class ItemStorage {
  public static final int FIRST_AVAILABLE_SLOT = 65535;
  private FastList<Item> storageItems;
  private int limit = 0;

  public ItemStorage(int limit) {
    this.limit = limit;
    this.storageItems = new FastList(limit);
  }

  public List<Item> getStorageItems() {
    return Collections.unmodifiableList((List<? extends Item>) this.storageItems);
  }

  public int getLimit() {
    return this.limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public Item getItemFromStorageByItemId(int itemId) {
    for (Item item : this.storageItems) {

      ItemTemplate itemTemplate = item.getItemTemplate();
      if (itemTemplate.getTemplateId() == itemId) {
        return item;
      }
    }

    return null;
  }

  public List<Item> getItemsFromStorageByItemId(int itemId) {
    List<Item> itemList = new ArrayList<Item>();

    for (Item item : this.storageItems) {

      ItemTemplate itemTemplate = item.getItemTemplate();
      if (itemTemplate.getTemplateId() == itemId) {
        itemList.add(item);
      }
    }

    return itemList;
  }

  public Item getItemFromStorageByItemObjId(int itemObjId) {
    for (Item item : this.storageItems) {

      if (item.getObjectId() == itemObjId) {
        return item;
      }
    }
    return null;
  }

  public int getSlotIdByItemId(int itemId) {
    for (Item item : this.storageItems) {

      ItemTemplate itemTemplate = item.getItemTemplate();
      if (itemTemplate.getTemplateId() == itemId) {
        return item.getEquipmentSlot();
      }
    }
    return -1;
  }

  public int getSlotIdByObjId(int objId) {
    for (Item item : this.storageItems) {

      if (item.getObjectId() == objId) {
        return item.getEquipmentSlot();
      }
    }

    return -1;
  }

  public int getNextAvailableSlot() {
    return 65535;
  }

  public Item putToNextAvailableSlot(Item item) {
    if (!isFull() && this.storageItems.add(item)) {
      return item;
    }
    return null;
  }

  public boolean removeItemFromStorage(Item item) {
    return this.storageItems.remove(item);
  }

  public boolean isFull() {
    return (this.storageItems.size() >= this.limit);
  }

  public int getNumberOfFreeSlots() {
    return this.limit - this.storageItems.size();
  }

  public int size() {
    return this.storageItems.size();
  }
}
