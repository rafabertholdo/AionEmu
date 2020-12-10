package com.aionemu.gameserver.model.siege;






















public enum SiegeType
{
  FORTRESS(0),
  ARTIFACT(1),

  
  BOSSRAID_LIGHT(2),
  BOSSRAID_DARK(3),

  
  INDUN(4),
  UNDERPASS(5);
  
  private int typeId;
  
  SiegeType(int id) {
    this.typeId = id;
  }

  
  public int getTypeId() {
    return this.typeId;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\SiegeType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
