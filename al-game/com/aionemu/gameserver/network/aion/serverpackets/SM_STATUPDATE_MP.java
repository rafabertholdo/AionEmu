package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_STATUPDATE_MP extends AionServerPacket {
  private int currentMp;
  private int maxMp;

  public SM_STATUPDATE_MP(int currentMp, int maxMp) {
    this.currentMp = currentMp;
    this.maxMp = maxMp;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.currentMp);
    writeD(buf, this.maxMp);
  }
}
