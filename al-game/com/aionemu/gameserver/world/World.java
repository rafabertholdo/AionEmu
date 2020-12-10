/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.WorldMapTemplate;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.world.exceptions.AlreadySpawnedException;
/*     */ import com.aionemu.gameserver.world.exceptions.DuplicateAionObjectException;
/*     */ import com.aionemu.gameserver.world.exceptions.WorldMapNotExistException;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class World
/*     */ {
/*  51 */   private static final Logger log = Logger.getLogger(World.class);
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
/*  73 */   private final FastMap<String, Player> playersByName = (new FastMap(NetworkConfig.MAX_ONLINE_PLAYERS)).shared();
/*  74 */   private final FastMap<Integer, AionObject> allObjects = (new FastMap(100000)).shared();
/*  75 */   private final Map<Integer, WorldMap> worldMaps = (Map<Integer, WorldMap>)(new FastMap()).shared();
/*     */   private World() {
/*  77 */     for (WorldMapTemplate template : DataManager.WORLD_MAPS_DATA)
/*     */     {
/*  79 */       this.worldMaps.put(template.getMapId(), new WorldMap(template, this));
/*     */     }
/*  81 */     log.info("World: " + this.worldMaps.size() + " worlds map created.");
/*     */   }
/*     */ 
/*     */   
/*     */   public static final World getInstance() {
/*  86 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeObject(AionObject object) {
/*  96 */     if (this.allObjects.put(Integer.valueOf(object.getObjectId()), object) != null) {
/*  97 */       throw new DuplicateAionObjectException();
/*     */     }
/*  99 */     if (object instanceof Player) {
/* 100 */       this.playersByName.put(object.getName().toLowerCase(), object);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObject(AionObject object) {
/* 111 */     this.allObjects.remove(Integer.valueOf(object.getObjectId()));
/* 112 */     if (object instanceof Player)
/* 113 */       this.playersByName.remove(object.getName().toLowerCase()); 
/* 114 */     if (object instanceof com.aionemu.gameserver.model.gameobjects.Npc) {
/* 115 */       IDFactory.getInstance().releaseId(object.getObjectId());
/*     */     }
/*     */   }
/*     */   
/*     */   public Collection<Player> getAllPlayers() {
/* 120 */     return this.playersByName.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AionObject> getAllObjects() {
/* 125 */     return this.allObjects.values();
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
/*     */   public Player findPlayer(String name) {
/* 137 */     return (Player)this.playersByName.get(name.toLowerCase());
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
/*     */   public Player findPlayer(int objectId) {
/* 149 */     AionObject object = (AionObject)this.allObjects.get(Integer.valueOf(objectId));
/* 150 */     if (object instanceof Player)
/* 151 */       return (Player)this.allObjects.get(Integer.valueOf(objectId)); 
/* 152 */     return null;
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
/*     */   public AionObject findAionObject(int objectId) {
/* 164 */     return (AionObject)this.allObjects.get(Integer.valueOf(objectId));
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
/*     */   public WorldMap getWorldMap(int id) {
/* 176 */     WorldMap map = this.worldMaps.get(Integer.valueOf(id));
/*     */ 
/*     */ 
/*     */     
/* 180 */     if (map == null)
/* 181 */       throw new WorldMapNotExistException("Map: " + id + " not exist!"); 
/* 182 */     return map;
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
/*     */   
/*     */   public void updatePosition(VisibleObject object, float newX, float newY, float newZ, byte newHeading) {
/* 197 */     updatePosition(object, newX, newY, newZ, newHeading, true);
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
/*     */   public void updatePosition(VisibleObject object, float newX, float newY, float newZ, byte newHeading, boolean updateKnownList) {
/* 211 */     if (!object.isSpawned()) {
/*     */       return;
/*     */     }
/* 214 */     object.getPosition().setXYZH(newX, newY, newZ, newHeading);
/*     */     
/* 216 */     MapRegion oldRegion = object.getActiveRegion();
/* 217 */     if (oldRegion == null) {
/*     */       
/* 219 */       log.warn(String.format("CHECKPOINT: oldregion is null, object coordinates - %d %d %d", new Object[] { Float.valueOf(object.getX()), Float.valueOf(object.getY()), Float.valueOf(object.getY()) }));
/*     */       
/*     */       return;
/*     */     } 
/* 223 */     MapRegion newRegion = oldRegion.getParent().getRegion(object);
/*     */     
/* 225 */     if (newRegion != oldRegion) {
/*     */       
/* 227 */       oldRegion.remove(object);
/* 228 */       newRegion.add(object);
/* 229 */       object.getPosition().setMapRegion(newRegion);
/*     */     } 
/*     */     
/* 232 */     if (updateKnownList)
/*     */     {
/* 234 */       object.getKnownList().updateKnownList();
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPosition(VisibleObject object, int mapId, float x, float y, float z, byte heading) {
/* 253 */     int instanceId = 1;
/* 254 */     if (object.getWorldId() == mapId)
/*     */     {
/* 256 */       instanceId = object.getInstanceId();
/*     */     }
/* 258 */     setPosition(object, mapId, instanceId, x, y, z, heading);
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
/*     */   
/*     */   public void setPosition(VisibleObject object, int mapId, int instance, float x, float y, float z, byte heading) {
/* 273 */     if (object.isSpawned())
/* 274 */       despawn(object); 
/* 275 */     object.getPosition().setXYZH(x, y, z, heading);
/* 276 */     object.getPosition().setMapId(mapId);
/* 277 */     object.getPosition().setMapRegion(getWorldMap(mapId).getWorldMapInstanceById(instance).getRegion(object));
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
/*     */ 
/*     */   
/*     */   public WorldPosition createPosition(int mapId, float x, float y, float z, byte heading) {
/* 293 */     WorldPosition position = new WorldPosition();
/* 294 */     position.setXYZH(x, y, z, heading);
/* 295 */     position.setMapId(mapId);
/* 296 */     position.setMapRegion(getWorldMap(mapId).getWorldMapInstance().getRegion(x, y, z));
/* 297 */     return position;
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
/*     */   public void spawn(VisibleObject object) {
/* 310 */     if (object.isSpawned()) {
/* 311 */       throw new AlreadySpawnedException();
/*     */     }
/* 313 */     object.getPosition().setIsSpawned(true);
/* 314 */     if (object.getSpawn() != null)
/* 315 */       object.getSpawn().setSpawned(true, object.getInstanceId()); 
/* 316 */     object.getActiveRegion().getParent().addObject(object);
/* 317 */     object.getActiveRegion().add(object);
/*     */     
/* 319 */     object.getKnownList().updateKnownList();
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
/*     */   public void despawn(VisibleObject object) {
/* 331 */     if (object.getActiveRegion() != null) {
/*     */       
/* 333 */       if (object.getActiveRegion().getParent() != null)
/* 334 */         object.getActiveRegion().getParent().removeObject((AionObject)object); 
/* 335 */       object.getActiveRegion().remove(object);
/*     */     } 
/* 337 */     object.getPosition().setIsSpawned(false);
/* 338 */     if (object.getSpawn() != null)
/* 339 */       object.getSpawn().setSpawned(false, object.getInstanceId()); 
/* 340 */     object.getKnownList().clearKnownList();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 346 */     protected static final World instance = new World();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */