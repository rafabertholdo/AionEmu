package com.aionemu.gameserver.model;





















public enum SkillElement
{
  NONE(0),
  FIRE(1),
  WATER(2),
  WIND(3),
  EARTH(4);
  
  private int element;
  
  SkillElement(int id) {
    this.element = id;
  }
  
  public int getElementId() {
    return this.element;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\SkillElement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
