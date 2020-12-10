package com.aionemu.gameserver.utils.gametime;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.GameTimeDAO;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.apache.log4j.Logger;

public class GameTimeManager {
  private static final Logger log = Logger.getLogger(GameTimeManager.class);

  private static GameTime instance;
  private static GameTimeUpdater updater;
  private static boolean clockStarted = false;

  static {
    GameTimeDAO dao = (GameTimeDAO) DAOManager.getDAO(GameTimeDAO.class);
    instance = new GameTime(dao.load());
  }

  public static GameTime getGameTime() {
    return instance;
  }

  public static void startClock() {
    if (clockStarted) {
      throw new IllegalStateException("Clock is already started");
    }

    updater = new GameTimeUpdater(getGameTime());
    ThreadPoolManager.getInstance().scheduleAtFixedRate(updater, 0L, 5000L);

    clockStarted = true;
  }

  public static boolean saveTime() {
    log.info("Game time saved...");
    return ((GameTimeDAO) DAOManager.getDAO(GameTimeDAO.class)).store(getGameTime().getTime());
  }
}
