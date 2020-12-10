package com.aionemu.gameserver.model.gameobjects.stats;

public enum StatModifierPriority {
  HIGH(0), MEDIUM(1), LOW(2);

  private int value;

  StatModifierPriority(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
