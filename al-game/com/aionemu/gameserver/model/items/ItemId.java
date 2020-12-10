package com.aionemu.gameserver.model.items;

public enum ItemId {
  KINAH(182400001);

  private int itemId;

  ItemId(int itemId) {
    this.itemId = itemId;
  }

  public int value() {
    return this.itemId;
  }
}
