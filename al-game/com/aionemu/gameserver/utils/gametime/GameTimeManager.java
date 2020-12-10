/*    */ package com.aionemu.gameserver.utils.gametime;
/*    */ 
/*    */ import com.aionemu.commons.database.dao.DAOManager;
/*    */ import com.aionemu.gameserver.dao.GameTimeDAO;
/*    */ import com.aionemu.gameserver.utils.ThreadPoolManager;
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
/*    */ 
/*    */ 
/*    */ public class GameTimeManager
/*    */ {
/* 33 */   private static final Logger log = Logger.getLogger(GameTimeManager.class);
/*    */   
/*    */   private static GameTime instance;
/*    */   private static GameTimeUpdater updater;
/*    */   private static boolean clockStarted = false;
/*    */   
/*    */   static {
/* 40 */     GameTimeDAO dao = (GameTimeDAO)DAOManager.getDAO(GameTimeDAO.class);
/* 41 */     instance = new GameTime(dao.load());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static GameTime getGameTime() {
/* 51 */     return instance;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void startClock() {
/* 62 */     if (clockStarted)
/*    */     {
/* 64 */       throw new IllegalStateException("Clock is already started");
/*    */     }
/*    */     
/* 67 */     updater = new GameTimeUpdater(getGameTime());
/* 68 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(updater, 0L, 5000L);
/*    */     
/* 70 */     clockStarted = true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean saveTime() {
/* 80 */     log.info("Game time saved...");
/* 81 */     return ((GameTimeDAO)DAOManager.getDAO(GameTimeDAO.class)).store(getGameTime().getTime());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\gametime\GameTimeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */