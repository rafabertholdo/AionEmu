package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_STATUPDATE_HP extends AionServerPacket {
  private int currentHp;
  private int maxHp;

  public SM_STATUPDATE_HP(int currentHp, int maxHp) {
    this.currentHp = currentHp;
    this.maxHp = maxHp;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.currentHp);
    writeD(buf, this.maxHp);
  }
}
