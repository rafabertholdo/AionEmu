package com.aionemu.gameserver.model.legion;





















public enum LegionHistoryType
{
  CREATE(0),
  JOIN(1),
  KICK(2),
  LEVEL_UP(3),
  APPOINTED(4),
  EMBLEM_REGISTER(5),
  EMBLEM_MODIFIED(6);
  
  private byte historyType;

  
  LegionHistoryType(int historyType) {
    this.historyType = (byte)historyType;
  }






  
  public byte getHistoryId() {
    return this.historyType;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\legion\LegionHistoryType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
