package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_DELETE extends AionServerPacket {
  private final int objectId;
  private final int time;

  public SM_DELETE(AionObject object, int time) {
    this.objectId = object.getObjectId();
    this.time = time;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    int action = 0;
    if (action != 1) {
      writeD(buf, this.objectId);
      writeC(buf, this.time);
    }
  }
}
