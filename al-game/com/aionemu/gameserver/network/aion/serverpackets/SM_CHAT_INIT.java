package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_CHAT_INIT extends AionServerPacket {
  private byte[] token;

  public SM_CHAT_INIT(byte[] token) {
    this.token = token;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.token.length);
    writeB(buf, this.token);
  }
}
