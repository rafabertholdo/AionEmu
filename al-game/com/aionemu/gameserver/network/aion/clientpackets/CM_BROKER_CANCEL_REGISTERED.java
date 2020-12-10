package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;

public class CM_BROKER_CANCEL_REGISTERED extends AionClientPacket {
  private int npcId;
  private int brokerItemId;

  public CM_BROKER_CANCEL_REGISTERED(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.npcId = readD();
    this.brokerItemId = readD();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    BrokerService.getInstance().cancelRegisteredItem(player, this.brokerItemId);
  }
}
