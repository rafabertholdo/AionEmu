package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PING_RESPONSE;

public class CM_PING_REQUEST extends AionClientPacket {
  public CM_PING_REQUEST(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    sendPacket((AionServerPacket) new SM_PING_RESPONSE());
  }
}
