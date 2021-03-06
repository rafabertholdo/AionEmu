package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.BrokerService;

public class CM_BROKER_LIST extends AionClientPacket {
  private int brokerId;
  private int sortType;
  private int page;
  private int listMask;

  public CM_BROKER_LIST(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.brokerId = readD();
    this.sortType = readC();
    this.page = readH();
    this.listMask = readH();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();

    BrokerService.getInstance().showRequestedItems(player, this.listMask, this.sortType, this.page);
  }
}
