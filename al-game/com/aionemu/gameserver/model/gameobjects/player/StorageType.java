package com.aionemu.gameserver.model.gameobjects.player;










public enum StorageType
{
  CUBE(0),
  REGULAR_WAREHOUSE(1),
  ACCOUNT_WAREHOUSE(2),
  LEGION_WAREHOUSE(3),
  BROKER(126),
  MAILBOX(127);
  
  private int id;

  
  StorageType(int id) {
    this.id = id;
  }

  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\StorageType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
