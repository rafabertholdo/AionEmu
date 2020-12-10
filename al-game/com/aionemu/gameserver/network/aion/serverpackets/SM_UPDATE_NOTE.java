package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_UPDATE_NOTE extends AionServerPacket {
  private int targetObjId;
  private String note;

  public SM_UPDATE_NOTE(int targetObjId, String note) {
    this.targetObjId = targetObjId;
    this.note = note;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjId);
    writeS(buf, this.note);
  }
}
