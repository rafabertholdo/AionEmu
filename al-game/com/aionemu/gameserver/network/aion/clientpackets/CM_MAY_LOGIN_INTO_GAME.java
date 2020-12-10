package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MAY_LOGIN_INTO_GAME;

public class CM_MAY_LOGIN_INTO_GAME extends AionClientPacket {
  public CM_MAY_LOGIN_INTO_GAME(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    AionConnection client = (AionConnection) getConnection();

    client.sendPacket((AionServerPacket) new SM_MAY_LOGIN_INTO_GAME());
  }
}
