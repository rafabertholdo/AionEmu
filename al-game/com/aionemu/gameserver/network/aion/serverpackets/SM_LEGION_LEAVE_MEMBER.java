package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_LEAVE_MEMBER extends AionServerPacket {
  private String name;
  private String name1;
  private int playerObjId;
  private int msgId;

  public SM_LEGION_LEAVE_MEMBER(int msgId, int playerObjId, String name) {
    this.msgId = msgId;
    this.playerObjId = playerObjId;
    this.name = name;
  }

  public SM_LEGION_LEAVE_MEMBER(int msgId, int playerObjId, String name, String name1) {
    this.msgId = msgId;
    this.playerObjId = playerObjId;
    this.name = name;
    this.name1 = name1;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeC(buf, 0);
    writeD(buf, 0);
    writeD(buf, this.msgId);
    writeS(buf, this.name);
    writeS(buf, this.name1);
  }
}
