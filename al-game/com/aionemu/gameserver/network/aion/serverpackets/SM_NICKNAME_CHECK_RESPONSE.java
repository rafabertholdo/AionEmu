package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_NICKNAME_CHECK_RESPONSE extends AionServerPacket {
  private final int value;

  public SM_NICKNAME_CHECK_RESPONSE(int value) {
    this.value = value;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.value);
  }
}
