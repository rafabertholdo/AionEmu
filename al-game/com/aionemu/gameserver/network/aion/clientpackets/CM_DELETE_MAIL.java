package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.MailService;

public class CM_DELETE_MAIL extends AionClientPacket {
  int mailObjId;

  public CM_DELETE_MAIL(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.mailObjId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    MailService.getInstance().deleteMail(player, this.mailObjId);
  }
}
