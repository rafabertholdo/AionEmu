package com.aionemu.gameserver.model.alliance;





















public enum PlayerAllianceEvent
{
  LEAVE(0),
  LEAVE_TIMEOUT(0),
  BANNED(0),
  
  MOVEMENT(1),
  
  DISCONNECTED(3),

  
  UNK(9),
  
  RECONNECT(13),
  ENTER(13),
  UPDATE(13),
  MEMBER_GROUP_CHANGE(13),

  
  APPOINT_VICE_CAPTAIN(13),
  DEMOTE_VICE_CAPTAIN(13),
  APPOINT_CAPTAIN(13);
  
  private int id;

  
  PlayerAllianceEvent(int id) {
    this.id = id;
  }

  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\alliance\PlayerAllianceEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
