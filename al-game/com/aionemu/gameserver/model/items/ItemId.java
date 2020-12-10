package com.aionemu.gameserver.model.items;





















public enum ItemId
{
  KINAH(182400001);

  
  private int itemId;

  
  ItemId(int itemId) {
    this.itemId = itemId;
  }

  
  public int value() {
    return this.itemId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
