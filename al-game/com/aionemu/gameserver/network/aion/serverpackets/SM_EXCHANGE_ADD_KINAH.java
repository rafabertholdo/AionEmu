package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_EXCHANGE_ADD_KINAH extends AionServerPacket {
  private long itemCount;
  private int action;

  public SM_EXCHANGE_ADD_KINAH(long itemCount, int action) {
    this.itemCount = itemCount;
    this.action = action;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.action);
    writeD(buf, (int) this.itemCount);
    writeD(buf, 0);
  }
}
