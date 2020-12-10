package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;

public class SM_ACCOUNT_RECONNECT_KEY extends LsServerPacket {
  private final int accountId;

  public SM_ACCOUNT_RECONNECT_KEY(int accountId) {
    super(2);
    this.accountId = accountId;
  }

  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.accountId);
  }
}
