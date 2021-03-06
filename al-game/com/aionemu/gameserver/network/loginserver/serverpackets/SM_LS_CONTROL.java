package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;

public class SM_LS_CONTROL extends LsServerPacket {
  private final String accountName;
  private final String adminName;
  private final String playerName;
  private final int param;
  private final int type;

  public SM_LS_CONTROL(String accountName, String playerName, String adminName, int param, int type) {
    super(5);
    this.accountName = accountName;
    this.param = param;
    this.playerName = playerName;
    this.adminName = adminName;
    this.type = type;
  }

  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeC(buf, this.type);
    writeS(buf, this.adminName);
    writeS(buf, this.accountName);
    writeS(buf, this.playerName);
    writeC(buf, this.param);
  }
}
