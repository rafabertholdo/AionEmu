package com.aionemu.gameserver.model.gameobjects.stats.id;

import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;





















public class ItemStatEffectId
  extends StatEffectId
{
  private int slot;
  
  private ItemStatEffectId(int id, int slot) {
    super(id, StatEffectType.ITEM_EFFECT);
    this.slot = slot;
  }

  
  public static ItemStatEffectId getInstance(int id, int slot) {
    return new ItemStatEffectId(id, slot);
  }


  
  public boolean equals(Object o) {
    boolean result = super.equals(o);
    result = (result && o != null);
    result = (result && o instanceof ItemStatEffectId);
    result = (result && ((ItemStatEffectId)o).slot == this.slot);
    return result;
  }


  
  public int compareTo(StatEffectId o) {
    int result = super.compareTo(o);
    if (result == 0)
    {
      if (o instanceof ItemStatEffectId)
      {
        result = this.slot - ((ItemStatEffectId)o).slot;
      }
    }
    return result;
  }


  
  public String toString() {
    String str = super.toString() + ",slot:" + this.slot;
    return str;
  }

  
  public int getSlot() {
    return this.slot;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\id\ItemStatEffectId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
