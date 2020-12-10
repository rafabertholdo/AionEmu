/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RIFT_ANNOUNCE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RIFT_STATUS;
/*     */ import com.aionemu.gameserver.services.RespawnService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.spawnengine.RiftSpawnManager;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.WorldMapInstance;
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
/*     */ public class RiftController
/*     */   extends NpcController
/*     */ {
/*     */   private boolean isMaster = false;
/*     */   private SpawnTemplate slaveSpawnTemplate;
/*     */   private Npc slave;
/*     */   private Integer maxEntries;
/*     */   private Integer maxLevel;
/*     */   private int usedEntries;
/*     */   private boolean isAccepting;
/*     */   private RiftSpawnManager.RiftEnum riftTemplate;
/*     */   
/*     */   public RiftController(Npc slave, RiftSpawnManager.RiftEnum riftTemplate) {
/*  60 */     this.riftTemplate = riftTemplate;
/*  61 */     if (slave != null) {
/*     */       
/*  63 */       this.slave = slave;
/*  64 */       this.slaveSpawnTemplate = slave.getSpawn();
/*  65 */       this.maxEntries = Integer.valueOf(riftTemplate.getEntries());
/*  66 */       this.maxLevel = Integer.valueOf(riftTemplate.getMaxLevel());
/*     */       
/*  68 */       this.isMaster = true;
/*  69 */       this.isAccepting = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogRequest(Player player) {
/*  76 */     if (!this.isMaster && !this.isAccepting) {
/*     */       return;
/*     */     }
/*  79 */     RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)getOwner())
/*     */       {
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder)
/*     */         {
/*  84 */           if (!RiftController.this.isAccepting) {
/*     */             return;
/*     */           }
/*  87 */           int worldId = RiftController.this.slaveSpawnTemplate.getWorldId();
/*  88 */           float x = RiftController.this.slaveSpawnTemplate.getX();
/*  89 */           float y = RiftController.this.slaveSpawnTemplate.getY();
/*  90 */           float z = RiftController.this.slaveSpawnTemplate.getZ();
/*     */           
/*  92 */           TeleportService.teleportTo(responder, worldId, x, y, z, 0);
/*  93 */           RiftController.this.usedEntries++;
/*     */           
/*  95 */           if (RiftController.this.usedEntries >= RiftController.this.maxEntries.intValue()) {
/*     */             
/*  97 */             RiftController.this.isAccepting = false;
/*     */             
/*  99 */             RespawnService.scheduleDecayTask(RiftController.this.getOwner());
/* 100 */             RespawnService.scheduleDecayTask(RiftController.this.slave);
/*     */           } 
/* 102 */           PacketSendUtility.broadcastPacket((VisibleObject)RiftController.this.getOwner(), (AionServerPacket)new SM_RIFT_STATUS(RiftController.this.getOwner().getObjectId(), RiftController.this.usedEntries, RiftController.this.maxEntries.intValue(), RiftController.this.maxLevel.intValue()));
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void denyRequest(Creature requester, Player responder) {}
/*     */       };
/* 113 */     boolean requested = player.getResponseRequester().putRequest(160019, responseHandler);
/* 114 */     if (requested)
/*     */     {
/* 116 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(160019, 0, new Object[0]));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void see(VisibleObject object) {
/* 123 */     if (!this.isMaster) {
/*     */       return;
/*     */     }
/* 126 */     if (object instanceof Player)
/*     */     {
/* 128 */       PacketSendUtility.sendPacket((Player)object, (AionServerPacket)new SM_RIFT_STATUS(getOwner().getObjectId(), this.usedEntries, this.maxEntries.intValue(), this.maxLevel.intValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendMessage(Player activePlayer) {
/* 138 */     if (this.isMaster && getOwner().isSpawned()) {
/* 139 */       PacketSendUtility.sendPacket(activePlayer, (AionServerPacket)new SM_RIFT_ANNOUNCE(this.riftTemplate.getDestination()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendAnnounce() {
/* 147 */     if (this.isMaster && getOwner().isSpawned()) {
/*     */       
/* 149 */       WorldMapInstance worldInstance = getOwner().getPosition().getMapRegion().getParent();
/* 150 */       for (Player player : worldInstance.getAllWorldMapPlayers()) {
/*     */         
/* 152 */         if (player.isSpawned())
/* 153 */           sendMessage(player); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\RiftController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */