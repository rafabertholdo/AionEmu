package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_EMOTION_LIST extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeH(buf, 13);
    for (int i = 0; i < 13; i++) {

      writeD(buf, 64 + i);
      writeH(buf, 0);
    }
  }
}
