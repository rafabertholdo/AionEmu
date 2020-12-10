/*     */ package com.aionemu.gameserver.controllers;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.Race;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.RequestResponseHandler;
/*     */ import com.aionemu.gameserver.model.templates.BindPointTemplate;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_QUESTION_WINDOW;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.services.ItemService;
/*     */ import com.aionemu.gameserver.services.TeleportService;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldType;
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
/*     */ public class BindpointController
/*     */   extends NpcController
/*     */ {
/*  44 */   private static Logger log = Logger.getLogger(BindpointController.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private BindPointTemplate bindPointTemplate;
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBindPointTemplate(BindPointTemplate bindPointTemplate) {
/*  53 */     this.bindPointTemplate = bindPointTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   private void bindHere(Player player) {
/*  58 */     RequestResponseHandler responseHandler = new RequestResponseHandler((Creature)getOwner())
/*     */       {
/*     */         
/*     */         public void acceptRequest(Creature requester, Player responder)
/*     */         {
/*  63 */           if (responder.getCommonData().getBindPoint() != BindpointController.this.bindPointTemplate.getBindId())
/*     */           {
/*  65 */             if (ItemService.decreaseKinah(responder, BindpointController.this.bindPointTemplate.getPrice())) {
/*     */               
/*  67 */               responder.getCommonData().setBindPoint(BindpointController.this.bindPointTemplate.getBindId());
/*  68 */               TeleportService.sendSetBindPoint(responder);
/*  69 */               PacketSendUtility.broadcastPacket(responder, (AionServerPacket)new SM_LEVEL_UPDATE(responder.getObjectId(), 2, responder.getCommonData().getLevel()), true);
/*  70 */               PacketSendUtility.sendPacket(responder, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_DEATH_REGISTER_RESURRECT_POINT());
/*     */             }
/*     */             else {
/*     */               
/*  74 */               PacketSendUtility.sendPacket(responder, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_CANNOT_REGISTER_RESURRECT_POINT_NOT_ENOUGH_FEE());
/*     */               return;
/*     */             } 
/*     */           }
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void denyRequest(Creature requester, Player responder) {}
/*     */       };
/*  86 */     boolean requested = player.getResponseRequester().putRequest(160012, responseHandler);
/*  87 */     if (requested) {
/*     */       
/*  89 */       String price = Integer.toString(this.bindPointTemplate.getPrice());
/*  90 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUESTION_WINDOW(160012, 0, new Object[] { price }));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDialogRequest(Player player) {
/*  97 */     if (this.bindPointTemplate == null) {
/*     */       
/*  99 */       log.info("There is no bind point template for npc: " + getOwner().getNpcId());
/*     */       
/*     */       return;
/*     */     } 
/* 103 */     if (player.getCommonData().getBindPoint() == this.bindPointTemplate.getBindId()) {
/*     */       
/* 105 */       PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_ALREADY_REGISTER_THIS_RESURRECT_POINT());
/*     */       
/*     */       return;
/*     */     } 
/* 109 */     WorldType worldType = World.getInstance().getWorldMap(player.getWorldId()).getWorldType();
/* 110 */     if (!CustomConfig.ENABLE_CROSS_FACTION_BINDING) {
/*     */       
/* 112 */       if (worldType == WorldType.ASMODAE && player.getCommonData().getRace() == Race.ELYOS) {
/*     */         
/* 114 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "Elyos cannot bind in Asmodian territory.", ChatType.ANNOUNCEMENTS));
/*     */         
/*     */         return;
/*     */       } 
/* 118 */       if (worldType == WorldType.ELYSEA && player.getCommonData().getRace() == Race.ASMODIANS) {
/*     */         
/* 120 */         PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "Asmodians cannot bind in Elyos territory.", ChatType.ANNOUNCEMENTS));
/*     */         return;
/*     */       } 
/* 123 */       if (worldType == WorldType.ABYSS) {
/*     */         
/* 125 */         if (player.getCommonData().getRace() == Race.ELYOS && player.getTarget().getObjectTemplate().getTemplateId() == 700401) {
/*     */           
/* 127 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "Elyos cannot bind in Asmodian territory.", ChatType.ANNOUNCEMENTS));
/*     */           return;
/*     */         } 
/* 130 */         if (player.getCommonData().getRace() == Race.ASMODIANS && player.getTarget().getObjectTemplate().getTemplateId() == 730071) {
/*     */           
/* 132 */           PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "Asmodians cannot bind in Elyos territory.", ChatType.ANNOUNCEMENTS));
/*     */           return;
/*     */         } 
/*     */       } 
/*     */     } 
/* 137 */     if (worldType == WorldType.PRISON) {
/*     */       
/* 139 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, "You cannot bind here.", ChatType.ANNOUNCEMENTS));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 144 */     bindHere(player);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\BindpointController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */