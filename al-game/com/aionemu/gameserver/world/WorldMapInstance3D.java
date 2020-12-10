/*    */ package com.aionemu.gameserver.world;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldMapInstance3D
/*    */   extends WorldMapInstance
/*    */ {
/*    */   public WorldMapInstance3D(WorldMap parent, int instanceId) {
/* 32 */     super(parent, instanceId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected Integer getRegionId(float x, float y, float z) {
/* 45 */     return Integer.valueOf(((int)x + 1000) / regionSize * maxWorldSize * maxWorldSize + ((int)y + 1000) / regionSize * maxWorldSize + ((int)z + 1000) / regionSize);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MapRegion createMapRegion(Integer regionId) {
/* 58 */     MapRegion r = new MapRegion(regionId, this, true);
/* 59 */     this.regions.put(regionId, r);
/*    */     
/* 61 */     int rx = regionId.intValue() / maxWorldSize * maxWorldSize;
/* 62 */     int ry = regionId.intValue() / maxWorldSize % maxWorldSize;
/* 63 */     int rz = regionId.intValue() % maxWorldSize * maxWorldSize / maxWorldSize;
/*    */     
/* 65 */     for (int x = rx - 1; x <= rx + 1; x++) {
/*    */       
/* 67 */       for (int y = ry - 1; y <= ry + 1; y++) {
/*    */         
/* 69 */         for (int z = rz - 1; z <= rz + 1; z++) {
/*    */           
/* 71 */           if (x != rx || y != ry || z != rz) {
/*    */             
/* 73 */             int neighbourId = x * maxWorldSize * maxWorldSize + y * maxWorldSize + z;
/*    */             
/* 75 */             MapRegion neighbour = this.regions.get(Integer.valueOf(neighbourId));
/* 76 */             if (neighbour != null) {
/*    */               
/* 78 */               r.addNeighbourRegion(neighbour);
/* 79 */               neighbour.addNeighbourRegion(r);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/* 84 */     }  return r;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldMapInstance3D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */