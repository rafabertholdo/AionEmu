package com.aionemu.gameserver;

import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
import com.aionemu.gameserver.configs.main.ShutdownConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.services.PlayerService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.gametime.GameTimeManager;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class ShutdownHook extends Thread {
  private static final Logger log = Logger.getLogger(ShutdownHook.class);

  public static ShutdownHook getInstance() {
    return SingletonHolder.INSTANCE;
  }

  public void run() {
    if (ShutdownConfig.HOOK_MODE == 1) {

      shutdownHook(ShutdownConfig.HOOK_DELAY, ShutdownConfig.ANNOUNCE_INTERVAL, ShutdownMode.SHUTDOWN);
    } else if (ShutdownConfig.HOOK_MODE == 2) {

      shutdownHook(ShutdownConfig.HOOK_DELAY, ShutdownConfig.ANNOUNCE_INTERVAL, ShutdownMode.RESTART);
    }
  }

  public enum ShutdownMode {
    NONE("terminating"), SHUTDOWN("shutting down"), RESTART("restarting");

    private final String text;

    ShutdownMode(String text) {
      this.text = text;
    }

    public String getText() {
      return this.text;
    }
  }

  private void sendShutdownMessage(int seconds) {
    try {
      for (Player player : World.getInstance().getAllPlayers()) {

        if (player != null && player.getClientConnection() != null) {
          player.getClientConnection().sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.SERVER_SHUTDOWN(seconds));
        }
      }
    } catch (Exception e) {

      log.error(e.getMessage());
    }
  }

  private void sendShutdownStatus(boolean status) {
    try {
      for (Player player : World.getInstance().getAllPlayers()) {

        if (player != null && player.getClientConnection() != null) {
          player.getController().setInShutdownProgress(status);
        }
      }
    } catch (Exception e) {

      log.error(e.getMessage());
    }
  }

  private void shutdownHook(int duration, int interval, ShutdownMode mode) {
    int i;
    for (i = duration; i >= interval; i -= interval) {

      try {
        if (World.getInstance().getAllPlayers().size() == 0) {

          log.info("Runtime is " + mode.getText() + " now ...");

          break;
        }
        log.info("Runtime is " + mode.getText() + " in " + i + " seconds.");
        sendShutdownMessage(i);
        sendShutdownStatus(ShutdownConfig.SAFE_REBOOT);

        if (i > interval) {
          sleep((interval * 1000));
        } else {
          sleep(1000L);
        }

      } catch (InterruptedException e) {
        return;
      }
    }

    LoginServer.getInstance().gameServerDisconnected();

    for (Player player : World.getInstance().getAllPlayers()) {

      try {
        PlayerService.playerLoggedOut(player);
      } catch (Exception e) {

        log.error("Error while saving player " + e.getMessage());
      }
    }
    log.info("All players are disconnected...");

    RunnableStatsManager.dumpClassStats(RunnableStatsManager.SortBy.AVG);

    GameTimeManager.saveTime();

    ThreadPoolManager.getInstance().shutdown();

    if (mode == ShutdownMode.RESTART) {
      Runtime.getRuntime().halt(2);
    } else {
      Runtime.getRuntime().halt(0);
    }
    log.info("Runtime is " + mode.getText() + " now...");
  }

  public void doShutdown(int delay, int announceInterval, ShutdownMode mode) {
    shutdownHook(delay, announceInterval, mode);
  }

  private static final class SingletonHolder {
    private static final ShutdownHook INSTANCE = new ShutdownHook();
  }
}
