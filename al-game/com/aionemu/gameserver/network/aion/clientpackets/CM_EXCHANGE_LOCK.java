package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.ExchangeService;

public class CM_EXCHANGE_LOCK extends AionClientPacket {
  public CM_EXCHANGE_LOCK(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    ExchangeService.getInstance().lockExchange(activePlayer);
  }
}
