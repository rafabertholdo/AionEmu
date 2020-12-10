package com.aionemu.gameserver.model.gameobjects;

public abstract class AionObject {
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
    AionObject other = (AionObject) obj;
    if (this.objectId != other.objectId)
      return false;
    return true;
  }
}
