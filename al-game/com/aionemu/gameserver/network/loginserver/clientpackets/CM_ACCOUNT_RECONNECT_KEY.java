package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;

public class CM_ACCOUNT_RECONNECT_KEY extends LsClientPacket {
  private int accountId;
  private int reconnectKey;

  public CM_ACCOUNT_RECONNECT_KEY(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.accountId = readD();
    this.reconnectKey = readD();
  }

  protected void runImpl() {
    LoginServer.getInstance().authReconnectionResponse(this.accountId, this.reconnectKey);
  }
}
