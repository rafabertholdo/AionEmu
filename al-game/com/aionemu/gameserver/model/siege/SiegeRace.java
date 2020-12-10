package com.aionemu.gameserver.model.siege;





















public enum SiegeRace
{
  ELYOS(0),
  ASMODIANS(1),
  BALAUR(2);
  
  private int raceId;
  
  SiegeRace(int id) {
    this.raceId = id;
  }

  
  public int getRaceId() {
    return this.raceId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\SiegeRace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
