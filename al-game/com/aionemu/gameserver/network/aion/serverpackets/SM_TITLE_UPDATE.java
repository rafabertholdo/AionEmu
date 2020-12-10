package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TITLE_UPDATE extends AionServerPacket {
  private int objectId;
  private int titleId;

  public SM_TITLE_UPDATE(Player player, int titleId) {
    this.objectId = player.getObjectId();
    this.titleId = titleId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.objectId);
    writeD(buf, this.titleId);
  }
}
