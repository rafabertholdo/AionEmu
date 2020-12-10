/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.EmotionType;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.group.PlayerGroup;
/*     */ import com.aionemu.gameserver.model.templates.portal.ExitPoint;
/*     */ import com.aionemu.gameserver.model.templates.portal.PortalTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
/*     */ import com.aionemu.gameserver.services.InstanceService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldMap;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
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
/*     */ public class PortalController
/*     */   extends NpcController
/*     */ {
/*  46 */   private static final Logger log = Logger.getLogger(PortalController.class);
/*     */ 
/*     */   
/*     */   PortalTemplate portalTemplate;
/*     */ 
/*     */   
/*     */   public void setOwner(Creature owner) {
/*  53 */     super.setOwner(owner);
/*  54 */     this.portalTemplate = DataManager.PORTAL_DATA.getPortalTemplate(owner.getObjectTemplate().getTemplateId());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogRequest(final Player player) {
/*  60 */     if (this.portalTemplate == null) {
/*     */       return;
/*     */     }
/*  63 */     if (!CustomConfig.ENABLE_INSTANCES) {
/*     */       return;
/*     */     }
/*  66 */     int defaultUseTime = 3000;
/*  67 */     PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), getOwner().getObjectId(), 3000, 1));
/*     */     
/*  69 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_QUESTLOOT, 0, getOwner().getObjectId()), true);
/*     */     
/*  71 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*  75 */             PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), PortalController.this.getOwner().getObjectId(), 3000, 0));
/*     */             
/*  77 */             PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.END_QUESTLOOT, 0, PortalController.this.getOwner().getObjectId()), true);
/*     */             
/*  79 */             analyzePortation(player);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           private void analyzePortation(Player player) {
/*  87 */             if (PortalController.this.portalTemplate.getIdTitle() != 0 && player.getCommonData().getTitleId() != PortalController.this.portalTemplate.getIdTitle()) {
/*     */               return;
/*     */             }
/*  90 */             if (PortalController.this.portalTemplate.getRace() != null && !PortalController.this.portalTemplate.getRace().equals(player.getCommonData().getRace())) {
/*     */               
/*  92 */               PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MOVE_PORTAL_ERROR_INVALID_RACE);
/*     */               
/*     */               return;
/*     */             } 
/*  96 */             if ((PortalController.this.portalTemplate.getMaxLevel() != 0 && player.getLevel() > PortalController.this.portalTemplate.getMaxLevel()) || player.getLevel() < PortalController.this.portalTemplate.getMinLevel()) {
/*     */ 
/*     */               
/*  99 */               PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_CANT_INSTANCE_ENTER_LEVEL);
/*     */               
/*     */               return;
/*     */             } 
/* 103 */             PlayerGroup group = player.getPlayerGroup();
/* 104 */             if (PortalController.this.portalTemplate.isGroup() && group == null) {
/*     */               
/* 106 */               PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_MSG_ENTER_ONLY_PARTY_DON);
/*     */               
/*     */               return;
/*     */             } 
/* 110 */             if (PortalController.this.portalTemplate.isGroup() && group != null) {
/*     */               
/* 112 */               WorldMapInstance instance = InstanceService.getRegisteredInstance(PortalController.this.portalTemplate.getExitPoint().getMapId(), group.getGroupId());
/*     */ 
/*     */               
/* 115 */               if (instance == null)
/*     */               {
/* 117 */                 instance = PortalController.this.registerGroup(group);
/*     */               }
/*     */               
/* 120 */               PortalController.this.transfer(player, instance);
/*     */             }
/* 122 */             else if (!PortalController.this.portalTemplate.isGroup()) {
/*     */               
/* 124 */               WorldMapInstance instance = InstanceService.getRegisteredInstance(PortalController.this.portalTemplate.getExitPoint().getMapId(), player.getObjectId());
/*     */ 
/*     */               
/* 127 */               if (instance != null) {
/*     */                 
/* 129 */                 PortalController.this.transfer(player, instance);
/*     */                 return;
/*     */               } 
/* 132 */               PortalController.this.port(player);
/*     */             } 
/*     */           }
/*     */         }3000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void port(Player requester) {
/* 144 */     WorldMapInstance instance = null;
/* 145 */     int worldId = this.portalTemplate.getExitPoint().getMapId();
/* 146 */     if (this.portalTemplate.isInstance()) {
/*     */       
/* 148 */       instance = InstanceService.getNextAvailableInstance(worldId);
/* 149 */       InstanceService.registerPlayerWithInstance(instance, requester);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 154 */       WorldMap worldMap = World.getInstance().getWorldMap(worldId);
/* 155 */       if (worldMap == null) {
/*     */         
/* 157 */         log.warn("There is no registered map with id " + worldId);
/*     */         return;
/*     */       } 
/* 160 */       instance = worldMap.getWorldMapInstance();
/*     */     } 
/*     */     
/* 163 */     transfer(requester, instance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldMapInstance registerGroup(PlayerGroup group) {
/* 171 */     WorldMapInstance instance = InstanceService.getNextAvailableInstance(this.portalTemplate.getExitPoint().getMapId());
/* 172 */     InstanceService.registerGroupWithInstance(instance, group);
/* 173 */     return instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void transfer(Player player, WorldMapInstance instance) {
/* 181 */     ExitPoint exitPoint = this.portalTemplate.getExitPoint();
/* 182 */     TeleportService.teleportTo(player, exitPoint.getMapId(), instance.getInstanceId(), exitPoint.getX(), exitPoint.getY(), exitPoint.getZ(), 0);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\PortalController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */