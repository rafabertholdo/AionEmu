package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SELL_ITEM extends AionServerPacket {
  private int targetObjectId;
  private int sellPercentage;

  public SM_SELL_ITEM(int targetObjectId, int sellPercentage) {
    this.sellPercentage = sellPercentage;
    this.targetObjectId = targetObjectId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeD(buf, this.sellPercentage);
  }
}
