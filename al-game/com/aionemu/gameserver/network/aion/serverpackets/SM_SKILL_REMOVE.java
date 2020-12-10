package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SKILL_REMOVE extends AionServerPacket {
  private int skillId;

  public SM_SKILL_REMOVE(int skillId) {
    this.skillId = skillId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.skillId);
    writeC(buf, 1);
    writeC(buf, 1);
  }
}
