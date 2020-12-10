package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_RIFT_STATUS extends AionServerPacket {
  private int usedEntries;
  private int maxEntries;
  private int maxLevel;
  private int targetObjectId;

  public SM_RIFT_STATUS(int targetObjId, int usedEntries, int maxEntries, int maxLevel) {
    this.targetObjectId = targetObjId;
    this.usedEntries = usedEntries;
    this.maxEntries = maxEntries;
    this.maxLevel = maxLevel;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeD(buf, this.usedEntries);
    writeD(buf, this.maxEntries);
    writeD(buf, 6793);
    writeD(buf, 25);
    writeD(buf, this.maxLevel);
  }
}
