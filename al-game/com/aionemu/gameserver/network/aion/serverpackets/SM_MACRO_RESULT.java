package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_MACRO_RESULT extends AionServerPacket {
  public static SM_MACRO_RESULT SM_MACRO_CREATED = new SM_MACRO_RESULT(0);
  public static SM_MACRO_RESULT SM_MACRO_DELETED = new SM_MACRO_RESULT(1);

  private int code;

  private SM_MACRO_RESULT(int code) {
    this.code = code;
  }

  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.code);
  }
}
