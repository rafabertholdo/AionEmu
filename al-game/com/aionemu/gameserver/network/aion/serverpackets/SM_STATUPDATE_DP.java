package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_STATUPDATE_DP extends AionServerPacket {
  private int currentDp;

  public SM_STATUPDATE_DP(int currentDp) {
    this.currentDp = currentDp;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.currentDp);
  }
}
