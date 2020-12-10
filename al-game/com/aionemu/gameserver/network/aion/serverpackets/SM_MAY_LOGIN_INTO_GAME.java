package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_MAY_LOGIN_INTO_GAME extends AionServerPacket {
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
  }
}
