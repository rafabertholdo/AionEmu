package com.aionemu.gameserver.model.gameobjects.state;





















public enum CreatureSeeState
{
  NORMAL(0),
  SEARCH1(1),
  SEARCH2(2);
  
  private int id;

  
  CreatureSeeState(int id) {
    this.id = id;
  }




  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureSeeState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
