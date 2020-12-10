package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import org.apache.log4j.Logger;

public class CM_GROUP_RESPONSE extends AionClientPacket {
  private static Logger log = Logger.getLogger(CM_GROUP_RESPONSE.class);

  private int unk1;
  private int unk2;

  public CM_GROUP_RESPONSE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.unk1 = readD();
    this.unk2 = readC();
  }

  protected void runImpl() {
    log.debug(String.valueOf(this.unk1) + "," + String.valueOf(this.unk2));
  }
}
