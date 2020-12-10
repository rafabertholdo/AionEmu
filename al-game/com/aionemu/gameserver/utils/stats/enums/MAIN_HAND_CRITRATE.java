package com.aionemu.gameserver.utils.stats.enums;





















public enum MAIN_HAND_CRITRATE
{
  WARRIOR(2),
  GLADIATOR(2),
  TEMPLAR(2),
  SCOUT(3),
  ASSASSIN(3),
  RANGER(3),
  MAGE(1),
  SORCERER(2),
  SPIRIT_MASTER(2),
  PRIEST(2),
  CLERIC(2),
  CHANTER(1);
  
  private int value;

  
  MAIN_HAND_CRITRATE(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAIN_HAND_CRITRATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
