package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.loginserver.LoginServer;

public class CM_RECONNECT_AUTH extends AionClientPacket {
  public CM_RECONNECT_AUTH(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    AionConnection client = (AionConnection) getConnection();

    LoginServer.getInstance().requestAuthReconnection(client);
  }
}
