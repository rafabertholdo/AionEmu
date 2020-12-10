package com.aionemu.gameserver.model.gameobjects.player;

public enum StorageType {
  CUBE(0), REGULAR_WAREHOUSE(1), ACCOUNT_WAREHOUSE(2), LEGION_WAREHOUSE(3), BROKER(126), MAILBOX(127);

  private int id;

  StorageType(int id) {
    this.id = id;
  }

  public int getId() {
    return this.id;
  }
}
