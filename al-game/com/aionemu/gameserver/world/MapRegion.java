/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import javolution.util.FastList;
/*     */ import javolution.util.FastMap;
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
/*     */ public class MapRegion
/*     */ {
/*     */   private final Integer regionId;
/*     */   private final WorldMapInstance parent;
/*     */   private final FastList<MapRegion> neighbours;
/*  51 */   private final FastMap<Integer, VisibleObject> objects = (new FastMap()).shared();
/*     */   
/*  53 */   private int playerCount = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   MapRegion(Integer id, WorldMapInstance parent, boolean is3D) {
/*  63 */     if (is3D) {
/*  64 */       this.neighbours = new FastList(27);
/*     */     } else {
/*  66 */       this.neighbours = new FastList(9);
/*  67 */     }  this.regionId = id;
/*  68 */     this.parent = parent;
/*  69 */     this.neighbours.add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMapId() {
/*  79 */     return getParent().getMapId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/*  85 */     return getParent().getWorld();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getRegionId() {
/*  95 */     return this.regionId;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 100 */     return (this.playerCount > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapInstance getParent() {
/* 110 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastMap<Integer, VisibleObject> getVisibleObjects() {
/* 120 */     return this.objects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FastList<MapRegion> getNeighbours() {
/* 128 */     return this.neighbours;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void addNeighbourRegion(MapRegion neighbour) {
/* 138 */     this.neighbours.add(neighbour);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void add(VisibleObject object) {
/* 148 */     if (this.objects.put(Integer.valueOf(object.getObjectId()), object) == null)
/*     */     {
/* 150 */       if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
/*     */       {
/* 152 */         this.playerCount++;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void remove(VisibleObject object) {
/* 164 */     if (this.objects.remove(Integer.valueOf(object.getObjectId())) != null)
/*     */     {
/* 166 */       if (object instanceof com.aionemu.gameserver.model.gameobjects.player.Player)
/*     */       {
/* 168 */         this.playerCount--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 179 */     if (this == obj)
/* 180 */       return true; 
/* 181 */     if (obj == null)
/* 182 */       return false; 
/* 183 */     if (getClass() != obj.getClass())
/* 184 */       return false; 
/* 185 */     MapRegion other = (MapRegion)obj;
/* 186 */     if (this.regionId == null) {
/*     */       
/* 188 */       if (other.regionId != null) {
/* 189 */         return false;
/*     */       }
/* 191 */     } else if (!this.regionId.equals(other.regionId)) {
/* 192 */       return false;
/* 193 */     }  return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\MapRegion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */