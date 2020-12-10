package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.DropService;

public class CM_LOOT_ITEM extends AionClientPacket {
  private int targetObjectId;
  private int index;

  public CM_LOOT_ITEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.targetObjectId = readD();
    this.index = readC();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    DropService.getInstance().requestDropItem(player, this.targetObjectId, this.index);
  }
}
