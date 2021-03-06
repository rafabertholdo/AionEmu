package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_RESTORE_CHARACTER extends AionServerPacket {
  private final int chaOid;
  private final boolean success;

  public SM_RESTORE_CHARACTER(int chaOid, boolean success) {
    this.chaOid = chaOid;
    this.success = success;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.success ? 0 : 16);
    writeD(buf, this.chaOid);
  }
}
