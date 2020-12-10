package com.aionemu.gameserver.utils.stats.enums;





















public enum ATTACK_SPEED
{
  WARRIOR(1500),
  GLADIATOR(1500),
  TEMPLAR(1500),
  SCOUT(1500),
  ASSASSIN(1500),
  RANGER(1500),
  MAGE(1500),
  SORCERER(1500),
  SPIRIT_MASTER(1500),
  PRIEST(1500),
  CLERIC(1500),
  CHANTER(1500);
  
  private int value;

  
  ATTACK_SPEED(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\ATTACK_SPEED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
