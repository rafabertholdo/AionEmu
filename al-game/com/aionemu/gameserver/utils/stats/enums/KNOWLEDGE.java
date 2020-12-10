package com.aionemu.gameserver.utils.stats.enums;





















public enum KNOWLEDGE
{
  WARRIOR(90),
  GLADIATOR(90),
  TEMPLAR(90),
  SCOUT(90),
  ASSASSIN(90),
  RANGER(120),
  MAGE(115),
  SORCERER(120),
  SPIRIT_MASTER(115),
  PRIEST(100),
  CLERIC(105),
  CHANTER(105);
  
  private int value;

  
  KNOWLEDGE(int value) {
    this.value = value;
  }

  
  public int getValue() {
    return this.value;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\KNOWLEDGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
