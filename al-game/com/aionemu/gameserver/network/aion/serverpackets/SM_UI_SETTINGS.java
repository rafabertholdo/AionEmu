package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_UI_SETTINGS extends AionServerPacket {
  private byte[] data;
  private int type;

  public SM_UI_SETTINGS(byte[] data, int type) {
    this.data = data;
    this.type = type;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.type);
    writeC(buf, 28);
    writeB(buf, this.data);
  }
}
