package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_FRIEND_NOTIFY extends AionServerPacket {
  public static final int LOGIN = 0;
  public static final int LOGOUT = 1;
  public static final int DELETED = 2;
  private final int code;
  private final String name;

  public SM_FRIEND_NOTIFY(int code, String name) {
    this.code = code;
    this.name = name;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeS(buf, this.name);
    writeC(buf, this.code);
  }
}
