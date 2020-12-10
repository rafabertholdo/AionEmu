package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;

public class CM_BROKER_REGISTERED extends AionClientPacket {
  private int npcId;

  public CM_BROKER_REGISTERED(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.npcId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    BrokerService.getInstance().showRegisteredItems(player);
  }
}
