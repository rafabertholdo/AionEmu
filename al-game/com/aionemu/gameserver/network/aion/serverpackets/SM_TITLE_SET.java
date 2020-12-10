package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TITLE_SET extends AionServerPacket {
  private int titleId;

  public SM_TITLE_SET(int titleId) {
    this.titleId = titleId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.titleId);
  }
}
