package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_QUEST_DELETE extends AionServerPacket {
  private int questId;

  public SM_QUEST_DELETE(int questId) {
    this.questId = questId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.questId);
    writeC(buf, 0);
  }
}
