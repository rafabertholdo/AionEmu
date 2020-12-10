package com.aionemu.gameserver.services;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.PlayerInitialData;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.templates.BindPointTemplate;
import com.aionemu.gameserver.model.templates.portal.ExitPoint;
import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.teleport.TelelocationTemplate;
import com.aionemu.gameserver.model.templates.teleport.TeleportLocation;
import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SET_BIND_POINT;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_LOC;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_MAP;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMapType;
import org.apache.log4j.Logger;




















public class TeleportService
{
  private static final Logger log = Logger.getLogger(TeleportService.class);
  
  private static final int TELEPORT_DEFAULT_DELAY = 2200;
  
  private static final World world = World.getInstance();











  
  public static void scheduleTeleportTask(Player activePlayer, int mapid, float x, float y, float z) {
    teleportTo(activePlayer, mapid, x, y, z, 2200);
  }








  
  public static void flightTeleport(TeleporterTemplate template, int locId, Player player) {
    if (template.getTeleLocIdData() == null) {
      
      log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      
      PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
      
      return;
    } 
    
    TeleportLocation location = template.getTeleLocIdData().getTeleportLocation(locId);
    if (location == null) {
      
      log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      
      PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
      
      return;
    } 
    
    TelelocationTemplate locationTemplate = DataManager.TELELOCATION_DATA.getTelelocationTemplate(locId);
    if (locationTemplate == null) {
      
      log.info(String.format("Missing info at teleport_location.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      PacketSendUtility.sendMessage(player, "Missing info at teleport_location.xml with locId: " + locId);
      
      return;
    } 
    if (!checkKinahForTransportation(location, player))
      return; 
    player.setState(CreatureState.FLIGHT_TELEPORT);
    player.unsetState(CreatureState.ACTIVE);
    player.setFlightTeleportId(location.getTeleportId());
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, location.getTeleportId(), 0), true);
  }








  
  public static void regularTeleport(TeleporterTemplate template, int locId, Player player) {
    if (template.getTeleLocIdData() == null) {
      
      log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      
      PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
      
      return;
    } 
    
    TeleportLocation location = template.getTeleLocIdData().getTeleportLocation(locId);
    if (location == null) {
      
      log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      
      PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
      
      return;
    } 
    
    TelelocationTemplate locationTemplate = DataManager.TELELOCATION_DATA.getTelelocationTemplate(locId);
    if (locationTemplate == null) {
      
      log.info(String.format("Missing info at teleport_location.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
      PacketSendUtility.sendMessage(player, "Missing info at teleport_location.xml with locId: " + locId);
      
      return;
    } 
    if (!checkKinahForTransportation(location, player)) {
      return;
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_LOC(locationTemplate.getMapId(), locationTemplate.getX(), locationTemplate.getY(), locationTemplate.getZ()));
    
    scheduleTeleportTask(player, locationTemplate.getMapId(), locationTemplate.getX(), locationTemplate.getY(), locationTemplate.getZ());
  }










  
  private static boolean checkKinahForTransportation(TeleportLocation location, Player player) {
    int basePrice = (int)(location.getPrice() * 0.8F);
    
    long transportationPrice = player.getPrices().getPriceForService(basePrice);
    
    if (!ItemService.decreaseKinah(player, transportationPrice)) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(transportationPrice));
      return false;
    } 
    return true;
  }





  
  public static void showMap(Player player, int targetObjectId, int npcId) {
    if (player.isInState(CreatureState.FLYING)) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_USE_AIRPORT_WHEN_FLYING);
      
      return;
    } 
    Npc object = (Npc)world.findAionObject(targetObjectId);
    Race npcRace = object.getObjectTemplate().getRace();
    if (npcRace != null && npcRace != player.getCommonData().getRace()) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_MOVE_TO_AIRPORT_WRONG_NPC);
      
      return;
    } 
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_MAP(player, targetObjectId, getTeleporterTemplate(npcId)));
  }











  
  public static boolean teleportTo(Player player, int worldId, float x, float y, float z, int delay) {
    int instanceId = 1;
    if (player.getWorldId() == worldId)
    {
      instanceId = player.getInstanceId();
    }
    return teleportTo(player, worldId, instanceId, x, y, z, delay);
  }











  
  public static boolean teleportTo(Player player, int worldId, int instanceId, float x, float y, float z, int delay) {
    return teleportTo(player, worldId, instanceId, x, y, z, player.getHeading(), delay);
  }














  
  public static boolean teleportTo(final Player player, final int worldId, final int instanceId, final float x, final float y, final float z, final byte heading, int delay) {
    if (player.getLifeStats().isAlreadyDead() || !player.isSpawned()) {
      return false;
    }
    if (DuelService.getInstance().isDueling(player.getObjectId())) {
      DuelService.getInstance().loseDuel(player);
    }
    if (delay == 0) {
      
      changePosition(player, worldId, instanceId, x, y, z, heading);
      return true;
    } 
    
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), 0, 0, delay, 0, 0));
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            if (player.getLifeStats().isAlreadyDead() || !player.isSpawned()) {
              return;
            }
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(0, 0, 0, 0, 1, 0));
            TeleportService.changePosition(player, worldId, instanceId, x, y, z, heading);
          }
        }delay);
    
    return true;
  }










  
  private static void changePosition(Player player, int worldId, int instanceId, float x, float y, float z, byte heading) {
    player.getFlyController().endFly();
    
    world.despawn((VisibleObject)player);
    
    int currentWorldId = player.getWorldId();
    world.setPosition((VisibleObject)player, worldId, instanceId, x, y, z, heading);



    
    if (currentWorldId == worldId) {
      
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_INFO(player, false));
      world.spawn((VisibleObject)player);
      player.getEffectController().updatePlayerEffectIcons();
      player.getController().addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_REFRESH);

    
    }
    else {

      
      player.getController().startProtectionActiveTask();
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
      PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_SPAWN(player));
    } 
    player.getController().startProtectionActiveTask();
  }





  
  public static TeleporterTemplate getTeleporterTemplate(int npcId) {
    return DataManager.TELEPORTER_DATA.getTeleporterTemplate(npcId);
  }




  
  public static BindPointTemplate getBindPointTemplate2(int bindPointId) {
    return DataManager.BIND_POINT_DATA.getBindPointTemplate2(bindPointId);
  }




  
  public static void changeChannel(Player player, int channel) {
    world.despawn((VisibleObject)player);
    world.setPosition((VisibleObject)player, player.getWorldId(), channel + 1, player.getX(), player.getY(), player.getZ(), player.getHeading());
    
    player.getController().startProtectionActiveTask();
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_SPAWN(player));
  }







  
  public static void moveToBindLocation(Player player, boolean useTeleport) {
    moveToBindLocation(player, useTeleport, 0);
  }










  
  public static void moveToBindLocation(Player player, boolean useTeleport, int delay) {
    float x, y, z;
    int worldId, bindPointId = player.getCommonData().getBindPoint();
    
    if (bindPointId != 0) {
      
      BindPointTemplate bplist = getBindPointTemplate2(bindPointId);
      worldId = bplist.getZoneId();
      x = bplist.getX();
      y = bplist.getY();
      z = bplist.getZ();
    }
    else {
      
      PlayerInitialData.LocationData locationData = DataManager.PLAYER_INITIAL_DATA.getSpawnLocation(player.getCommonData().getRace());
      
      worldId = locationData.getMapId();
      x = locationData.getX();
      y = locationData.getY();
      z = locationData.getZ();
    } 
    
    if (useTeleport) {
      
      teleportTo(player, worldId, x, y, z, delay);
    }
    else {
      
      world.setPosition((VisibleObject)player, worldId, 1, x, y, z, player.getHeading());
    } 
  }






  
  public static void sendSetBindPoint(Player player) {
    int worldId;
    float x, y, z;
    if (player.getCommonData().getBindPoint() != 0) {
      
      BindPointTemplate bplist = DataManager.BIND_POINT_DATA.getBindPointTemplate2(player.getCommonData().getBindPoint());
      worldId = bplist.getZoneId();
      x = bplist.getX();
      y = bplist.getY();
      z = bplist.getZ();
    }
    else {
      
      PlayerInitialData.LocationData locationData = DataManager.PLAYER_INITIAL_DATA.getSpawnLocation(player.getCommonData().getRace());
      worldId = locationData.getMapId();
      x = locationData.getX();
      y = locationData.getY();
      z = locationData.getZ();
    } 
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SET_BIND_POINT(worldId, x, y, z, player));
  }





  
  public static void teleportToPortalExit(Player player, String portalName, int worldId, int delay) {
    PortalTemplate template = DataManager.PORTAL_DATA.getTemplateByNameAndWorld(worldId, portalName);
    if (template == null) {
      
      log.warn("No portal template found for : " + portalName + " " + worldId);
      
      return;
    } 
    ExitPoint exitPoint = template.getExitPoint();
    teleportTo(player, worldId, exitPoint.getX(), exitPoint.getY(), exitPoint.getZ(), delay);
  }

  
  public static void teleportToNpc(Player player, int npcId) {
    int delay = 0;
    SpawnTemplate template = DataManager.SPAWNS_DATA.getFirstSpawnByNpcId(npcId);
    
    if (template == null) {
      
      log.warn("No npc template found for : " + npcId);
      
      return;
    } 
    teleportTo(player, template.getWorldId(), template.getX(), template.getY(), template.getZ(), delay);
  }





  
  public static void moveToKiskLocation(Player player) {
    Kisk kisk = player.getKisk();
    
    int worldId = kisk.getWorldId();
    float x = kisk.getX();
    float y = kisk.getY();
    float z = kisk.getZ();
    byte heading = kisk.getHeading();
    
    int instanceId = 1;
    if (player.getWorldId() == worldId)
    {
      instanceId = player.getInstanceId();
    }
    
    teleportTo(player, worldId, instanceId, x, y, z, heading, 0);
  }

  
  public static void teleportToPrison(Player player) {
    teleportTo(player, WorldMapType.PRISON.getId(), 256.0F, 256.0F, 49.0F, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\TeleportService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
