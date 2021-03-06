package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.LegionService;

public class CM_LEGION_UPLOAD_EMBLEM extends AionClientPacket {
  private int size;
  private byte[] data;

  public CM_LEGION_UPLOAD_EMBLEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.size = readD();
    this.data = new byte[this.size];
    this.data = readB(this.size);
  }

  protected void runImpl() {
    LegionService.getInstance().uploadEmblemData(((AionConnection) getConnection()).getActivePlayer(), this.size,
        this.data);
  }
}
