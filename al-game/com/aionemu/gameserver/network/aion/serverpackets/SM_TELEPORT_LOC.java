package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TELEPORT_LOC extends AionServerPacket {
  private int mapId;
  private float x;
  private float y;
  private float z;

  public SM_TELEPORT_LOC(int mapId, float x, float y, float z) {
    this.mapId = mapId;
    this.x = x;
    this.y = y;
    this.z = z;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 3);
    writeC(buf, 144);
    writeC(buf, 158);
    writeD(buf, this.mapId);
    writeF(buf, this.x);
    writeF(buf, this.y);
    writeF(buf, this.z);
    writeC(buf, 0);
  }
}
