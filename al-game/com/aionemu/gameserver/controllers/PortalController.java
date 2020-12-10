package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.group.PlayerGroup;
import com.aionemu.gameserver.model.templates.portal.ExitPoint;
import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.services.InstanceService;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import com.aionemu.gameserver.world.WorldMapInstance;
import org.apache.log4j.Logger;



















public class PortalController
  extends NpcController
{
  private static final Logger log = Logger.getLogger(PortalController.class);

  
  PortalTemplate portalTemplate;

  
  public void setOwner(Creature owner) {
    super.setOwner(owner);
    this.portalTemplate = DataManager.PORTAL_DATA.getPortalTemplate(owner.getObjectTemplate().getTemplateId());
  }


  
  public void onDialogRequest(final Player player) {
    if (this.portalTemplate == null) {
      return;
    }
    if (!CustomConfig.ENABLE_INSTANCES) {
      return;
    }
    int defaultUseTime = 3000;
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), getOwner().getObjectId(), 3000, 1));
    
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, getOwner().getObjectId()), true);
    
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), PortalController.this.getOwner().getObjectId(), 3000, 0));
            
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_QUESTLOOT, 0, PortalController.this.getOwner().getObjectId()), true);
            
            analyzePortation(player);
          }




          
          private void analyzePortation(Player player) {
            if (PortalController.this.portalTemplate.getIdTitle() != 0 && player.getCommonData().getTitleId() != PortalController.this.portalTemplate.getIdTitle()) {
              return;
            }
            if (PortalController.this.portalTemplate.getRace() != null && !PortalController.this.portalTemplate.getRace().equals(player.getCommonData().getRace())) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MOVE_PORTAL_ERROR_INVALID_RACE);
              
              return;
            } 
            if ((PortalController.this.portalTemplate.getMaxLevel() != 0 && player.getLevel() > PortalController.this.portalTemplate.getMaxLevel()) || player.getLevel() < PortalController.this.portalTemplate.getMinLevel()) {

              
              PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_CANT_INSTANCE_ENTER_LEVEL);
              
              return;
            } 
            PlayerGroup group = player.getPlayerGroup();
            if (PortalController.this.portalTemplate.isGroup() && group == null) {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
              
              return;
            } 
            if (PortalController.this.portalTemplate.isGroup() && group != null) {
              
              WorldMapInstance instance = InstanceService.getRegisteredInstance(PortalController.this.portalTemplate.getExitPoint().getMapId(), group.getGroupId());

              
              if (instance == null)
              {
                instance = PortalController.this.registerGroup(group);
              }
              
              PortalController.this.transfer(player, instance);
            }
            else if (!PortalController.this.portalTemplate.isGroup()) {
              
              WorldMapInstance instance = InstanceService.getRegisteredInstance(PortalController.this.portalTemplate.getExitPoint().getMapId(), player.getObjectId());

              
              if (instance != null) {
                
                PortalController.this.transfer(player, instance);
                return;
              } 
              PortalController.this.port(player);
            } 
          }
        }3000L);
  }





  
  private void port(Player requester) {
    WorldMapInstance instance = null;
    int worldId = this.portalTemplate.getExitPoint().getMapId();
    if (this.portalTemplate.isInstance()) {
      
      instance = InstanceService.getNextAvailableInstance(worldId);
      InstanceService.registerPlayerWithInstance(instance, requester);
    
    }
    else {
      
      WorldMap worldMap = World.getInstance().getWorldMap(worldId);
      if (worldMap == null) {
        
        log.warn("There is no registered map with id " + worldId);
        return;
      } 
      instance = worldMap.getWorldMapInstance();
    } 
    
    transfer(requester, instance);
  }




  
  private WorldMapInstance registerGroup(PlayerGroup group) {
    WorldMapInstance instance = InstanceService.getNextAvailableInstance(this.portalTemplate.getExitPoint().getMapId());
    InstanceService.registerGroupWithInstance(instance, group);
    return instance;
  }




  
  private void transfer(Player player, WorldMapInstance instance) {
    ExitPoint exitPoint = this.portalTemplate.getExitPoint();
    TeleportService.teleportTo(player, exitPoint.getMapId(), instance.getInstanceId(), exitPoint.getX(), exitPoint.getY(), exitPoint.getZ(), 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\PortalController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
