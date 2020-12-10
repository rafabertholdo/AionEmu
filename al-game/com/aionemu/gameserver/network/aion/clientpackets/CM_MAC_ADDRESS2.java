package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;

public class CM_MAC_ADDRESS2 extends AionClientPacket {
  public CM_MAC_ADDRESS2(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    int objectId = readD();

    byte[] macAddress = readB(6);
  }

  protected void runImpl() {
  }
}
