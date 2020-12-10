package com.aionemu.gameserver.utils.stats.enums;

public enum MAIN_HAND_ATTACK {
  WARRIOR(19), GLADIATOR(19), TEMPLAR(19), SCOUT(18), ASSASSIN(19), RANGER(18), MAGE(16), SORCERER(16),
  SPIRIT_MASTER(16), PRIEST(17), CLERIC(19), CHANTER(19);

  private int value;

  MAIN_HAND_ATTACK(int value) {
    this.value = value;
  }

  public int getValue() {
    return this.value;
  }
}
