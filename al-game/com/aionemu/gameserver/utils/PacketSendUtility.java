/*     */ package com.aionemu.gameserver.utils;
/*     */ 
/*     */ import com.aionemu.commons.objects.filter.ObjectFilter;
/*     */ import com.aionemu.gameserver.model.ChatType;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.legion.Legion;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
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
/*     */ public class PacketSendUtility
/*     */ {
/*     */   public static void sendMessage(Player player, String msg) {
/*  45 */     sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, msg, ChatType.ANNOUNCEMENTS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendSysMessage(Player player, String msg) {
/*  56 */     sendPacket(player, (AionServerPacket)new SM_MESSAGE(0, null, msg, ChatType.SYSTEM_NOTICE));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendPacket(Player player, AionServerPacket packet) {
/*  67 */     if (player.getClientConnection() != null) {
/*  68 */       player.getClientConnection().sendPacket(packet);
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
/*     */ 
/*     */   
/*     */   public static void broadcastPacket(Player player, AionServerPacket packet, boolean toSelf) {
/*  83 */     if (toSelf) {
/*  84 */       sendPacket(player, packet);
/*     */     }
/*  86 */     broadcastPacket((VisibleObject)player, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacketAndReceive(VisibleObject visibleObject, AionServerPacket packet) {
/*  97 */     if (visibleObject instanceof Player) {
/*  98 */       sendPacket((Player)visibleObject, packet);
/*     */     }
/* 100 */     broadcastPacket(visibleObject, packet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacket(VisibleObject visibleObject, AionServerPacket packet) {
/* 111 */     for (VisibleObject obj : visibleObject.getKnownList().getKnownObjects().values()) {
/*     */       
/* 113 */       if (obj instanceof Player) {
/* 114 */         sendPacket((Player)obj, packet);
/*     */       }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void broadcastPacket(Player player, AionServerPacket packet, boolean toSelf, ObjectFilter<Player> filter) {
/* 133 */     if (toSelf)
/*     */     {
/* 135 */       sendPacket(player, packet);
/*     */     }
/*     */     
/* 138 */     for (VisibleObject obj : player.getKnownList().getKnownObjects().values()) {
/*     */       
/* 140 */       if (obj instanceof Player) {
/*     */         
/* 142 */         Player target = (Player)obj;
/* 143 */         if (filter.acceptObject(target)) {
/* 144 */           sendPacket(target, packet);
/*     */         }
/*     */       } 
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
/*     */   public static void broadcastPacketToLegion(Legion legion, AionServerPacket packet) {
/* 159 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers())
/*     */     {
/* 161 */       sendPacket(onlineLegionMember, packet);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void broadcastPacketToLegion(Legion legion, AionServerPacket packet, int playerObjId) {
/* 167 */     for (Player onlineLegionMember : legion.getOnlineLegionMembers()) {
/*     */       
/* 169 */       if (onlineLegionMember.getObjectId() != playerObjId)
/* 170 */         sendPacket(onlineLegionMember, packet); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\PacketSendUtility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */