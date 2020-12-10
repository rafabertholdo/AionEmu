package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_RECONNECT_KEY extends AionServerPacket {
  private final int key;

  public SM_RECONNECT_KEY(int key) {
    this.key = key;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeD(buf, this.key);
  }
}
