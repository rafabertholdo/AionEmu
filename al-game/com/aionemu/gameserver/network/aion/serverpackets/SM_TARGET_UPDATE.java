package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TARGET_UPDATE extends AionServerPacket {
  private Player player;

  public SM_TARGET_UPDATE(Player player) {
    this.player = player;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeD(buf, (this.player.getTarget() == null) ? 0 : this.player.getTarget().getObjectId());
  }
}
