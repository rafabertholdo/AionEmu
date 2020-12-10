package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;

public class CM_MAC_ADDRESS extends AionClientPacket {
  private String macAddress;

  public CM_MAC_ADDRESS(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    readB(22);
    this.macAddress = readS();
  }

  protected void runImpl() {
  }
}
