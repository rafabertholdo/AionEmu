package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;

public class CM_QUESTIONNAIRE extends AionClientPacket {
  private int objectId;

  public CM_QUESTIONNAIRE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.objectId = readD();
    readH();
    readH();
    readH();
    readH();
  }

  protected void runImpl() {
  }
}
