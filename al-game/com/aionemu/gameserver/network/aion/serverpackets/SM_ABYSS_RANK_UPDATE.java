package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_ABYSS_RANK_UPDATE extends AionServerPacket {
  private Player player;
  private int rankId;

  public SM_ABYSS_RANK_UPDATE(Player player) {
    this.player = player;
    this.rankId = player.getAbyssRank().getRank().getId();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeD(buf, this.rankId);
  }
}
