package com.aionemu.gameserver.model;






















public enum NpcType
{
  ATTACKABLE(0),
  
  AGGRESSIVE(8),
  
  NON_ATTACKABLE(38),
  
  RESURRECT(38),
  
  POSTBOX(38),
  
  USEITEM(38),
  
  PORTAL(38),
  
  ARTIFACT(38),
  
  ARTIFACT_PROTECTOR(0);
  
  private int someClientSideId;

  
  NpcType(int id) {
    this.someClientSideId = id;
  }

  
  public int getId() {
    return this.someClientSideId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\NpcType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
