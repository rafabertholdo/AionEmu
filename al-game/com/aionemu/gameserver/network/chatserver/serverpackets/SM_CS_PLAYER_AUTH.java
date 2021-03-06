package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;
import java.nio.ByteBuffer;

public class SM_CS_PLAYER_AUTH extends CsServerPacket {
  private int playerId;
  private String playerLogin;

  public SM_CS_PLAYER_AUTH(int playerId, String playerLogin) {
    super(1);
    this.playerId = playerId;
    this.playerLogin = playerLogin;
  }

  protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.playerId);
    writeS(buf, this.playerLogin);
  }
}
