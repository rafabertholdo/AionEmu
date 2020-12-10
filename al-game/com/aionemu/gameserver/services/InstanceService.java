/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.model.templates.WorldMapTemplate;
/*     */ import com.aionemu.gameserver.model.templates.portal.EntryPoint;
/*     */ import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldMap;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
/*     */ import java.util.List;
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
/*     */ public class InstanceService
/*     */ {
/*  43 */   private static Logger log = Logger.getLogger(InstanceService.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized WorldMapInstance getNextAvailableInstance(int worldId) {
/*  52 */     WorldMap map = World.getInstance().getWorldMap(worldId);
/*     */     
/*  54 */     if (!map.isInstanceType()) {
/*  55 */       throw new UnsupportedOperationException("Invalid call for next available instance  of " + worldId);
/*     */     }
/*  57 */     int nextInstanceId = map.getNextInstanceId();
/*     */     
/*  59 */     log.info("Creating new instance: " + worldId + " " + nextInstanceId);
/*     */     
/*  61 */     WorldMapInstance worldMapInstance = map.addInstance(nextInstanceId);
/*  62 */     startInstanceChecker(worldMapInstance);
/*  63 */     SpawnEngine.getInstance().spawnInstance(worldId, worldMapInstance.getInstanceId());
/*     */     
/*  65 */     return worldMapInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void destroyInstance(WorldMapInstance instance) {
/*  73 */     instance.getEmptyInstanceTask().cancel(false);
/*     */     
/*  75 */     int worldId = instance.getMapId().intValue();
/*  76 */     int instanceId = instance.getInstanceId();
/*     */     
/*  78 */     WorldMap map = World.getInstance().getWorldMap(worldId);
/*  79 */     map.removeWorldMapInstance(instanceId);
/*     */     
/*  81 */     log.info("Destroying instance:" + worldId + " " + instanceId);
/*     */     
/*  83 */     for (VisibleObject obj : instance.getAllWorldMapObjects()) {
/*     */       
/*  85 */       if (obj instanceof Player) {
/*     */         
/*  87 */         Player player = (Player)obj;
/*  88 */         PortalTemplate portal = DataManager.PORTAL_DATA.getInstancePortalTemplate(worldId, player.getCommonData().getRace());
/*  89 */         moveToEntryPoint((Player)obj, portal, true);
/*     */         
/*     */         continue;
/*     */       } 
/*  93 */       obj.getController().delete();
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
/*     */   public static void registerPlayerWithInstance(WorldMapInstance instance, Player player) {
/* 105 */     instance.register(player.getObjectId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerGroupWithInstance(WorldMapInstance instance, PlayerGroup group) {
/* 115 */     instance.registerGroup(group);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static WorldMapInstance getRegisteredInstance(int worldId, int objectId) {
/* 126 */     for (WorldMapInstance instance : World.getInstance().getWorldMap(worldId).getAllWorldMapInstances()) {
/*     */       
/* 128 */       if (instance.isRegistered(objectId))
/* 129 */         return instance; 
/*     */     } 
/* 131 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayerLogin(Player player) {
/* 139 */     int worldId = player.getWorldId();
/*     */     
/* 141 */     WorldMapTemplate worldTemplate = DataManager.WORLD_MAPS_DATA.getTemplate(worldId);
/* 142 */     if (worldTemplate.isInstance()) {
/*     */       
/* 144 */       PortalTemplate portalTemplate = DataManager.PORTAL_DATA.getInstancePortalTemplate(worldId, player.getCommonData().getRace());
/*     */       
/* 146 */       if (portalTemplate == null) {
/*     */         
/* 148 */         log.error("No portal template found for " + worldId);
/*     */         
/*     */         return;
/*     */       } 
/* 152 */       int lookupId = player.getObjectId();
/* 153 */       if (portalTemplate.isGroup() && player.getPlayerGroup() != null)
/*     */       {
/* 155 */         lookupId = player.getPlayerGroup().getGroupId();
/*     */       }
/*     */       
/* 158 */       WorldMapInstance registeredInstance = getRegisteredInstance(worldId, lookupId);
/* 159 */       if (registeredInstance != null) {
/*     */         
/* 161 */         World.getInstance().setPosition((VisibleObject)player, worldId, registeredInstance.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading());
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 166 */       moveToEntryPoint(player, portalTemplate, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void moveToEntryPoint(Player player, PortalTemplate portalTemplate, boolean useTeleport) {
/* 177 */     EntryPoint entryPoint = null;
/* 178 */     List<EntryPoint> entryPoints = portalTemplate.getEntryPoint();
/*     */     
/* 180 */     for (EntryPoint point : entryPoints) {
/*     */       
/* 182 */       if (point.getRace() == null || point.getRace().equals(player.getCommonData().getRace())) {
/*     */         
/* 184 */         entryPoint = point;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 189 */     if (entryPoint == null) {
/*     */       
/* 191 */       log.warn("Entry point not found for " + player.getCommonData().getRace() + " " + player.getWorldId());
/*     */       
/*     */       return;
/*     */     } 
/* 195 */     if (useTeleport) {
/*     */       
/* 197 */       TeleportService.teleportTo(player, entryPoint.getMapId(), 1, entryPoint.getX(), entryPoint.getY(), entryPoint.getZ(), 0);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 202 */       World.getInstance().setPosition((VisibleObject)player, entryPoint.getMapId(), 1, entryPoint.getX(), entryPoint.getY(), entryPoint.getZ(), player.getHeading());
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
/*     */   public static boolean isInstanceExist(int worldId, int instanceId) {
/* 215 */     return (World.getInstance().getWorldMap(worldId).getWorldMapInstanceById(instanceId) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void startInstanceChecker(WorldMapInstance worldMapInstance) {
/* 224 */     int delay = 60000 + Rnd.get(-10, 10);
/* 225 */     worldMapInstance.setEmptyInstanceTask(ThreadPoolManager.getInstance().scheduleAtFixedRate(new EmptyInstanceCheckerTask(worldMapInstance), delay, delay));
/*     */   }
/*     */ 
/*     */   
/*     */   private static class EmptyInstanceCheckerTask
/*     */     implements Runnable
/*     */   {
/*     */     private WorldMapInstance worldMapInstance;
/*     */     
/*     */     private EmptyInstanceCheckerTask(WorldMapInstance worldMapInstance) {
/* 235 */       this.worldMapInstance = worldMapInstance;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/* 241 */       PlayerGroup registeredGroup = this.worldMapInstance.getRegisteredGroup();
/* 242 */       if (registeredGroup == null) {
/*     */         
/* 244 */         if (this.worldMapInstance.playersCount() == 0) {
/*     */           
/* 246 */           InstanceService.destroyInstance(this.worldMapInstance);
/*     */           return;
/*     */         } 
/* 249 */         int mapId = this.worldMapInstance.getMapId().intValue();
/* 250 */         for (Player player : this.worldMapInstance.getAllWorldMapPlayers()) {
/*     */           
/* 252 */           if (player.isOnline() && player.getWorldId() == mapId)
/*     */             return; 
/*     */         } 
/* 255 */         InstanceService.destroyInstance(this.worldMapInstance);
/*     */       }
/* 257 */       else if (registeredGroup.size() == 0) {
/*     */         
/* 259 */         InstanceService.destroyInstance(this.worldMapInstance);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\InstanceService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */