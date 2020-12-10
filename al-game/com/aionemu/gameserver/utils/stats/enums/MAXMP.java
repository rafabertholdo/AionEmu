package com.aionemu.gameserver.utils.stats.enums;





















public enum MAXMP
{
  WARRIOR(100),
  GLADIATOR(100),
  TEMPLAR(100),
  SCOUT(100),
  ASSASSIN(100),
  RANGER(100),
  MAGE(100),
  SORCERER(100),
  SPIRIT_MASTER(100),
  PRIEST(100),
  CLERIC(100),
  CHANTER(100);
  
  private int value;

  
  MAXMP(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAXMP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
