package com.aionemu.gameserver.utils.stats.enums;





















public enum SPEED
{
  WARRIOR(6),
  GLADIATOR(6),
  TEMPLAR(6),
  SCOUT(6),
  ASSASSIN(6),
  RANGER(6),
  MAGE(6),
  SORCERER(6),
  SPIRIT_MASTER(6),
  PRIEST(6),
  CLERIC(6),
  CHANTER(6);
  
  private int value;

  
  SPEED(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
