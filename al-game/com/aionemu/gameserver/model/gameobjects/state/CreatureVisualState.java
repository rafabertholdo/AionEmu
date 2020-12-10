package com.aionemu.gameserver.model.gameobjects.state;





















public enum CreatureVisualState
{
  VISIBLE(0),
  HIDE1(1),
  HIDE2(2),
  HIDE3(3),
  HIDE10(10),
  HIDE13(13),
  HIDE20(20),
  BLINKING(64);
  
  private int id;

  
  CreatureVisualState(int id) {
    this.id = id;
  }




  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureVisualState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
