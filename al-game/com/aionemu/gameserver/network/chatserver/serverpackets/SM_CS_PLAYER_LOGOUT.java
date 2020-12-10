package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;
import java.nio.ByteBuffer;

public class SM_CS_PLAYER_LOGOUT extends CsServerPacket {
  private int playerId;

  public SM_CS_PLAYER_LOGOUT(int playerId) {
    super(2);
    this.playerId = playerId;
  }

  protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.playerId);
  }
}
