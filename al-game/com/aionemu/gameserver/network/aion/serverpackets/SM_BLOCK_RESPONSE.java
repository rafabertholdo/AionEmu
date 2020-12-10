package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_BLOCK_RESPONSE extends AionServerPacket {
  public static final int BLOCK_SUCCESSFUL = 0;
  public static final int UNBLOCK_SUCCESSFUL = 1;
  public static final int TARGET_NOT_FOUND = 2;
  public static final int LIST_FULL = 3;
  public static final int CANT_BLOCK_SELF = 4;
  private int code;
  private String playerName;

  public SM_BLOCK_RESPONSE(int code, String playerName) {
    this.code = code;
    this.playerName = playerName;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeS(buf, this.playerName);
    writeD(buf, this.code);
  }
}
