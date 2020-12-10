package com.aionemu.gameserver.model.gameobjects.state;





















public enum CreatureState
{
  NPC_IDLE(64),
  
  FLIGHT_TELEPORT(2),
  CHAIR(6),
  LOOTING(12),

  
  ACTIVE(1),
  FLYING(2),
  RESTING(4),
  DEAD(6),
  
  PRIVATE_SHOP(10),
  
  WEAPON_EQUIPPED(32),
  WALKING(64),
  POWERSHARD(128),
  TREATMENT(256),
  GLIDING(512);




  
  private int id;




  
  CreatureState(int id) {
    this.id = id;
  }




  
  public int getId() {
    return this.id;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\state\CreatureState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
