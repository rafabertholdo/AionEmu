package com.aionemu.gameserver.model.gameobjects.stats;

import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import javolution.util.FastList;
import javolution.util.FastMap;

public class StatModifiers {
  private FastMap<StatModifierPriority, FastList<StatModifier>> modifiers = new FastMap();

  public boolean add(StatModifier modifier) {
    if (!this.modifiers.containsKey(modifier.getPriority())) {
      this.modifiers.put(modifier.getPriority(), new FastList());
    }
    return ((FastList) this.modifiers.get(modifier.getPriority())).add(modifier);
  }

  public FastList<StatModifier> getModifiers(StatModifierPriority priority) {
    if (!this.modifiers.containsKey(priority)) {
      this.modifiers.put(priority, new FastList());
    }
    return (FastList<StatModifier>) this.modifiers.get(priority);
  }
}
