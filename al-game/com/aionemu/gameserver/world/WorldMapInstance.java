/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class WorldMapInstance
/*     */ {
/*  45 */   public static final int regionSize = OptionsConfig.REGION_SIZE;
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected static final int maxWorldSize = OptionsConfig.MAX_WORLD_SIZE;
/*     */ 
/*     */ 
/*     */   
/*     */   protected static final int offset = 1000;
/*     */ 
/*     */   
/*     */   private final WorldMap parent;
/*     */ 
/*     */   
/*  59 */   protected final Map<Integer, MapRegion> regions = (Map<Integer, MapRegion>)(new FastMap()).shared();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   private final Map<Integer, VisibleObject> worldMapObjects = (Map<Integer, VisibleObject>)(new FastMap()).shared();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final Map<Integer, Player> worldMapPlayers = (Map<Integer, Player>)(new FastMap()).shared();
/*     */   
/*  71 */   private final Set<Integer> registeredObjects = Collections.newSetFromMap((Map<Integer, Boolean>)(new FastMap()).shared());
/*     */ 
/*     */ 
/*     */   
/*  75 */   private PlayerGroup registeredGroup = null;
/*     */   
/*  77 */   private Future<?> emptyInstanceTask = null;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int instanceId;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapInstance(WorldMap parent, int instanceId) {
/*  91 */     this.parent = parent;
/*  92 */     this.instanceId = instanceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMapId() {
/* 102 */     return getParent().getMapId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMap getParent() {
/* 112 */     return this.parent;
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
/*     */   MapRegion getRegion(VisibleObject object) {
/* 124 */     return getRegion(object.getX(), object.getY(), object.getZ());
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
/*     */   MapRegion getRegion(float x, float y, float z) {
/* 136 */     Integer regionId = getRegionId(x, y, z);
/* 137 */     MapRegion region = this.regions.get(regionId);
/* 138 */     if (region == null)
/*     */     {
/* 140 */       synchronized (this) {
/*     */         
/* 142 */         region = this.regions.get(regionId);
/* 143 */         if (region == null)
/*     */         {
/* 145 */           region = createMapRegion(regionId);
/*     */         }
/*     */       } 
/*     */     }
/* 149 */     return region;
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
/*     */   protected Integer getRegionId(float x, float y, float z) {
/* 161 */     return Integer.valueOf(((int)x + 1000) / regionSize * maxWorldSize + ((int)y + 1000) / regionSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MapRegion createMapRegion(Integer regionId) {
/* 172 */     MapRegion r = new MapRegion(regionId, this, false);
/* 173 */     this.regions.put(regionId, r);
/*     */     
/* 175 */     int rx = regionId.intValue() / maxWorldSize;
/* 176 */     int ry = regionId.intValue() % maxWorldSize;
/*     */     
/* 178 */     for (int x = rx - 1; x <= rx + 1; x++) {
/*     */       
/* 180 */       for (int y = ry - 1; y <= ry + 1; y++) {
/*     */         
/* 182 */         if (x != rx || y != ry) {
/*     */           
/* 184 */           int neighbourId = x * maxWorldSize + y;
/*     */           
/* 186 */           MapRegion neighbour = this.regions.get(Integer.valueOf(neighbourId));
/* 187 */           if (neighbour != null) {
/*     */             
/* 189 */             r.addNeighbourRegion(neighbour);
/* 190 */             neighbour.addNeighbourRegion(r);
/*     */           } 
/*     */         } 
/*     */       } 
/* 194 */     }  return r;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 204 */     return getParent().getWorld();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addObject(VisibleObject object) {
/* 213 */     if (this.worldMapObjects.put(Integer.valueOf(object.getObjectId()), object) != null) {
/* 214 */       throw new DuplicateAionObjectException();
/*     */     }
/* 216 */     if (object instanceof Player) {
/* 217 */       this.worldMapPlayers.put(Integer.valueOf(object.getObjectId()), (Player)object);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObject(AionObject object) {
/* 226 */     this.worldMapObjects.remove(Integer.valueOf(object.getObjectId()));
/* 227 */     if (object instanceof Player) {
/* 228 */       this.worldMapPlayers.remove(Integer.valueOf(object.getObjectId()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInstanceId() {
/* 236 */     return this.instanceId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInInstance(int objId) {
/* 247 */     return this.worldMapPlayers.containsKey(Integer.valueOf(objId));
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<VisibleObject> getAllWorldMapObjects() {
/* 252 */     return this.worldMapObjects.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Player> getAllWorldMapPlayers() {
/* 257 */     return this.worldMapPlayers.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerGroup(PlayerGroup group) {
/* 262 */     this.registeredGroup = group;
/* 263 */     register(group.getGroupId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(int objectId) {
/* 271 */     this.registeredObjects.add(Integer.valueOf(objectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRegisteredObjectsSize() {
/* 279 */     return this.registeredObjects.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRegistered(int objectId) {
/* 288 */     return this.registeredObjects.contains(Integer.valueOf(objectId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Future<?> getEmptyInstanceTask() {
/* 296 */     return this.emptyInstanceTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEmptyInstanceTask(Future<?> emptyInstanceTask) {
/* 305 */     this.emptyInstanceTask = emptyInstanceTask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerGroup getRegisteredGroup() {
/* 313 */     return this.registeredGroup;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int playersCount() {
/* 322 */     return this.worldMapPlayers.size();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldMapInstance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */