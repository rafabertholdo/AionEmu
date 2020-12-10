/*    */ package com.aionemu.gameserver.services;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*    */ import com.aionemu.gameserver.world.container.KiskContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KiskService
/*    */ {
/* 33 */   private static final KiskContainer kiskContainer = new KiskContainer();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Kisk getKisk(Player player) {
/* 42 */     return kiskContainer.get(player);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void removeKisk(Kisk kisk) {
/* 51 */     for (Player member : kisk.getCurrentMemberList()) {
/*    */       
/* 53 */       kiskContainer.remove(member);
/* 54 */       member.setKisk(null);
/* 55 */       TeleportService.sendSetBindPoint(member);
/* 56 */       if (member.getLifeStats().isAlreadyDead()) {
/* 57 */         PacketSendUtility.sendPacket(member, (AionServerPacket)new SM_DIE(false, false, 0));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void onBind(Kisk kisk, Player player) {
/* 69 */     if (player.getKisk() != null) {
/*    */       
/* 71 */       kiskContainer.remove(player);
/* 72 */       player.getKisk().removePlayer(player);
/*    */     } 
/*    */     
/* 75 */     kiskContainer.add(kisk, player);
/* 76 */     kisk.addPlayer(player);
/*    */ 
/*    */     
/* 79 */     TeleportService.sendSetBindPoint(player);
/*    */ 
/*    */     
/* 82 */     PacketSendUtility.sendPacket(player, (AionServerPacket)SM_SYSTEM_MESSAGE.STR_BINDSTONE_REGISTER);
/*    */ 
/*    */     
/* 85 */     PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_LEVEL_UPDATE(player.getObjectId(), 2, player.getCommonData().getLevel()), true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void onLogin(Player player) {
/* 94 */     Kisk kisk = kiskContainer.get(player);
/* 95 */     if (kisk != null)
/* 96 */       kisk.reAddPlayer(player); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\KiskService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */