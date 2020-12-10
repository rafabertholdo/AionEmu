package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_WEATHER extends AionServerPacket {
  private int weatherCode;

  public SM_WEATHER(int weatherCode) {
    this.weatherCode = weatherCode;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.weatherCode);
    writeC(buf, 0);
  }
}
