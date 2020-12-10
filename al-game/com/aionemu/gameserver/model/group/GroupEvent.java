package com.aionemu.gameserver.model.group;























public enum GroupEvent
{
  LEAVE(0),
  MOVEMENT(1),
  ENTER(13),
  UPDATE(13),
  CHANGELEADER(13);
  
  private int id;

  
  GroupEvent(int id) {
    this.id = id;
  }

  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\GroupEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
