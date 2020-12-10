package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.PrivateStoreService;

public class CM_PRIVATE_STORE_NAME extends AionClientPacket {
  private String name;

  public CM_PRIVATE_STORE_NAME(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.name = readS();
  }

  protected void runImpl() {
    Player activePlayer = ((AionConnection) getConnection()).getActivePlayer();
    PrivateStoreService.openPrivateStore(activePlayer, this.name);
  }
}
