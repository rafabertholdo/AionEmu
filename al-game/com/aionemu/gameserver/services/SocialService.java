/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.BlockListDAO;
/*     */ import com.aionemu.gameserver.dao.FriendListDAO;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.BlockedPlayer;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Friend;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_BLOCK_RESPONSE;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_LIST;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_NOTIFY;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_FRIEND_RESPONSE;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class SocialService
/*     */ {
/*     */   public static boolean addBlockedUser(Player player, Player blockedPlayer, String reason) {
/*  52 */     if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).addBlockedUser(player.getObjectId(), blockedPlayer.getObjectId(), reason)) {
/*     */       
/*  54 */       player.getBlockList().add(new BlockedPlayer(blockedPlayer.getCommonData(), reason));
/*     */       
/*  56 */       player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(0, blockedPlayer.getName()));
/*     */       
/*  58 */       player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());
/*     */ 
/*     */       
/*  61 */       return true;
/*     */     } 
/*  63 */     return false;
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
/*     */   public static boolean deleteBlockedUser(Player player, int blockedUserId) {
/*  77 */     if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).delBlockedUser(player.getObjectId(), blockedUserId)) {
/*     */       
/*  79 */       player.getBlockList().remove(blockedUserId);
/*  80 */       player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_RESPONSE(1, ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonData(blockedUserId).getName()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  86 */       player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());
/*     */       
/*  88 */       return true;
/*     */     } 
/*  90 */     return false;
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
/*     */   public static boolean setBlockedReason(Player player, BlockedPlayer target, String reason) {
/* 106 */     if (!target.getReason().equals(reason))
/*     */     {
/* 108 */       if (((BlockListDAO)DAOManager.getDAO(BlockListDAO.class)).setReason(player.getObjectId(), target.getObjId(), reason)) {
/*     */         
/* 110 */         target.setReason(reason);
/* 111 */         player.getClientConnection().sendPacket((AionServerPacket)new SM_BLOCK_LIST());
/*     */         
/* 113 */         return true;
/*     */       } 
/*     */     }
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void makeFriends(Player friend1, Player friend2) {
/* 126 */     ((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).addFriends(friend1, friend2);
/*     */     
/* 128 */     friend1.getFriendList().addFriend(new Friend(friend2.getCommonData()));
/* 129 */     friend2.getFriendList().addFriend(new Friend(friend1.getCommonData()));
/*     */     
/* 131 */     friend1.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
/*     */     
/* 133 */     friend2.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
/*     */ 
/*     */     
/* 136 */     friend1.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend2.getName(), 0));
/*     */     
/* 138 */     friend2.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend1.getName(), 0));
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
/*     */   public static void deleteFriend(Player deleter, int exFriend2Id) {
/* 152 */     if (((FriendListDAO)DAOManager.getDAO(FriendListDAO.class)).delFriends(deleter.getObjectId(), exFriend2Id)) {
/*     */ 
/*     */       
/* 155 */       Player friend2Player = PlayerService.getCachedPlayer(exFriend2Id);
/*     */       
/* 157 */       if (friend2Player == null) {
/* 158 */         friend2Player = World.getInstance().findPlayer(exFriend2Id);
/*     */       }
/* 160 */       String friend2Name = (friend2Player != null) ? friend2Player.getName() : ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).loadPlayerCommonData(exFriend2Id).getName();
/*     */ 
/*     */ 
/*     */       
/* 164 */       deleter.getFriendList().delFriend(exFriend2Id);
/*     */       
/* 166 */       deleter.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
/*     */       
/* 168 */       deleter.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_RESPONSE(friend2Name, 6));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 174 */       if (friend2Player != null) {
/*     */         
/* 176 */         friend2Player.getFriendList().delFriend(deleter.getObjectId());
/*     */         
/* 178 */         if (friend2Player.isOnline()) {
/*     */           
/* 180 */           friend2Player.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_NOTIFY(2, deleter.getName()));
/*     */           
/* 182 */           friend2Player.getClientConnection().sendPacket((AionServerPacket)new SM_FRIEND_LIST());
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\SocialService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */