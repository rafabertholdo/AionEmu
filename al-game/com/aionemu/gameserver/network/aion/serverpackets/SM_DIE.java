package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DIE extends AionServerPacket {
  private boolean hasRebirth;
  private boolean hasItem;
  private int remainingKiskTime;

  public SM_DIE(boolean hasRebirth, boolean hasItem, int remainingKiskTime) {
    this.hasRebirth = hasRebirth;
    this.hasItem = hasItem;
    this.remainingKiskTime = remainingKiskTime;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.hasRebirth ? 1 : 0);
    writeC(buf, this.hasItem ? 1 : 0);
    writeD(buf, this.remainingKiskTime);
  }
}
