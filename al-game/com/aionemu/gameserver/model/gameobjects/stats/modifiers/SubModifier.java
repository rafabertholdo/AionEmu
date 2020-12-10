package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;

public class SubModifier extends SimpleModifier {
  public int apply(int baseStat, int currentStat) {
    if (isBonus()) {
      return Math.round((-1 * this.value));
    }

    return Math.round((baseStat - this.value));
  }

  public StatModifierPriority getPriority() {
    return StatModifierPriority.LOW;
  }
}
