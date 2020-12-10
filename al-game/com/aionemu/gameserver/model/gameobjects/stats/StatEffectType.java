package com.aionemu.gameserver.model.gameobjects.stats;

public enum StatEffectType {
  SKILL_EFFECT(1), ITEM_EFFECT(2), TITLE_EFFECT(3), STONE_EFFECT(4), ENCHANT_EFFECT(6), ITEM_SET_EFFECT(5);

  private int value;

  StatEffectType(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
