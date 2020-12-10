package com.aionemu.gameserver.model.group;























public enum LootRuleType
{
  FREEFORALL(0),
  ROUNDROBIN(1),
  LEADER(2);
  
  private int id;

  
  LootRuleType(int id) {
    this.id = id;
  }

  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootRuleType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
