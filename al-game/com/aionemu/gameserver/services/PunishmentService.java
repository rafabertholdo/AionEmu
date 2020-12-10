package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.dao.PlayerPunishmentsDAO;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.WorldMapType;
import java.util.concurrent.Future;

public class PunishmentService {
  public static void setIsInPrison(Player player, boolean state, long delayInMinutes) {
    stopPrisonTask(player, false);
    if (state) {

      long prisonTimer = player.getPrisonTimer();
      if (delayInMinutes > 0L) {

        prisonTimer = delayInMinutes * 60000L;
        schedulePrisonTask(player, prisonTimer);
        PacketSendUtility.sendMessage(player,
            "You are in prison for " + delayInMinutes + " minutes.\nIf you disconnect, the countdown will be stopped.");
      }

      player.setStartPrison(System.currentTimeMillis());
      TeleportService.teleportToPrison(player);
      ((PlayerPunishmentsDAO) DAOManager.getDAO(PlayerPunishmentsDAO.class)).punishPlayer(player, 1);
    } else {

      PacketSendUtility.sendMessage(player, "You removed from prison!");
      player.setPrisonTimer(0L);

      TeleportService.moveToBindLocation(player, true);

      ((PlayerPunishmentsDAO) DAOManager.getDAO(PlayerPunishmentsDAO.class)).unpunishPlayer(player);
    }
  }

  public static void stopPrisonTask(Player player, boolean save) {
    Future<?> prisonTask = player.getController().getTask(TaskId.PRISON);
    if (prisonTask != null) {

      if (save) {

        long delay = player.getPrisonTimer();
        if (delay < 0L)
          delay = 0L;
        player.setPrisonTimer(delay);
      }
      player.getController().cancelTask(TaskId.PRISON);
    }
  }

  public static void updatePrisonStatus(Player player) {
    if (player.isInPrison()) {

      long prisonTimer = player.getPrisonTimer();
      if (prisonTimer > 0L) {

        schedulePrisonTask(player, prisonTimer);
        int timeInPrison = Math.round((float) (prisonTimer / 60000L));

        if (timeInPrison <= 0) {
          timeInPrison = 1;
        }
        PacketSendUtility.sendMessage(player,
            "You are still in prison for " + timeInPrison + " minute" + ((timeInPrison > 1) ? "s" : "") + ".");

        player.setStartPrison(System.currentTimeMillis());
      }
      if (player.getWorldId() != WorldMapType.PRISON.getId()) {
        TeleportService.teleportToPrison(player);
      }
    }
  }

  private static void schedulePrisonTask(final Player player, long prisonTimer) {
    player.setPrisonTimer(prisonTimer);
    player.getController().addTask(TaskId.PRISON, ThreadPoolManager.getInstance().schedule(new Runnable()
          {
            public void run()
            {
              PunishmentService.setIsInPrison(player, false, 0L);
            }
          }prisonTimer));
  }
}
