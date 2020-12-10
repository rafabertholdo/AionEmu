package com.aionemu.gameserver.utils.stats.enums;






















public enum POWER
{
  WARRIOR(110),
  GLADIATOR(115),
  TEMPLAR(115),
  SCOUT(100),
  ASSASSIN(110),
  RANGER(90),
  MAGE(90),
  SORCERER(90),
  SPIRIT_MASTER(90),
  PRIEST(95),
  CLERIC(105),
  CHANTER(110);
  
  private int value;

  
  POWER(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\POWER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
