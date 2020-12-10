package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GAME_TIME;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class GameTimeService {
  private static Logger log = Logger.getLogger(GameTimeService.class);
  private static final int GAMETIME_UPDATE = 180000;

  public static final GameTimeService getInstance() {
    return SingletonHolder.instance;
  }

  private GameTimeService() {
    ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
        {
          
          public void run()
          {
            GameTimeService.log.info("Sending current game time to all players");
            for (Player player : World.getInstance().getAllPlayers())
            {
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_GAME_TIME());
            }
          }
        }180000L, 180000L);
    
    log.info("GameTimeService started. Update interval:180000");
  }

  private static class SingletonHolder {
    protected static final GameTimeService instance = new GameTimeService();
  }
}
