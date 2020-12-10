package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEAVE_GROUP_MEMBER extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
    writeD(buf, 0);
    writeH(buf, 0);
    writeC(buf, 0);
  }
}
