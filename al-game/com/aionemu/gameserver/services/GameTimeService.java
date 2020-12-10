/*    */ package com.aionemu.gameserver.services;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*    */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
/*    */ import com.aionemu.gameserver.utils.PacketSendUtility;
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
/*    */ public class GameTimeService
/*    */ {
/* 33 */   private static Logger log = Logger.getLogger(GameTimeService.class);
/*    */   private static final int GAMETIME_UPDATE = 180000;
/*    */   
/*    */   public static final GameTimeService getInstance() {
/* 37 */     return SingletonHolder.instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private GameTimeService() {
/* 47 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*    */         {
/*    */           
/*    */           public void run()
/*    */           {
/* 52 */             GameTimeService.log.info("Sending current game time to all players");
/* 53 */             for (Player player : World.getInstance().getAllPlayers())
/*    */             {
/* 55 */               PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_GAME_TIME());
/*    */             }
/*    */           }
/*    */         }180000L, 180000L);
/*    */     
/* 60 */     log.info("GameTimeService started. Update interval:180000");
/*    */   }
/*    */ 
/*    */   
/*    */   private static class SingletonHolder
/*    */   {
/* 66 */     protected static final GameTimeService instance = new GameTimeService();
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\GameTimeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */