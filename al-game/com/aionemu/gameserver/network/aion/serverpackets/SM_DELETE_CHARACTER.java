package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DELETE_CHARACTER extends AionServerPacket {
  private int playerObjId;
  private int deletionTime;

  public SM_DELETE_CHARACTER(int playerObjId, int deletionTime) {
    this.playerObjId = playerObjId;
    this.deletionTime = deletionTime;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.playerObjId != 0) {

      writeD(buf, 0);
      writeD(buf, this.playerObjId);
      writeD(buf, this.deletionTime);
    } else {

      writeD(buf, 16);
      writeD(buf, 0);
      writeD(buf, 0);
    }
  }
}
