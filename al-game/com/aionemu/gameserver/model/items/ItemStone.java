package com.aionemu.gameserver.model.items;

import com.aionemu.gameserver.model.gameobjects.PersistentState;

























public class ItemStone
{
  private int itemObjId;
  private int itemId;
  private int slot;
  private PersistentState persistentState;
  private ItemStoneType itemStoneType;
  
  public enum ItemStoneType
  {
    MANASTONE,
    GODSTONE;
  }









  
  public ItemStone(int itemObjId, int itemId, int slot, ItemStoneType itemStoneType, PersistentState persistentState) {
    this.itemObjId = itemObjId;
    this.itemId = itemId;
    this.slot = slot;
    this.persistentState = persistentState;
  }




  
  public int getItemObjId() {
    return this.itemObjId;
  }




  
  public int getItemId() {
    return this.itemId;
  }




  
  public int getSlot() {
    return this.slot;
  }




  
  public void setSlot(int slot) {
    this.slot = slot;
    setPersistentState(PersistentState.UPDATE_REQUIRED);
  }




  
  public PersistentState getPersistentState() {
    return this.persistentState;
  }





  
  public void setPersistentState(PersistentState persistentState) {
    switch (persistentState) {
      
      case DELETED:
        if (this.persistentState == PersistentState.NEW) {
          this.persistentState = PersistentState.NOACTION;
        } else {
          this.persistentState = PersistentState.DELETED;
        }  return;
      case UPDATE_REQUIRED:
        if (this.persistentState == PersistentState.NEW)
          return;  break;
    } 
    this.persistentState = persistentState;
  }





  
  public ItemStoneType getItemStoneType() {
    return this.itemStoneType;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\items\ItemStone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
