package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.DropService;

public class CM_START_LOOT extends AionClientPacket {
  private int targetObjectId;
  private int action;

  public CM_START_LOOT(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = readD();
    this.action = readC();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    if (this.action == 0) {

      DropService.getInstance().requestDropList(player, this.targetObjectId);
    } else if (this.action == 1) {

      DropService.getInstance().requestDropList(player, this.targetObjectId, true);
    }
  }
}
