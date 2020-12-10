package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;

public class SM_ACCOUNT_AUTH extends LsServerPacket {
  private final int accountId;
  private final int loginOk;
  private final int playOk1;
  private final int playOk2;

  public SM_ACCOUNT_AUTH(int accountId, int loginOk, int playOk1, int playOk2) {
    super(1);
    this.accountId = accountId;
    this.loginOk = loginOk;
    this.playOk1 = playOk1;
    this.playOk2 = playOk2;
  }

  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.accountId);
    writeD(buf, this.loginOk);
    writeD(buf, this.playOk1);
    writeD(buf, this.playOk2);
  }
}
