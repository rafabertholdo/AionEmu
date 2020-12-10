package com.aionemu.gameserver.utils.stats.enums;





















public enum WILL
{
  WARRIOR(90),
  GLADIATOR(90),
  TEMPLAR(105),
  SCOUT(90),
  ASSASSIN(90),
  RANGER(110),
  MAGE(115),
  SORCERER(110),
  SPIRIT_MASTER(115),
  PRIEST(110),
  CLERIC(110),
  CHANTER(110);
  
  private int value;

  
  WILL(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\WILL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
