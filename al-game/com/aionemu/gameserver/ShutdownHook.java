/*     */ package com.aionemu.gameserver;
/*     */ 
/*     */ import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
/*     */ import com.aionemu.gameserver.configs.main.ShutdownConfig;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
/*     */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*     */ import com.aionemu.gameserver.services.PlayerService;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.gametime.GameTimeManager;
/*     */ import com.aionemu.gameserver.world.World;
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
/*     */ public class ShutdownHook
/*     */   extends Thread
/*     */ {
/*  39 */   private static final Logger log = Logger.getLogger(ShutdownHook.class);
/*     */ 
/*     */   
/*     */   public static ShutdownHook getInstance() {
/*  43 */     return SingletonHolder.INSTANCE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  49 */     if (ShutdownConfig.HOOK_MODE == 1) {
/*     */       
/*  51 */       shutdownHook(ShutdownConfig.HOOK_DELAY, ShutdownConfig.ANNOUNCE_INTERVAL, ShutdownMode.SHUTDOWN);
/*     */     }
/*  53 */     else if (ShutdownConfig.HOOK_MODE == 2) {
/*     */       
/*  55 */       shutdownHook(ShutdownConfig.HOOK_DELAY, ShutdownConfig.ANNOUNCE_INTERVAL, ShutdownMode.RESTART);
/*     */     } 
/*     */   }
/*     */   
/*     */   public enum ShutdownMode
/*     */   {
/*  61 */     NONE("terminating"),
/*  62 */     SHUTDOWN("shutting down"),
/*  63 */     RESTART("restarting");
/*     */     
/*     */     private final String text;
/*     */ 
/*     */     
/*     */     ShutdownMode(String text) {
/*  69 */       this.text = text;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getText() {
/*  74 */       return this.text;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendShutdownMessage(int seconds) {
/*     */     try {
/*  82 */       for (Player player : World.getInstance().getAllPlayers()) {
/*     */         
/*  84 */         if (player != null && player.getClientConnection() != null) {
/*  85 */           player.getClientConnection().sendPacket((AionServerPacket)SM_SYSTEM_MESSAGE.SERVER_SHUTDOWN(seconds));
/*     */         }
/*     */       } 
/*  88 */     } catch (Exception e) {
/*     */       
/*  90 */       log.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendShutdownStatus(boolean status) {
/*     */     try {
/*  98 */       for (Player player : World.getInstance().getAllPlayers()) {
/*     */         
/* 100 */         if (player != null && player.getClientConnection() != null) {
/* 101 */           player.getController().setInShutdownProgress(status);
/*     */         }
/*     */       } 
/* 104 */     } catch (Exception e) {
/*     */       
/* 106 */       log.error(e.getMessage());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void shutdownHook(int duration, int interval, ShutdownMode mode) {
/*     */     int i;
/* 112 */     for (i = duration; i >= interval; i -= interval) {
/*     */ 
/*     */       
/*     */       try {
/* 116 */         if (World.getInstance().getAllPlayers().size() == 0) {
/*     */           
/* 118 */           log.info("Runtime is " + mode.getText() + " now ...");
/*     */           
/*     */           break;
/*     */         } 
/* 122 */         log.info("Runtime is " + mode.getText() + " in " + i + " seconds.");
/* 123 */         sendShutdownMessage(i);
/* 124 */         sendShutdownStatus(ShutdownConfig.SAFE_REBOOT);
/*     */         
/* 126 */         if (i > interval)
/*     */         {
/* 128 */           sleep((interval * 1000));
/*     */         }
/*     */         else
/*     */         {
/* 132 */           sleep(1000L);
/*     */         }
/*     */       
/* 135 */       } catch (InterruptedException e) {
/*     */         return;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 142 */     LoginServer.getInstance().gameServerDisconnected();
/*     */ 
/*     */     
/* 145 */     for (Player player : World.getInstance().getAllPlayers()) {
/*     */ 
/*     */       
/*     */       try {
/* 149 */         PlayerService.playerLoggedOut(player);
/*     */       }
/* 151 */       catch (Exception e) {
/*     */         
/* 153 */         log.error("Error while saving player " + e.getMessage());
/*     */       } 
/*     */     } 
/* 156 */     log.info("All players are disconnected...");
/*     */     
/* 158 */     RunnableStatsManager.dumpClassStats(RunnableStatsManager.SortBy.AVG);
/*     */ 
/*     */     
/* 161 */     GameTimeManager.saveTime();
/*     */     
/* 163 */     ThreadPoolManager.getInstance().shutdown();
/*     */ 
/*     */     
/* 166 */     if (mode == ShutdownMode.RESTART) {
/* 167 */       Runtime.getRuntime().halt(2);
/*     */     } else {
/* 169 */       Runtime.getRuntime().halt(0);
/*     */     } 
/* 171 */     log.info("Runtime is " + mode.getText() + " now...");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doShutdown(int delay, int announceInterval, ShutdownMode mode) {
/* 182 */     shutdownHook(delay, announceInterval, mode);
/*     */   }
/*     */   
/*     */   private static final class SingletonHolder
/*     */   {
/* 187 */     private static final ShutdownHook INSTANCE = new ShutdownHook();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\ShutdownHook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */