package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class DebugService {
  private static final Logger log = Logger.getLogger(DebugService.class);

  private static final int ANALYZE_PLAYERS_INTERVAL = 1800000;

  public static final DebugService getInstance() {
    return SingletonHolder.instance;
  }

  private DebugService() {
    ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

      public void run() {
        DebugService.this.analyzeWorldPlayers();
      }
    }, 1800000L, 1800000L);

    log.info("DebugService started. Analyze iterval: 1800000");
  }

  private void analyzeWorldPlayers() {
    log.info("Starting analysis of world players at " + System.currentTimeMillis());

    for (Player player : World.getInstance().getAllPlayers()) {

      AionConnection connection = player.getClientConnection();
      if (connection == null) {

        log.warn(String.format("[DEBUG SERVICE] Player without connection: detected: ObjId %d, Name %s, Spawned %s",
            new Object[] { Integer.valueOf(player.getObjectId()), player.getName(),
                Boolean.valueOf(player.isSpawned()) }));

        continue;
      }

      long lastPingTimeMS = connection.getLastPingTimeMS();
      long pingInterval = System.currentTimeMillis() - lastPingTimeMS;
      if (lastPingTimeMS > 0L && pingInterval > 300000L) {
        log.warn(
            String.format("[DEBUG SERVICE] Player with large ping interval: ObjId %d, Name %s, Spawned %s, PingMS %d",
                new Object[] { Integer.valueOf(player.getObjectId()), player.getName(),
                    Boolean.valueOf(player.isSpawned()), Long.valueOf(pingInterval) }));
      }
    }
  }

  private static class SingletonHolder {
    protected static final DebugService instance = new DebugService();
  }
}
