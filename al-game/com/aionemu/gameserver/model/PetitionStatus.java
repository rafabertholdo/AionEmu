package com.aionemu.gameserver.model;





















public enum PetitionStatus
{
  PENDING(0),
  IN_PROGRESS(1),
  REPLIED(2);
  
  private int element;
  
  PetitionStatus(int id) {
    this.element = id;
  }
  
  public int getElementId() {
    return this.element;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\PetitionStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
