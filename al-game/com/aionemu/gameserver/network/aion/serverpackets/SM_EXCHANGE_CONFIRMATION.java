package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_EXCHANGE_CONFIRMATION extends AionServerPacket {
  private int action;

  public SM_EXCHANGE_CONFIRMATION(int action) {
    this.action = action;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.action);
  }
}
