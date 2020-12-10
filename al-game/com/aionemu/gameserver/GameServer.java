package com.aionemu.gameserver;

import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.commons.log4j.exceptions.Log4jInitializationError;
import com.aionemu.commons.network.ConnectionFactory;
import com.aionemu.commons.network.DisconnectionThreadPool;
import com.aionemu.commons.network.NioServer;
import com.aionemu.commons.network.ServerCfg;
import com.aionemu.commons.services.LoggingService;
import com.aionemu.commons.utils.AEFastSet;
import com.aionemu.commons.utils.AEInfos;
import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.configs.Config;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.configs.main.ThreadConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.network.aion.GameConnectionFactoryImpl;
import com.aionemu.gameserver.network.chatserver.ChatServer;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.AnnouncementService;
import com.aionemu.gameserver.services.BrokerService;
import com.aionemu.gameserver.services.DebugService;
import com.aionemu.gameserver.services.DropService;
import com.aionemu.gameserver.services.DuelService;
import com.aionemu.gameserver.services.ExchangeService;
import com.aionemu.gameserver.services.GameTimeService;
import com.aionemu.gameserver.services.GroupService;
import com.aionemu.gameserver.services.MailService;
import com.aionemu.gameserver.services.PetitionService;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.services.WeatherService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.taskmanager.TaskManagerFromDB;
import com.aionemu.gameserver.taskmanager.tasks.ItemUpdater;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.utils.DeadlockDetector;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.ThreadUncaughtExceptionHandler;
import com.aionemu.gameserver.utils.VersionningService;
import com.aionemu.gameserver.utils.chathandlers.ChatHandlers;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.World;
import java.util.Set;
import org.apache.log4j.Logger;
























public class GameServer
{
  private static final Logger log = Logger.getLogger(GameServer.class);







  
  public static void main(String[] args) {
    new GameServer();
  }

  
  public GameServer() throws Log4jInitializationError {
    long start = System.currentTimeMillis();

    
    Thread.setDefaultUncaughtExceptionHandler((Thread.UncaughtExceptionHandler)new ThreadUncaughtExceptionHandler());

    
    LoggingService.init();
    log.info("Logging Initialized.");

    
    Config.load();

    
    AEInfos.printSection("DataBase");
    DatabaseFactory.init();
    
    DAOManager.init();

    
    AEInfos.printSection("Threads");
    ThreadConfig.load();
    ThreadPoolManager.getInstance();
    
    AEInfos.printSection("StaticDatas");
    DataManager.getInstance();
    
    AEInfos.printSection("IDFactory");
    IDFactory.getInstance();
    
    AEInfos.printSection("World");
    World.getInstance();

    
    ((PlayerDAO)DAOManager.getDAO(PlayerDAO.class)).setPlayersOffline(false);
    
    AEInfos.printSection("TaskManagers");
    PacketBroadcaster.getInstance();
    ItemUpdater.getInstance();
    TaskManagerFromDB.getInstance();
    
    AEInfos.printSection("Drops");
    DropService.getInstance();
    
    AEInfos.printSection("Spawns");
    SpawnEngine.getInstance();
    DayNightSpawnManager.getInstance().notifyChangeMode();
    
    AEInfos.printSection("Quests");
    QuestEngine.getInstance();
    QuestEngine.getInstance().load();
    
    AEInfos.printSection("Time");
    GameTimeService.getInstance();
    
    AEInfos.printSection("Announcements");
    AnnouncementService.getInstance();
    
    AEInfos.printSection("Debug");
    DebugService.getInstance();
    
    AEInfos.printSection("Zones");
    ZoneService.getInstance();
    
    AEInfos.printSection("Weather");
    WeatherService.getInstance();
    
    AEInfos.printSection("Duel");
    DuelService.getInstance();
    
    AEInfos.printSection("Mail");
    MailService.getInstance();
    
    AEInfos.printSection("Group");
    GroupService.getInstance();
    
    AEInfos.printSection("Alliance");
    AllianceService.getInstance();
    
    AEInfos.printSection("Broker");
    BrokerService.getInstance();
    
    AEInfos.printSection("Sieges");
    SiegeService.getInstance();
    Influence.getInstance();
    
    AEInfos.printSection("Exchange");
    ExchangeService.getInstance();
    
    AEInfos.printSection("Petitions");
    PetitionService.getInstance();
    
    AEInfos.printSection("ChatHandlers");
    ChatHandlers.getInstance();
    
    AEInfos.printSection("HTMLs");
    HTMLCache.getInstance();
    
    AEInfos.printSection("System");
    VersionningService.printFullVersionInfo();
    System.gc();
    System.runFinalization();
    AEInfos.printAllInfos();
    
    AEInfos.printSection("IOServer");
    ServerCfg aion = new ServerCfg(NetworkConfig.GAME_BIND_ADDRESS, NetworkConfig.GAME_PORT, "Game Connections", (ConnectionFactory)new GameConnectionFactoryImpl());
    NioServer nioServer = new NioServer(1, (DisconnectionThreadPool)ThreadPoolManager.getInstance(), new ServerCfg[] { aion });
    
    LoginServer loginServer = LoginServer.getInstance();
    ChatServer chatServer = ChatServer.getInstance();
    loginServer.setNioServer(nioServer);
    chatServer.setNioServer(nioServer);

    
    nioServer.connect();
    loginServer.connect();
    if (!GSConfig.DISABLE_CHAT_SERVER) {
      chatServer.connect();
    }
    GameTimeManager.startClock();
    
    if (OptionsConfig.DEADLOCK_DETECTOR_ENABLED) {
      
      AEInfos.printSection("DeadLock Detector");
      (new Thread((Runnable)new DeadlockDetector(OptionsConfig.DEADLOCK_DETECTOR_INTERVAL))).start();
    } 
    
    Runtime.getRuntime().addShutdownHook(ShutdownHook.getInstance());
    
    AEInfos.printSection("GameServerLog");
    log.info("Total Boot Time: " + ((System.currentTimeMillis() - start) / 1000L) + " seconds.");
    
    onStartup();
  }
  
  private static Set<StartupHook> startUpHooks = (Set<StartupHook>)new AEFastSet();

  
  public static synchronized void addStartupHook(StartupHook hook) {
    if (startUpHooks != null) {
      startUpHooks.add(hook);
    } else {
      hook.onStartup();
    } 
  }
  
  private static synchronized void onStartup() {
    Set<StartupHook> startupHooks = startUpHooks;
    
    startUpHooks = null;
    
    for (StartupHook hook : startupHooks)
      hook.onStartup(); 
  }
  
  public static interface StartupHook {
    void onStartup();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\GameServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
