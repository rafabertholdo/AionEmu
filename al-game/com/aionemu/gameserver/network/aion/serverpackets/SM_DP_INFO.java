package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DP_INFO extends AionServerPacket {
  private int playerObjectId;
  private int currentDp;

  public SM_DP_INFO(int playerObjectId, int currentDp) {
    this.playerObjectId = playerObjectId;
    this.currentDp = currentDp;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjectId);
    writeH(buf, this.currentDp);
  }
}
