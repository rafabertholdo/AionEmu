package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_QUIT_RESPONSE extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 1);
    writeC(buf, 0);
  }
}
