package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.trade.TradePSItem;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.services.PrivateStoreService;

public class CM_PRIVATE_STORE extends AionClientPacket {
  private Player activePlayer;
  private TradePSItem[] tradePSItems;
  private int itemCount;

  public CM_PRIVATE_STORE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.activePlayer = ((AionConnection) getConnection()).getActivePlayer();

    this.itemCount = readH();
    this.tradePSItems = new TradePSItem[this.itemCount];
    for (int i = 0; i < this.itemCount; i++) {
      this.tradePSItems[i] = new TradePSItem(readD(), readD(), readH(), readD());
    }
  }

  protected void runImpl() {
    if (this.itemCount > 0) {

      PrivateStoreService.addItem(this.activePlayer, this.tradePSItems);
    } else {

      PrivateStoreService.closePrivateStore(this.activePlayer);
    }
  }
}
