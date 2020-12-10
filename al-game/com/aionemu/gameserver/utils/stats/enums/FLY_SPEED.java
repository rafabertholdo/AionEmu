package com.aionemu.gameserver.utils.stats.enums;





















public enum FLY_SPEED
{
  WARRIOR(9),
  GLADIATOR(9),
  TEMPLAR(9),
  SCOUT(9),
  ASSASSIN(9),
  RANGER(9),
  MAGE(9),
  SORCERER(9),
  SPIRIT_MASTER(9),
  PRIEST(9),
  CLERIC(9),
  CHANTER(9);
  
  private int value;

  
  FLY_SPEED(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\FLY_SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
