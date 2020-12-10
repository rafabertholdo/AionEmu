package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Prices;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_PRICES extends AionServerPacket {
  private Prices prices;

  public SM_PRICES(Prices prices) {
    this.prices = prices;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, this.prices.getGlobalPrices());
    writeC(buf, this.prices.getGlobalPricesModifier());
    writeC(buf, this.prices.getTaxes());
  }
}
