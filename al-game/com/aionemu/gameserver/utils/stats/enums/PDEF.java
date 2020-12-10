package com.aionemu.gameserver.utils.stats.enums;

public enum PDEF {
  WARRIOR(100), GLADIATOR(100), TEMPLAR(100), SCOUT(100), ASSASSIN(100), RANGER(100), MAGE(100), SORCERER(100),
  SPIRIT_MASTER(100), PRIEST(100), CLERIC(100), CHANTER(100);

  private int value;

  PDEF(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
