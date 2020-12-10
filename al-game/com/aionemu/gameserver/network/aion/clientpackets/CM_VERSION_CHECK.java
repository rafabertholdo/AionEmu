package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_VERSION_CHECK;

public class CM_VERSION_CHECK extends AionClientPacket {
  private int unk1;
  private int unk2;

  public CM_VERSION_CHECK(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.unk1 = readD();
    this.unk2 = readD();
  }

  protected void runImpl() {
    sendPacket((AionServerPacket) new SM_VERSION_CHECK());
  }
}
