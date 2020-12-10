package com.aionemu.gameserver.model.gameobjects.stats.id;

import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;

public class ItemSetStatEffectId extends StatEffectId {
  private int setpart;

  private ItemSetStatEffectId(int id, int setpart) {
    super(id, StatEffectType.ITEM_SET_EFFECT);
    this.setpart = setpart;
  }

  public static ItemSetStatEffectId getInstance(int id, int setpart) {
    return new ItemSetStatEffectId(id, setpart);
  }

  public boolean equals(Object o) {
    boolean result = super.equals(o);
    result = (result && o != null);
    result = (result && o instanceof ItemSetStatEffectId);
    result = (result && ((ItemSetStatEffectId) o).setpart == this.setpart);
    return result;
  }

  public int compareTo(StatEffectId o) {
    int result = super.compareTo(o);
    if (result == 0) {
      if (o instanceof ItemSetStatEffectId) {
        result = this.setpart - ((ItemSetStatEffectId) o).setpart;
      }
    }
    return result;
  }

  public String toString() {
    String str = super.toString() + ",parts:" + this.setpart;
    return str;
  }
}
