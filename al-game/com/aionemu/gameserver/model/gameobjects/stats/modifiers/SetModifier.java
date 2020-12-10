package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;

public class SetModifier extends SimpleModifier {
  public int apply(int baseStat, int currentStat) {
    return this.value;
  }

  public StatModifierPriority getPriority() {
    return StatModifierPriority.HIGH;
  }

  public static SetModifier newInstance(StatEnum stat, int value, boolean isBonus) {
    SetModifier m = new SetModifier();
    m.setStat(stat);
    m.setValue(value);
    m.setBonus(isBonus);
    m.nextId();
    return m;
  }
}
