package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DELETE_ITEM extends AionServerPacket {
  private int itemUniqueId;

  public SM_DELETE_ITEM(int itemUniqueId) {
    this.itemUniqueId = itemUniqueId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.itemUniqueId);
    writeC(buf, 0);
  }
}
