package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SUMMON_OWNER_REMOVE extends AionServerPacket {
  private int summonObjId;

  public SM_SUMMON_OWNER_REMOVE(int summonObjId) {
    this.summonObjId = summonObjId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.summonObjId);
  }
}
