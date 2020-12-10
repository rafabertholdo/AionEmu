package com.aionemu.gameserver.services;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import com.aionemu.gameserver.model.templates.portal.EntryPoint;
import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import java.util.List;
import org.apache.log4j.Logger;























public class InstanceService
{
  private static Logger log = Logger.getLogger(InstanceService.class);






  
  public static synchronized WorldMapInstance getNextAvailableInstance(int worldId) {
    WorldMap map = World.getInstance().getWorldMap(worldId);
    
    if (!map.isInstanceType()) {
      throw new UnsupportedOperationException("Invalid call for next available instance  of " + worldId);
    }
    int nextInstanceId = map.getNextInstanceId();
    
    log.info("Creating new instance: " + worldId + " " + nextInstanceId);
    
    WorldMapInstance worldMapInstance = map.addInstance(nextInstanceId);
    startInstanceChecker(worldMapInstance);
    SpawnEngine.getInstance().spawnInstance(worldId, worldMapInstance.getInstanceId());
    
    return worldMapInstance;
  }




  
  private static void destroyInstance(WorldMapInstance instance) {
    instance.getEmptyInstanceTask().cancel(false);
    
    int worldId = instance.getMapId().intValue();
    int instanceId = instance.getInstanceId();
    
    WorldMap map = World.getInstance().getWorldMap(worldId);
    map.removeWorldMapInstance(instanceId);
    
    log.info("Destroying instance:" + worldId + " " + instanceId);
    
    for (VisibleObject obj : instance.getAllWorldMapObjects()) {
      
      if (obj instanceof Player) {
        
        Player player = (Player)obj;
        PortalTemplate portal = DataManager.PORTAL_DATA.getInstancePortalTemplate(worldId, player.getCommonData().getRace());
        moveToEntryPoint((Player)obj, portal, true);
        
        continue;
      } 
      obj.getController().delete();
    } 
  }







  
  public static void registerPlayerWithInstance(WorldMapInstance instance, Player player) {
    instance.register(player.getObjectId());
  }






  
  public static void registerGroupWithInstance(WorldMapInstance instance, PlayerGroup group) {
    instance.registerGroup(group);
  }







  
  public static WorldMapInstance getRegisteredInstance(int worldId, int objectId) {
    for (WorldMapInstance instance : World.getInstance().getWorldMap(worldId).getAllWorldMapInstances()) {
      
      if (instance.isRegistered(objectId))
        return instance; 
    } 
    return null;
  }




  
  public static void onPlayerLogin(Player player) {
    int worldId = player.getWorldId();
    
    WorldMapTemplate worldTemplate = DataManager.WORLD_MAPS_DATA.getTemplate(worldId);
    if (worldTemplate.isInstance()) {
      
      PortalTemplate portalTemplate = DataManager.PORTAL_DATA.getInstancePortalTemplate(worldId, player.getCommonData().getRace());
      
      if (portalTemplate == null) {
        
        log.error("No portal template found for " + worldId);
        
        return;
      } 
      int lookupId = player.getObjectId();
      if (portalTemplate.isGroup() && player.getPlayerGroup() != null)
      {
        lookupId = player.getPlayerGroup().getGroupId();
      }
      
      WorldMapInstance registeredInstance = getRegisteredInstance(worldId, lookupId);
      if (registeredInstance != null) {
        
        World.getInstance().setPosition((VisibleObject)player, worldId, registeredInstance.getInstanceId(), player.getX(), player.getY(), player.getZ(), player.getHeading());
        
        return;
      } 
      
      moveToEntryPoint(player, portalTemplate, false);
    } 
  }






  
  private static void moveToEntryPoint(Player player, PortalTemplate portalTemplate, boolean useTeleport) {
    EntryPoint entryPoint = null;
    List<EntryPoint> entryPoints = portalTemplate.getEntryPoint();
    
    for (EntryPoint point : entryPoints) {
      
      if (point.getRace() == null || point.getRace().equals(player.getCommonData().getRace())) {
        
        entryPoint = point;
        
        break;
      } 
    } 
    if (entryPoint == null) {
      
      log.warn("Entry point not found for " + player.getCommonData().getRace() + " " + player.getWorldId());
      
      return;
    } 
    if (useTeleport) {
      
      TeleportService.teleportTo(player, entryPoint.getMapId(), 1, entryPoint.getX(), entryPoint.getY(), entryPoint.getZ(), 0);
    
    }
    else {
      
      World.getInstance().setPosition((VisibleObject)player, entryPoint.getMapId(), 1, entryPoint.getX(), entryPoint.getY(), entryPoint.getZ(), player.getHeading());
    } 
  }








  
  public static boolean isInstanceExist(int worldId, int instanceId) {
    return (World.getInstance().getWorldMap(worldId).getWorldMapInstanceById(instanceId) != null);
  }





  
  private static void startInstanceChecker(WorldMapInstance worldMapInstance) {
    int delay = 60000 + Rnd.get(-10, 10);
    worldMapInstance.setEmptyInstanceTask(ThreadPoolManager.getInstance().scheduleAtFixedRate(new EmptyInstanceCheckerTask(worldMapInstance), delay, delay));
  }

  
  private static class EmptyInstanceCheckerTask
    implements Runnable
  {
    private WorldMapInstance worldMapInstance;
    
    private EmptyInstanceCheckerTask(WorldMapInstance worldMapInstance) {
      this.worldMapInstance = worldMapInstance;
    }


    
    public void run() {
      PlayerGroup registeredGroup = this.worldMapInstance.getRegisteredGroup();
      if (registeredGroup == null) {
        
        if (this.worldMapInstance.playersCount() == 0) {
          
          InstanceService.destroyInstance(this.worldMapInstance);
          return;
        } 
        int mapId = this.worldMapInstance.getMapId().intValue();
        for (Player player : this.worldMapInstance.getAllWorldMapPlayers()) {
          
          if (player.isOnline() && player.getWorldId() == mapId)
            return; 
        } 
        InstanceService.destroyInstance(this.worldMapInstance);
      }
      else if (registeredGroup.size() == 0) {
        
        InstanceService.destroyInstance(this.worldMapInstance);
      } 
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\InstanceService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
