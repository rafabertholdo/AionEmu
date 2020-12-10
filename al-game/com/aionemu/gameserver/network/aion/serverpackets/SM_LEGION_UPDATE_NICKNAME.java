package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_LEGION_UPDATE_NICKNAME extends AionServerPacket {
  private int playerObjId;
  private String newNickname;

  public SM_LEGION_UPDATE_NICKNAME(int playerObjId, String newNickname) {
    this.playerObjId = playerObjId;
    this.newNickname = newNickname;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeS(buf, this.newNickname);
  }
}
