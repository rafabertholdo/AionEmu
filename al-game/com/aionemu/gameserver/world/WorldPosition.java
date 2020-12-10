/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldPosition
/*     */ {
/*  33 */   private static final Logger log = Logger.getLogger(WorldPosition.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int mapId;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private MapRegion mapRegion;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float x;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float y;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private float z;
/*     */ 
/*     */ 
/*     */   
/*     */   private byte heading;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSpawned = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMapId() {
/*  72 */     if (this.mapId == 0)
/*  73 */       log.warn("WorldPosition has (mapId == 0) " + toString()); 
/*  74 */     return this.mapId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMapId(int mapId) {
/*  82 */     this.mapId = mapId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getX() {
/*  92 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getY() {
/* 102 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getZ() {
/* 112 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapRegion getMapRegion() {
/* 122 */     return this.isSpawned ? this.mapRegion : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInstanceId() {
/* 131 */     return this.mapRegion.getParent().getInstanceId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInstanceCount() {
/* 140 */     return this.mapRegion.getParent().getParent().getInstanceCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstanceMap() {
/* 149 */     return this.mapRegion.getParent().getParent().isInstanceType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getHeading() {
/* 158 */     return this.heading;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 167 */     return this.mapRegion.getWorld();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpawned() {
/* 176 */     return this.isSpawned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setIsSpawned(boolean val) {
/* 186 */     this.isSpawned = val;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setMapRegion(MapRegion r) {
/* 197 */     this.mapRegion = r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setXYZH(float newX, float newY, float newZ, byte newHeading) {
/* 211 */     this.x = newX;
/* 212 */     this.y = newY;
/* 213 */     this.z = newZ;
/* 214 */     this.heading = newHeading;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 220 */     return "WorldPosition [heading=" + this.heading + ", isSpawned=" + this.isSpawned + ", mapRegion=" + this.mapRegion + ", x=" + this.x + ", y=" + this.y + ", z=" + this.z + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 227 */     if (this == o)
/* 228 */       return true; 
/* 229 */     if (!(o instanceof WorldPosition)) {
/* 230 */       return false;
/*     */     }
/* 232 */     WorldPosition pos = (WorldPosition)o;
/* 233 */     return (this.x == pos.x && this.y == pos.y && this.z == pos.z && this.isSpawned == pos.isSpawned && this.heading == pos.heading && this.mapRegion == pos.mapRegion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldPosition clone() {
/* 240 */     WorldPosition pos = new WorldPosition();
/* 241 */     pos.heading = this.heading;
/* 242 */     pos.isSpawned = this.isSpawned;
/* 243 */     pos.mapRegion = this.mapRegion;
/* 244 */     pos.mapId = this.mapId;
/* 245 */     pos.x = this.x;
/* 246 */     pos.y = this.y;
/* 247 */     pos.z = this.z;
/* 248 */     return pos;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */