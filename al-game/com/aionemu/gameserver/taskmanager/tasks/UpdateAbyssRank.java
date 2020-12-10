package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ABYSS_RANK;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class UpdateAbyssRank extends TaskFromDBHandler {
  private static final Logger log = Logger.getLogger(UpdateAbyssRank.class);

  public String getTaskName() {
    return "update_abyss_rank";
  }

  public boolean isValid() {
    if (this.params.length > 0) {
      return false;
    }
    return true;
  }

  public void run() {
    log.info("Task[" + this.id + "] launched : updating abyss ranks for all online players !");
    setLastActivation();

    for (Player player : World.getInstance().getAllPlayers()) {

      player.getAbyssRank().doUpdate();
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_ABYSS_RANK(player.getAbyssRank()));
    }
  }
}
