package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.account.PlayerAccountData;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE_CHARACTER;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.services.PlayerService;

public class CM_DELETE_CHARACTER extends AionClientPacket {
  private int playOk2;
  private int chaOid;

  public CM_DELETE_CHARACTER(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.playOk2 = readD();
    this.chaOid = readD();
  }

  protected void runImpl() {
    AionConnection client = (AionConnection) getConnection();
    PlayerAccountData playerAccData = client.getAccount().getPlayerAccountData(this.chaOid);
    if (playerAccData != null && !playerAccData.isLegionMember()) {

      PlayerService.deletePlayer(playerAccData);
      client.sendPacket(
          (AionServerPacket) new SM_DELETE_CHARACTER(this.chaOid, playerAccData.getDeletionTimeInSeconds()));
    } else {

      client.sendPacket((AionServerPacket) SM_SYSTEM_MESSAGE.STR_DELETE_CHARACTER_IN_LEGION());
    }
  }
}
