package com.aionemu.gameserver.utils.stats.enums;

public enum ACCURACY {
  WARRIOR(100), GLADIATOR(100), TEMPLAR(100), SCOUT(110), ASSASSIN(110), RANGER(100), MAGE(95), SORCERER(100),
  SPIRIT_MASTER(100), PRIEST(100), CLERIC(100), CHANTER(90);

  private int value;

  ACCURACY(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
