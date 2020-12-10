package com.aionemu.gameserver.model.gameobjects.player;





















public enum DeniedStatus
{
  VEIW_DETAIL(1),
  TRADE(2),
  GROUP(4),
  GUILD(8),
  FRIEND(16),
  DUEL(32);
  
  private int id;

  
  DeniedStatus(int id) {
    this.id = id;
  }




  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\DeniedStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
