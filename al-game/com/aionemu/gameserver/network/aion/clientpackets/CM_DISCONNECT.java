package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;

public class CM_DISCONNECT extends AionClientPacket {
  boolean unk;

  public CM_DISCONNECT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.unk = (readC() == 0);
  }

  protected void runImpl() {
    if (this.unk) {

      AionConnection client = (AionConnection) getConnection();

      client.close(false);
    }
  }
}
