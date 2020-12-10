package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_FLY_TIME extends AionServerPacket {
  private int currentFp;
  private int maxFp;

  public SM_FLY_TIME(int currentFp, int maxFp) {
    this.currentFp = currentFp;
    this.maxFp = maxFp;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.currentFp);
    writeD(buf, this.maxFp);
  }
}
