package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_PRIVATE_STORE_NAME extends AionServerPacket {
  private int playerObjId;
  private String name;

  public SM_PRIVATE_STORE_NAME(int playerObjId, String name) {
    this.playerObjId = playerObjId;
    this.name = name;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeS(buf, this.name);
  }
}
