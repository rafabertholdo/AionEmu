/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.WorldMapType;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class PunishmentService
/*     */ {
/*     */   public static void setIsInPrison(Player player, boolean state, long delayInMinutes) {
/*  43 */     stopPrisonTask(player, false);
/*  44 */     if (state) {
/*     */       
/*  46 */       long prisonTimer = player.getPrisonTimer();
/*  47 */       if (delayInMinutes > 0L) {
/*     */         
/*  49 */         prisonTimer = delayInMinutes * 60000L;
/*  50 */         schedulePrisonTask(player, prisonTimer);
/*  51 */         PacketSendUtility.sendMessage(player, "You are in prison for " + delayInMinutes + " minutes.\nIf you disconnect, the countdown will be stopped.");
/*     */       } 
/*     */       
/*  54 */       player.setStartPrison(System.currentTimeMillis());
/*  55 */       TeleportService.teleportToPrison(player);
/*  56 */       ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).punishPlayer(player, 1);
/*     */     }
/*     */     else {
/*     */       
/*  60 */       PacketSendUtility.sendMessage(player, "You removed from prison!");
/*  61 */       player.setPrisonTimer(0L);
/*     */       
/*  63 */       TeleportService.moveToBindLocation(player, true);
/*     */       
/*  65 */       ((PlayerPunishmentsDAO)DAOManager.getDAO(PlayerPunishmentsDAO.class)).unpunishPlayer(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopPrisonTask(Player player, boolean save) {
/*  75 */     Future<?> prisonTask = player.getController().getTask(TaskId.PRISON);
/*  76 */     if (prisonTask != null) {
/*     */       
/*  78 */       if (save) {
/*     */         
/*  80 */         long delay = player.getPrisonTimer();
/*  81 */         if (delay < 0L)
/*  82 */           delay = 0L; 
/*  83 */         player.setPrisonTimer(delay);
/*     */       } 
/*  85 */       player.getController().cancelTask(TaskId.PRISON);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updatePrisonStatus(Player player) {
/*  95 */     if (player.isInPrison()) {
/*     */       
/*  97 */       long prisonTimer = player.getPrisonTimer();
/*  98 */       if (prisonTimer > 0L) {
/*     */         
/* 100 */         schedulePrisonTask(player, prisonTimer);
/* 101 */         int timeInPrison = Math.round((float)(prisonTimer / 60000L));
/*     */         
/* 103 */         if (timeInPrison <= 0) {
/* 104 */           timeInPrison = 1;
/*     */         }
/* 106 */         PacketSendUtility.sendMessage(player, "You are still in prison for " + timeInPrison + " minute" + ((timeInPrison > 1) ? "s" : "") + ".");
/*     */ 
/*     */         
/* 109 */         player.setStartPrison(System.currentTimeMillis());
/*     */       } 
/* 111 */       if (player.getWorldId() != WorldMapType.PRISON.getId()) {
/* 112 */         TeleportService.teleportToPrison(player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void schedulePrisonTask(final Player player, long prisonTimer) {
/* 123 */     player.setPrisonTimer(prisonTimer);
/* 124 */     player.getController().addTask(TaskId.PRISON, ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 128 */               PunishmentService.setIsInPrison(player, false, 0L);
/*     */             }
/*     */           }prisonTimer));
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\PunishmentService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */