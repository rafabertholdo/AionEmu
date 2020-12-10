package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.listeners.TitleChangeListener;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_SET;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_UPDATE;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class CM_TITLE_SET extends AionClientPacket {
  private int titleId;

  public CM_TITLE_SET(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.titleId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    sendPacket((AionServerPacket) new SM_TITLE_SET(this.titleId));
    PacketSendUtility.broadcastPacket((VisibleObject) player,
        (AionServerPacket) new SM_TITLE_UPDATE(player, this.titleId));
    if (player.getCommonData().getTitleId() > 0) {
      if (player.getGameStats() != null) {
        TitleChangeListener.onTitleChange((CreatureGameStats) player.getGameStats(),
            player.getCommonData().getTitleId(), false);
      }
    }
    player.getCommonData().setTitleId(this.titleId);
    if (player.getGameStats() != null) {
      TitleChangeListener.onTitleChange((CreatureGameStats) player.getGameStats(), this.titleId, true);
    }
  }
}
