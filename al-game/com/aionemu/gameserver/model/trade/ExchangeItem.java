package com.aionemu.gameserver.model.trade;

import com.aionemu.gameserver.model.gameobjects.Item;

public class ExchangeItem {
  private int itemObjId;
  private long itemCount;
  private int itemDesc;
  private Item item;

  public ExchangeItem(int itemObjId, long itemCount, Item item) {
    this.itemObjId = itemObjId;
    this.itemCount = itemCount;
    this.item = item;
    this.itemDesc = item.getItemTemplate().getNameId();
  }

  public void setItem(Item item) {
    this.item = item;
  }

  public void addCount(long countToAdd) {
    this.itemCount += countToAdd;
    this.item.setItemCount(this.itemCount);
  }

  public Item getItem() {
    return this.item;
  }

  public int getItemObjId() {
    return this.itemObjId;
  }

  public long getItemCount() {
    return this.itemCount;
  }

  public int getItemDesc() {
    return this.itemDesc;
  }
}
