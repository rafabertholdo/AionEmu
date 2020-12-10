package com.aionemu.gameserver.model.gameobjects.stats.id;

import com.aionemu.gameserver.model.gameobjects.stats.StatEffectType;

public class EnchantStatEffectId extends StatEffectId {
  private int slot;

  private EnchantStatEffectId(int id, int slot) {
    super(id, StatEffectType.ENCHANT_EFFECT);
    this.slot = slot;
  }

  public static EnchantStatEffectId getInstance(int id, int slot) {
    return new EnchantStatEffectId(id, slot);
  }

  public boolean equals(Object o) {
    boolean result = super.equals(o);
    result = (result && o != null);
    result = (result && o instanceof EnchantStatEffectId);
    result = (result && ((EnchantStatEffectId) o).slot == this.slot);
    return result;
  }

  public int compareTo(StatEffectId o) {
    int result = super.compareTo(o);
    if (result == 0) {
      if (o instanceof EnchantStatEffectId) {
        result = this.slot - ((EnchantStatEffectId) o).slot;
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
