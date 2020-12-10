/*    */ package com.aionemu.gameserver.services;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionConnection;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*    */ import com.aionemu.gameserver.world.World;
/*    */ import org.apache.log4j.Logger;
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
/*    */ 
/*    */ 
/*    */ public class DebugService
/*    */ {
/* 32 */   private static final Logger log = Logger.getLogger(DebugService.class);
/*    */   
/*    */   private static final int ANALYZE_PLAYERS_INTERVAL = 1800000;
/*    */ 
/*    */   
/*    */   public static final DebugService getInstance() {
/* 38 */     return SingletonHolder.instance;
/*    */   }
/*    */ 
/*    */   
/*    */   private DebugService() {
/* 43 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 48 */             DebugService.this.analyzeWorldPlayers();
/*    */           }
/*    */         },  1800000L, 1800000L);
/*    */     
/* 52 */     log.info("DebugService started. Analyze iterval: 1800000");
/*    */   }
/*    */ 
/*    */   
/*    */   private void analyzeWorldPlayers() {
/* 57 */     log.info("Starting analysis of world players at " + System.currentTimeMillis());
/*    */     
/* 59 */     for (Player player : World.getInstance().getAllPlayers()) {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 64 */       AionConnection connection = player.getClientConnection();
/* 65 */       if (connection == null) {
/*    */         
/* 67 */         log.warn(String.format("[DEBUG SERVICE] Player without connection: detected: ObjId %d, Name %s, Spawned %s", new Object[] { Integer.valueOf(player.getObjectId()), player.getName(), Boolean.valueOf(player.isSpawned()) }));
/*    */ 
/*    */ 
/*    */         
/*    */         continue;
/*    */       } 
/*    */ 
/*    */ 
/*    */       
/* 76 */       long lastPingTimeMS = connection.getLastPingTimeMS();
/* 77 */       long pingInterval = System.currentTimeMillis() - lastPingTimeMS;
/* 78 */       if (lastPingTimeMS > 0L && pingInterval > 300000L)
/*    */       {
/* 80 */         log.warn(String.format("[DEBUG SERVICE] Player with large ping interval: ObjId %d, Name %s, Spawned %s, PingMS %d", new Object[] { Integer.valueOf(player.getObjectId()), player.getName(), Boolean.valueOf(player.isSpawned()), Long.valueOf(pingInterval) }));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static class SingletonHolder
/*    */   {
/* 90 */     protected static final DebugService instance = new DebugService();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\DebugService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */