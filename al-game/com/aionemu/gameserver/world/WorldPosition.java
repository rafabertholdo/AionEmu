package com.aionemu.gameserver.world;

import org.apache.log4j.Logger;



























public class WorldPosition
{
  private static final Logger log = Logger.getLogger(WorldPosition.class);



  
  private int mapId;



  
  private MapRegion mapRegion;



  
  private float x;



  
  private float y;



  
  private float z;


  
  private byte heading;


  
  private boolean isSpawned = false;



  
  public int getMapId() {
    if (this.mapId == 0)
      log.warn("WorldPosition has (mapId == 0) " + toString()); 
    return this.mapId;
  }




  
  public void setMapId(int mapId) {
    this.mapId = mapId;
  }






  
  public float getX() {
    return this.x;
  }






  
  public float getY() {
    return this.y;
  }






  
  public float getZ() {
    return this.z;
  }






  
  public MapRegion getMapRegion() {
    return this.isSpawned ? this.mapRegion : null;
  }





  
  public int getInstanceId() {
    return this.mapRegion.getParent().getInstanceId();
  }





  
  public int getInstanceCount() {
    return this.mapRegion.getParent().getParent().getInstanceCount();
  }





  
  public boolean isInstanceMap() {
    return this.mapRegion.getParent().getParent().isInstanceType();
  }





  
  public byte getHeading() {
    return this.heading;
  }





  
  public World getWorld() {
    return this.mapRegion.getWorld();
  }





  
  public boolean isSpawned() {
    return this.isSpawned;
  }






  
  void setIsSpawned(boolean val) {
    this.isSpawned = val;
  }







  
  void setMapRegion(MapRegion r) {
    this.mapRegion = r;
  }










  
  void setXYZH(float newX, float newY, float newZ, byte newHeading) {
    this.x = newX;
    this.y = newY;
    this.z = newZ;
    this.heading = newHeading;
  }


  
  public String toString() {
    return "WorldPosition [heading=" + this.heading + ", isSpawned=" + this.isSpawned + ", mapRegion=" + this.mapRegion + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
  }



  
  public boolean equals(Object o) {
    if (this == o)
      return true; 
    if (!(o instanceof WorldPosition)) {
      return false;
    }
    WorldPosition pos = (WorldPosition)o;
    return (this.x == pos.x && this.y == pos.y && this.z == pos.z && this.isSpawned == pos.isSpawned && this.heading == pos.heading && this.mapRegion == pos.mapRegion);
  }



  
  public WorldPosition clone() {
    WorldPosition pos = new WorldPosition();
    pos.heading = this.heading;
    pos.isSpawned = this.isSpawned;
    pos.mapRegion = this.mapRegion;
    pos.mapId = this.mapId;
    pos.x = this.x;
    pos.y = this.y;
    pos.z = this.z;
    return pos;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
