/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_CHAT_INIT;
/*     */ import com.aionemu.gameserver.network.chatserver.ChatServer;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
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
/*     */ 
/*     */ 
/*     */ public class ChatService
/*     */ {
/*  37 */   private static final Logger log = Logger.getLogger(ChatService.class);
/*     */   
/*  39 */   private static Map<Integer, Player> players = new HashMap<Integer, Player>();
/*     */   
/*  41 */   private static byte[] ip = new byte[] { Byte.MAX_VALUE, 0, 0, 1 };
/*  42 */   private static int port = 10241;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void onPlayerLogin(final Player player) {
/*  51 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/*  56 */             if (!ChatService.isPlayerConnected(player)) {
/*     */               
/*  58 */               ChatServer.getInstance().sendPlayerLoginRequst(player);
/*     */             }
/*     */             else {
/*     */               
/*  62 */               ChatService.log.warn("Player already registered with chat server " + player.getName());
/*     */             } 
/*     */           }
/*     */         },  10000L);
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
/*     */   public static void onPlayerLogout(Player player) {
/*  77 */     players.remove(Integer.valueOf(player.getObjectId()));
/*  78 */     ChatServer.getInstance().sendPlayerLogout(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isPlayerConnected(Player player) {
/*  88 */     return players.containsKey(Integer.valueOf(player.getObjectId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void playerAuthed(int playerId, byte[] token) {
/*  97 */     Player player = World.getInstance().findPlayer(playerId);
/*  98 */     if (player != null) {
/*     */       
/* 100 */       players.put(Integer.valueOf(playerId), player);
/* 101 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_CHAT_INIT(token));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getIp() {
/* 110 */     return ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getPort() {
/* 118 */     return port;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setIp(byte[] _ip) {
/* 126 */     ip = _ip;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setPort(int _port) {
/* 134 */     port = _port;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ChatService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */