package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_PONG extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeC(buf, 0);
  }
}
