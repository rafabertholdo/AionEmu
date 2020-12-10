package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_EXCHANGE_REQUEST extends AionServerPacket {
  private String receiver;

  public SM_EXCHANGE_REQUEST(String receiver) {
    this.receiver = receiver;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeS(buf, this.receiver);
  }
}
