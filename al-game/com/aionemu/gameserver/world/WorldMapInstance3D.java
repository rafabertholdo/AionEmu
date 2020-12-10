package com.aionemu.gameserver.world;


























public class WorldMapInstance3D
  extends WorldMapInstance
{
  public WorldMapInstance3D(WorldMap parent, int instanceId) {
    super(parent, instanceId);
  }









  
  protected Integer getRegionId(float x, float y, float z) {
    return Integer.valueOf(((int)x + 1000) / regionSize * maxWorldSize * maxWorldSize + ((int)y + 1000) / regionSize * maxWorldSize + ((int)z + 1000) / regionSize);
  }









  
  protected MapRegion createMapRegion(Integer regionId) {
    MapRegion r = new MapRegion(regionId, this, true);
    this.regions.put(regionId, r);
    
    int rx = regionId.intValue() / maxWorldSize * maxWorldSize;
    int ry = regionId.intValue() / maxWorldSize % maxWorldSize;
    int rz = regionId.intValue() % maxWorldSize * maxWorldSize / maxWorldSize;
    
    for (int x = rx - 1; x <= rx + 1; x++) {
      
      for (int y = ry - 1; y <= ry + 1; y++) {
        
        for (int z = rz - 1; z <= rz + 1; z++) {
          
          if (x != rx || y != ry || z != rz) {
            
            int neighbourId = x * maxWorldSize * maxWorldSize + y * maxWorldSize + z;
            
            MapRegion neighbour = this.regions.get(Integer.valueOf(neighbourId));
            if (neighbour != null) {
              
              r.addNeighbourRegion(neighbour);
              neighbour.addNeighbourRegion(r);
            } 
          } 
        } 
      } 
    }  return r;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldMapInstance3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
