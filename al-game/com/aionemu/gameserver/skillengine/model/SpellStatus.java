package com.aionemu.gameserver.skillengine.model;



































public enum SpellStatus
{
  NONE(0),
  STUMBLE(1),
  STAGGER(2),
  OPENAERIAL(4),
  CLOSEAERIAL(8),
  SPIN(16),
  BLOCK(32),
  PARRY(64),
  DODGE(128),
  RESIST(256);
  
  private int id;

  
  SpellStatus(int id) {
    this.id = id;
  }




  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SpellStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
