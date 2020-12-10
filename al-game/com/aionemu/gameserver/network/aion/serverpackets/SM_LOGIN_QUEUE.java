package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LOGIN_QUEUE extends AionServerPacket {
  private int waitingPosition = 5;
  private int waitingTime = 60;
  private int waitingCount = 50;

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.waitingPosition);
    writeD(buf, this.waitingTime);
    writeD(buf, this.waitingCount);
  }
}
