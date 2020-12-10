package com.aionemu.gameserver.utils.stats.enums;





















public enum MAIN_HAND_ATTACK
{
  WARRIOR(19),
  GLADIATOR(19),
  TEMPLAR(19),
  SCOUT(18),
  ASSASSIN(19),
  RANGER(18),
  MAGE(16),
  SORCERER(16),
  SPIRIT_MASTER(16),
  PRIEST(17),
  CLERIC(19),
  CHANTER(19);
  
  private int value;

  
  MAIN_HAND_ATTACK(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAIN_HAND_ATTACK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
