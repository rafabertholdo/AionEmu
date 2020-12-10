package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;

public class CM_BUY_BROKER_ITEM extends AionClientPacket {
  private int brokerId;
  private int itemUniqueId;
  private int itemCount;

  public CM_BUY_BROKER_ITEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.brokerId = readD();
    this.itemUniqueId = readD();
    this.itemCount = readH();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    BrokerService.getInstance().buyBrokerItem(player, this.itemUniqueId);
  }
}
