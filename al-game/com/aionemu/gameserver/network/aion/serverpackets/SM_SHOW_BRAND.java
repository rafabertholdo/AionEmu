package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SHOW_BRAND extends AionServerPacket {
  private int brandId;
  private int targetObjectId;

  public SM_SHOW_BRAND(int brandId, int targetObjectId) {
    this.brandId = brandId;
    this.targetObjectId = targetObjectId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, 1);
    writeD(buf, this.brandId);
    writeD(buf, this.targetObjectId);
  }
}
