package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DELETE_WAREHOUSE_ITEM extends AionServerPacket {
  private int warehouseType;
  private int itemObjId;

  public SM_DELETE_WAREHOUSE_ITEM(int warehouseType, int itemObjId) {
    this.warehouseType = warehouseType;
    this.itemObjId = itemObjId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.warehouseType);
    writeD(buf, this.itemObjId);
    writeC(buf, 14);
  }
}
