package com.aionemu.gameserver.controllers.movement;


























public enum MovementType
{
  MOVEMENT_START_MOUSE(-32),


  
  MOVEMENT_START_KEYBOARD(-64),


  
  VALIDATE_MOUSE(-96),


  
  VALIDATE_KEYBOARD(-128),


  
  VALIDATE_JUMP(8),


  
  VALIDATE_JUMP_WHILE_MOVING(72),


  
  MOVEMENT_GLIDE_UP(-124),


  
  MOVEMENT_GLIDE_DOWN(-60),


  
  MOVEMENT_GLIDE_START_MOUSE(-28),


  
  VALIDATE_GLIDE_MOUSE(-92),


  
  MOVEMENT_STOP(0),
  
  MOVEMENT_STAYIN_ELEVATOR(24),
  MOVEMENT_JUMPIN_ELEVATOR(-48),
  MOVEMENT_VALIDATEIN_ELEVATOR(-112),
  MOVEMENT_MOVIN_ELEVATOR(-16),
  MOVEMENT_ON_ELEVATOR(16),
  MOVEMENT_GO_UPDOWN_ELEVATOR(-80),
  
  UNKNOWN_NPC_MOVEMENT(-22),
  
  UNKNOWN(1);



  
  private int typeId;



  
  MovementType(int typeId) {
    this.typeId = typeId;
  }





  
  public int getMovementTypeId() {
    return this.typeId;
  }






  
  public static MovementType getMovementTypeById(int id) {
    for (MovementType mt : values()) {
      
      if (mt.typeId == id)
        return mt; 
    } 
    return UNKNOWN;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\MovementType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
