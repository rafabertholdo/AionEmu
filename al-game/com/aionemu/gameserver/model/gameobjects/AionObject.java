package com.aionemu.gameserver.model.gameobjects;































public abstract class AionObject
{
  private int objectId;
  
  public AionObject(Integer objId) {
    this.objectId = objId.intValue();
  }






  
  public int getObjectId() {
    return this.objectId;
  }






  
  public abstract String getName();





  
  public boolean equals(Object obj) {
    if (this == obj)
      return true; 
    if (obj == null)
      return false; 
    if (getClass() != obj.getClass())
      return false; 
    AionObject other = (AionObject)obj;
    if (this.objectId != other.objectId)
      return false; 
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\AionObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
