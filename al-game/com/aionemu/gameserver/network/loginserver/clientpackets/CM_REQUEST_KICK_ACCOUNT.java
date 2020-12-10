package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;

public class CM_REQUEST_KICK_ACCOUNT extends LsClientPacket {
  private int accountId;

  public CM_REQUEST_KICK_ACCOUNT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.accountId = readD();
  }

  protected void runImpl() {
    LoginServer.getInstance().kickAccount(this.accountId);
  }
}
