package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHARACTER_LIST;

public class CM_CHARACTER_LIST extends AionClientPacket {
  private int playOk2;

  public CM_CHARACTER_LIST(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.playOk2 = readD();
  }

  protected void runImpl() {
    sendPacket((AionServerPacket) new SM_CHARACTER_LIST(this.playOk2));
  }
}
