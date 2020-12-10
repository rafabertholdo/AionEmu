package com.aionemu.gameserver.model;





















public enum PetitionType
{
  CHARACTER_STUCK(256),
  CHARACTER_RESTORATION(512),
  BUG(768),
  QUEST(1024),
  UNACCEPTABLE_BEHAVIOR(1280),
  SUGGESTION(1536),
  INQUIRY(65280);
  
  private int element;
  
  PetitionType(int id) {
    this.element = id;
  }
  
  public int getElementId() {
    return this.element;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\PetitionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
