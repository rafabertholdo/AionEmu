/*     */ package com.aionemu.gameserver;
/*     */ 
/*     */ import com.aionemu.commons.database.DatabaseFactory;
/*     */ import com.aionemu.commons.database.dao.DAOManager;
/*     */ import com.aionemu.commons.log4j.exceptions.Log4jInitializationError;
/*     */ import com.aionemu.commons.network.ConnectionFactory;
/*     */ import com.aionemu.commons.network.DisconnectionThreadPool;
/*     */ import com.aionemu.commons.network.NioServer;
/*     */ import com.aionemu.commons.network.ServerCfg;
/*     */ import com.aionemu.commons.services.LoggingService;
/*     */ import com.aionemu.commons.utils.AEFastSet;
/*     */ import com.aionemu.commons.utils.AEInfos;
/*     */ import com.aionemu.gameserver.cache.HTMLCache;
/*     */ import com.aionemu.gameserver.configs.Config;
/*     */ import com.aionemu.gameserver.configs.main.GSConfig;
/*     */ import com.aionemu.gameserver.configs.main.OptionsConfig;
/*     */ import com.aionemu.gameserver.configs.main.ThreadConfig;
/*     */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*     */ import com.aionemu.gameserver.dao.PlayerDAO;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.siege.Influence;
/*     */ import com.aionemu.gameserver.network.aion.GameConnectionFactoryImpl;
/*     */ import com.aionemu.gameserver.network.chatserver.ChatServer;
/*     */ import com.aionemu.gameserver.network.loginserver.LoginServer;
/*     */ import com.aionemu.gameserver.questEngine.QuestEngine;
/*     */ import com.aionemu.gameserver.services.AllianceService;
/*     */ import com.aionemu.gameserver.services.AnnouncementService;
/*     */ import com.aionemu.gameserver.services.BrokerService;
/*     */ import com.aionemu.gameserver.services.DebugService;
/*     */ import com.aionemu.gameserver.services.DropService;
/*     */ import com.aionemu.gameserver.services.DuelService;
/*     */ import com.aionemu.gameserver.services.ExchangeService;
/*     */ import com.aionemu.gameserver.services.GameTimeService;
/*     */ import com.aionemu.gameserver.services.GroupService;
/*     */ import com.aionemu.gameserver.services.MailService;
/*     */ import com.aionemu.gameserver.services.PetitionService;
/*     */ import com.aionemu.gameserver.services.SiegeService;
/*     */ import com.aionemu.gameserver.services.WeatherService;
/*     */ import com.aionemu.gameserver.services.ZoneService;
/*     */ import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnEngine;
/*     */ import com.aionemu.gameserver.taskmanager.TaskManagerFromDB;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
/*     */ import com.aionemu.gameserver.utils.DeadlockDetector;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.utils.ThreadUncaughtExceptionHandler;
/*     */ import com.aionemu.gameserver.utils.VersionningService;
/*     */ import com.aionemu.gameserver.utils.chathandlers.ChatHandlers;
/*     */ import com.aionemu.gameserver.utils.gametime.GameTimeManager;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ public class GameServer
/*     */ {
/*  81 */   private static final Logger log = Logger.getLogger(GameServer.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] args) {
/*  91 */     new GameServer();
/*     */   }
/*     */ 
/*     */   
/*     */   public GameServer() throws Log4jInitializationError {
/*  96 */     long start = System.currentTimeMillis();
/*     */ 
/*     */     
/*  99 */     Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new ThreadUncaughtExceptionHandler());
/*     */ 
/*     */     
/* 102 */     LoggingService.init();
/* 103 */     log.info("Logging Initialized.");
/*     */ 
/*     */     
/* 106 */     Config.load();
/*     */ 
/*     */     
/* 109 */     AEInfos.printSection("DataBase");
/* 110 */     DatabaseFactory.init();
/*     */     
/* 112 */     DAOManager.init();
/*     */ 
/*     */     
/* 115 */     AEInfos.printSection("Threads");
/* 116 */     ThreadConfig.load();
/* 117 */     ThreadPoolManager.getInstance();
/*     */     
/* 119 */     AEInfos.printSection("StaticDatas");
/* 120 */     DataManager.getInstance();
/*     */     
/* 122 */     AEInfos.printSection("IDFactory");
/* 123 */     IDFactory.getInstance();
/*     */     
/* 125 */     AEInfos.printSection("World");
/* 126 */     World.getInstance();
/*     */ 
/*     */     
/* 129 */     ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).setPlayersOffline(false);
/*     */     
/* 131 */     AEInfos.printSection("TaskManagers");
/* 132 */     PacketBroadcaster.getInstance();
/* 133 */     ItemUpdater.getInstance();
/* 134 */     TaskManagerFromDB.getInstance();
/*     */     
/* 136 */     AEInfos.printSection("Drops");
/* 137 */     DropService.getInstance();
/*     */     
/* 139 */     AEInfos.printSection("Spawns");
/* 140 */     SpawnEngine.getInstance();
/* 141 */     DayNightSpawnManager.getInstance().notifyChangeMode();
/*     */     
/* 143 */     AEInfos.printSection("Quests");
/* 144 */     QuestEngine.getInstance();
/* 145 */     QuestEngine.getInstance().load();
/*     */     
/* 147 */     AEInfos.printSection("Time");
/* 148 */     GameTimeService.getInstance();
/*     */     
/* 150 */     AEInfos.printSection("Announcements");
/* 151 */     AnnouncementService.getInstance();
/*     */     
/* 153 */     AEInfos.printSection("Debug");
/* 154 */     DebugService.getInstance();
/*     */     
/* 156 */     AEInfos.printSection("Zones");
/* 157 */     ZoneService.getInstance();
/*     */     
/* 159 */     AEInfos.printSection("Weather");
/* 160 */     WeatherService.getInstance();
/*     */     
/* 162 */     AEInfos.printSection("Duel");
/* 163 */     DuelService.getInstance();
/*     */     
/* 165 */     AEInfos.printSection("Mail");
/* 166 */     MailService.getInstance();
/*     */     
/* 168 */     AEInfos.printSection("Group");
/* 169 */     GroupService.getInstance();
/*     */     
/* 171 */     AEInfos.printSection("Alliance");
/* 172 */     AllianceService.getInstance();
/*     */     
/* 174 */     AEInfos.printSection("Broker");
/* 175 */     BrokerService.getInstance();
/*     */     
/* 177 */     AEInfos.printSection("Sieges");
/* 178 */     SiegeService.getInstance();
/* 179 */     Influence.getInstance();
/*     */     
/* 181 */     AEInfos.printSection("Exchange");
/* 182 */     ExchangeService.getInstance();
/*     */     
/* 184 */     AEInfos.printSection("Petitions");
/* 185 */     PetitionService.getInstance();
/*     */     
/* 187 */     AEInfos.printSection("ChatHandlers");
/* 188 */     ChatHandlers.getInstance();
/*     */     
/* 190 */     AEInfos.printSection("HTMLs");
/* 191 */     HTMLCache.getInstance();
/*     */     
/* 193 */     AEInfos.printSection("System");
/* 194 */     VersionningService.printFullVersionInfo();
/* 195 */     System.gc();
/* 196 */     System.runFinalization();
/* 197 */     AEInfos.printAllInfos();
/*     */     
/* 199 */     AEInfos.printSection("IOServer");
/* 200 */     ServerCfg aion = new ServerCfg(NetworkConfig.GAME_BIND_ADDRESS, NetworkConfig.GAME_PORT, "Game Connections", (ConnectionFactory)new GameConnectionFactoryImpl());
/* 201 */     NioServer nioServer = new NioServer(1, (DisconnectionThreadPool)ThreadPoolManager.getInstance(), new ServerCfg[] { aion });
/*     */     
/* 203 */     LoginServer loginServer = LoginServer.getInstance();
/* 204 */     ChatServer chatServer = ChatServer.getInstance();
/* 205 */     loginServer.setNioServer(nioServer);
/* 206 */     chatServer.setNioServer(nioServer);
/*     */ 
/*     */     
/* 209 */     nioServer.connect();
/* 210 */     loginServer.connect();
/* 211 */     if (!GSConfig.DISABLE_CHAT_SERVER) {
/* 212 */       chatServer.connect();
/*     */     }
/* 214 */     GameTimeManager.startClock();
/*     */     
/* 216 */     if (OptionsConfig.DEADLOCK_DETECTOR_ENABLED) {
/*     */       
/* 218 */       AEInfos.printSection("DeadLock Detector");
/* 219 */       (new Thread((Runnable)new DeadlockDetector(OptionsConfig.DEADLOCK_DETECTOR_INTERVAL))).start();
/*     */     } 
/*     */     
/* 222 */     Runtime.getRuntime().addShutdownHook(ShutdownHook.getInstance());
/*     */     
/* 224 */     AEInfos.printSection("GameServerLog");
/* 225 */     log.info("Total Boot Time: " + ((System.currentTimeMillis() - start) / 1000L) + " seconds.");
/*     */     
/* 227 */     onStartup();
/*     */   }
/*     */   
/* 230 */   private static Set<StartupHook> startUpHooks = (Set<StartupHook>)new AEFastSet();
/*     */ 
/*     */   
/*     */   public static synchronized void addStartupHook(StartupHook hook) {
/* 234 */     if (startUpHooks != null) {
/* 235 */       startUpHooks.add(hook);
/*     */     } else {
/* 237 */       hook.onStartup();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static synchronized void onStartup() {
/* 242 */     Set<StartupHook> startupHooks = startUpHooks;
/*     */     
/* 244 */     startUpHooks = null;
/*     */     
/* 246 */     for (StartupHook hook : startupHooks)
/* 247 */       hook.onStartup(); 
/*     */   }
/*     */   
/*     */   public static interface StartupHook {
/*     */     void onStartup();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\GameServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */