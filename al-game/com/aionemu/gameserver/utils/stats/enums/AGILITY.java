package com.aionemu.gameserver.utils.stats.enums;





















public enum AGILITY
{
  WARRIOR(100),
  GLADIATOR(100),
  TEMPLAR(110),
  SCOUT(100),
  ASSASSIN(100),
  RANGER(100),
  MAGE(95),
  SORCERER(100),
  SPIRIT_MASTER(100),
  PRIEST(100),
  CLERIC(90),
  CHANTER(90);
  
  private int value;

  
  AGILITY(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\AGILITY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
