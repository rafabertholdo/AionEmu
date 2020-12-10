package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NICKNAME_CHECK_RESPONSE;
import com.aionemu.gameserver.services.PlayerService;

public class CM_CHECK_NICKNAME extends AionClientPacket {
  private String nick;

  public CM_CHECK_NICKNAME(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.nick = readS();
  }

  protected void runImpl() {
    AionConnection client = (AionConnection) getConnection();

    if (!PlayerService.isValidName(this.nick)) {

      client.sendPacket((AionServerPacket) new SM_NICKNAME_CHECK_RESPONSE(5));
    } else if (!PlayerService.isFreeName(this.nick)) {

      client.sendPacket((AionServerPacket) new SM_NICKNAME_CHECK_RESPONSE(10));
    } else {

      client.sendPacket((AionServerPacket) new SM_NICKNAME_CHECK_RESPONSE(0));
    }
  }
}
