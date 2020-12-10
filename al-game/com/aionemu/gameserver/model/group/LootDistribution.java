package com.aionemu.gameserver.model.group;























public enum LootDistribution
{
  NORMAL(0),
  ROLL_DICE(2),
  BID(3);
  
  private int id;

  
  LootDistribution(int id) {
    this.id = id;
  }

  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootDistribution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
