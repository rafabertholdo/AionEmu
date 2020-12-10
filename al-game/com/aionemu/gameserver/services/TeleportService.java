/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.PlayerInitialData;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.templates.BindPointTemplate;
/*     */ import com.aionemu.gameserver.model.templates.portal.ExitPoint;
/*     */ import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.teleport.TelelocationTemplate;
/*     */ import com.aionemu.gameserver.model.templates.teleport.TeleportLocation;
/*     */ import com.aionemu.gameserver.model.templates.teleport.TeleporterTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CHANNEL_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ITEM_USAGE_ANIMATION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_SPAWN;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SET_BIND_POINT;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_LOC;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_TELEPORT_MAP;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldMapType;
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
/*     */ public class TeleportService
/*     */ {
/*  58 */   private static final Logger log = Logger.getLogger(TeleportService.class);
/*     */   
/*     */   private static final int TELEPORT_DEFAULT_DELAY = 2200;
/*     */   
/*  62 */   private static final World world = World.getInstance();
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
/*     */   public static void scheduleTeleportTask(Player activePlayer, int mapid, float x, float y, float z) {
/*  76 */     teleportTo(activePlayer, mapid, x, y, z, 2200);
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
/*     */   public static void flightTeleport(TeleporterTemplate template, int locId, Player player) {
/*  88 */     if (template.getTeleLocIdData() == null) {
/*     */       
/*  90 */       log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/*     */       
/*  92 */       PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  97 */     TeleportLocation location = template.getTeleLocIdData().getTeleportLocation(locId);
/*  98 */     if (location == null) {
/*     */       
/* 100 */       log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/*     */       
/* 102 */       PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 107 */     TelelocationTemplate locationTemplate = DataManager.TELELOCATION_DATA.getTelelocationTemplate(locId);
/* 108 */     if (locationTemplate == null) {
/*     */       
/* 110 */       log.info(String.format("Missing info at teleport_location.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/* 111 */       PacketSendUtility.sendMessage(player, "Missing info at teleport_location.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/* 115 */     if (!checkKinahForTransportation(location, player))
/*     */       return; 
/* 117 */     player.setState(CreatureState.FLIGHT_TELEPORT);
/* 118 */     player.unsetState(CreatureState.ACTIVE);
/* 119 */     player.setFlightTeleportId(location.getTeleportId());
/* 120 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_FLYTELEPORT, location.getTeleportId(), 0), true);
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
/*     */   public static void regularTeleport(TeleporterTemplate template, int locId, Player player) {
/* 132 */     if (template.getTeleLocIdData() == null) {
/*     */       
/* 134 */       log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/*     */       
/* 136 */       PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 141 */     TeleportLocation location = template.getTeleLocIdData().getTeleportLocation(locId);
/* 142 */     if (location == null) {
/*     */       
/* 144 */       log.info(String.format("Missing locId for this teleporter at teleporter_templates.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/*     */       
/* 146 */       PacketSendUtility.sendMessage(player, "Missing locId for this teleporter at teleporter_templates.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 151 */     TelelocationTemplate locationTemplate = DataManager.TELELOCATION_DATA.getTelelocationTemplate(locId);
/* 152 */     if (locationTemplate == null) {
/*     */       
/* 154 */       log.info(String.format("Missing info at teleport_location.xml with locId: %d", new Object[] { Integer.valueOf(locId) }));
/* 155 */       PacketSendUtility.sendMessage(player, "Missing info at teleport_location.xml with locId: " + locId);
/*     */       
/*     */       return;
/*     */     } 
/* 159 */     if (!checkKinahForTransportation(location, player)) {
/*     */       return;
/*     */     }
/* 162 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_LOC(locationTemplate.getMapId(), locationTemplate.getX(), locationTemplate.getY(), locationTemplate.getZ()));
/*     */     
/* 164 */     scheduleTeleportTask(player, locationTemplate.getMapId(), locationTemplate.getX(), locationTemplate.getY(), locationTemplate.getZ());
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
/*     */   private static boolean checkKinahForTransportation(TeleportLocation location, Player player) {
/* 178 */     int basePrice = (int)(location.getPrice() * 0.8F);
/*     */     
/* 180 */     long transportationPrice = player.getPrices().getPriceForService(basePrice);
/*     */     
/* 182 */     if (!ItemService.decreaseKinah(player, transportationPrice)) {
/*     */       
/* 184 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.NOT_ENOUGH_KINAH(transportationPrice));
/* 185 */       return false;
/*     */     } 
/* 187 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void showMap(Player player, int targetObjectId, int npcId) {
/* 196 */     if (player.isInState(CreatureState.FLYING)) {
/*     */       
/* 198 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_USE_AIRPORT_WHEN_FLYING);
/*     */       
/*     */       return;
/*     */     } 
/* 202 */     Npc object = (Npc)world.findAionObject(targetObjectId);
/* 203 */     Race npcRace = object.getObjectTemplate().getRace();
/* 204 */     if (npcRace != null && npcRace != player.getCommonData().getRace()) {
/*     */       
/* 206 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_MOVE_TO_AIRPORT_WRONG_NPC);
/*     */       
/*     */       return;
/*     */     } 
/* 210 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_TELEPORT_MAP(player, targetObjectId, getTeleporterTemplate(npcId)));
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
/*     */   public static boolean teleportTo(Player player, int worldId, float x, float y, float z, int delay) {
/* 225 */     int instanceId = 1;
/* 226 */     if (player.getWorldId() == worldId)
/*     */     {
/* 228 */       instanceId = player.getInstanceId();
/*     */     }
/* 230 */     return teleportTo(player, worldId, instanceId, x, y, z, delay);
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
/*     */   public static boolean teleportTo(Player player, int worldId, int instanceId, float x, float y, float z, int delay) {
/* 245 */     return teleportTo(player, worldId, instanceId, x, y, z, player.getHeading(), delay);
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
/*     */   public static boolean teleportTo(final Player player, final int worldId, final int instanceId, final float x, final float y, final float z, final byte heading, int delay) {
/* 263 */     if (player.getLifeStats().isAlreadyDead() || !player.isSpawned()) {
/* 264 */       return false;
/*     */     }
/* 266 */     if (DuelService.getInstance().isDueling(player.getObjectId())) {
/* 267 */       DuelService.getInstance().loseDuel(player);
/*     */     }
/* 269 */     if (delay == 0) {
/*     */       
/* 271 */       changePosition(player, worldId, instanceId, x, y, z, heading);
/* 272 */       return true;
/*     */     } 
/*     */     
/* 275 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(player.getObjectId(), 0, 0, delay, 0, 0));
/* 276 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 280 */             if (player.getLifeStats().isAlreadyDead() || !player.isSpawned()) {
/*     */               return;
/*     */             }
/* 283 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_ITEM_USAGE_ANIMATION(0, 0, 0, 0, 1, 0));
/* 284 */             TeleportService.changePosition(player, worldId, instanceId, x, y, z, heading);
/*     */           }
/*     */         }delay);
/*     */     
/* 288 */     return true;
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
/*     */   private static void changePosition(Player player, int worldId, int instanceId, float x, float y, float z, byte heading) {
/* 302 */     player.getFlyController().endFly();
/*     */     
/* 304 */     world.despawn((VisibleObject)player);
/*     */     
/* 306 */     int currentWorldId = player.getWorldId();
/* 307 */     world.setPosition((VisibleObject)player, worldId, instanceId, x, y, z, heading);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 312 */     if (currentWorldId == worldId) {
/*     */       
/* 314 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_STATS_INFO(player));
/* 315 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_INFO(player, false));
/* 316 */       world.spawn((VisibleObject)player);
/* 317 */       player.getEffectController().updatePlayerEffectIcons();
/* 318 */       player.getController().addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_REFRESH);
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/* 325 */       player.getController().startProtectionActiveTask();
/* 326 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
/* 327 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_SPAWN(player));
/*     */     } 
/* 329 */     player.getController().startProtectionActiveTask();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static TeleporterTemplate getTeleporterTemplate(int npcId) {
/* 338 */     return DataManager.TELEPORTER_DATA.getTeleporterTemplate(npcId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BindPointTemplate getBindPointTemplate2(int bindPointId) {
/* 346 */     return DataManager.BIND_POINT_DATA.getBindPointTemplate2(bindPointId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void changeChannel(Player player, int channel) {
/* 354 */     world.despawn((VisibleObject)player);
/* 355 */     world.setPosition((VisibleObject)player, player.getWorldId(), channel + 1, player.getX(), player.getY(), player.getZ(), player.getHeading());
/*     */     
/* 357 */     player.getController().startProtectionActiveTask();
/* 358 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CHANNEL_INFO(player.getPosition()));
/* 359 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAYER_SPAWN(player));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void moveToBindLocation(Player player, boolean useTeleport) {
/* 370 */     moveToBindLocation(player, useTeleport, 0);
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
/*     */   public static void moveToBindLocation(Player player, boolean useTeleport, int delay) {
/*     */     float x, y, z;
/* 385 */     int worldId, bindPointId = player.getCommonData().getBindPoint();
/*     */     
/* 387 */     if (bindPointId != 0) {
/*     */       
/* 389 */       BindPointTemplate bplist = getBindPointTemplate2(bindPointId);
/* 390 */       worldId = bplist.getZoneId();
/* 391 */       x = bplist.getX();
/* 392 */       y = bplist.getY();
/* 393 */       z = bplist.getZ();
/*     */     }
/*     */     else {
/*     */       
/* 397 */       PlayerInitialData.LocationData locationData = DataManager.PLAYER_INITIAL_DATA.getSpawnLocation(player.getCommonData().getRace());
/*     */       
/* 399 */       worldId = locationData.getMapId();
/* 400 */       x = locationData.getX();
/* 401 */       y = locationData.getY();
/* 402 */       z = locationData.getZ();
/*     */     } 
/*     */     
/* 405 */     if (useTeleport) {
/*     */       
/* 407 */       teleportTo(player, worldId, x, y, z, delay);
/*     */     }
/*     */     else {
/*     */       
/* 411 */       world.setPosition((VisibleObject)player, worldId, 1, x, y, z, player.getHeading());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendSetBindPoint(Player player) {
/*     */     int worldId;
/*     */     float x, y, z;
/* 424 */     if (player.getCommonData().getBindPoint() != 0) {
/*     */       
/* 426 */       BindPointTemplate bplist = DataManager.BIND_POINT_DATA.getBindPointTemplate2(player.getCommonData().getBindPoint());
/* 427 */       worldId = bplist.getZoneId();
/* 428 */       x = bplist.getX();
/* 429 */       y = bplist.getY();
/* 430 */       z = bplist.getZ();
/*     */     }
/*     */     else {
/*     */       
/* 434 */       PlayerInitialData.LocationData locationData = DataManager.PLAYER_INITIAL_DATA.getSpawnLocation(player.getCommonData().getRace());
/* 435 */       worldId = locationData.getMapId();
/* 436 */       x = locationData.getX();
/* 437 */       y = locationData.getY();
/* 438 */       z = locationData.getZ();
/*     */     } 
/* 440 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_SET_BIND_POINT(worldId, x, y, z, player));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void teleportToPortalExit(Player player, String portalName, int worldId, int delay) {
/* 449 */     PortalTemplate template = DataManager.PORTAL_DATA.getTemplateByNameAndWorld(worldId, portalName);
/* 450 */     if (template == null) {
/*     */       
/* 452 */       log.warn("No portal template found for : " + portalName + " " + worldId);
/*     */       
/*     */       return;
/*     */     } 
/* 456 */     ExitPoint exitPoint = template.getExitPoint();
/* 457 */     teleportTo(player, worldId, exitPoint.getX(), exitPoint.getY(), exitPoint.getZ(), delay);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void teleportToNpc(Player player, int npcId) {
/* 462 */     int delay = 0;
/* 463 */     SpawnTemplate template = DataManager.SPAWNS_DATA.getFirstSpawnByNpcId(npcId);
/*     */     
/* 465 */     if (template == null) {
/*     */       
/* 467 */       log.warn("No npc template found for : " + npcId);
/*     */       
/*     */       return;
/*     */     } 
/* 471 */     teleportTo(player, template.getWorldId(), template.getX(), template.getY(), template.getZ(), delay);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void moveToKiskLocation(Player player) {
/* 480 */     Kisk kisk = player.getKisk();
/*     */     
/* 482 */     int worldId = kisk.getWorldId();
/* 483 */     float x = kisk.getX();
/* 484 */     float y = kisk.getY();
/* 485 */     float z = kisk.getZ();
/* 486 */     byte heading = kisk.getHeading();
/*     */     
/* 488 */     int instanceId = 1;
/* 489 */     if (player.getWorldId() == worldId)
/*     */     {
/* 491 */       instanceId = player.getInstanceId();
/*     */     }
/*     */     
/* 494 */     teleportTo(player, worldId, instanceId, x, y, z, heading, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void teleportToPrison(Player player) {
/* 499 */     teleportTo(player, WorldMapType.PRISON.getId(), 256.0F, 256.0F, 49.0F, 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\TeleportService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */