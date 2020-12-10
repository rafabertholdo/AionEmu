package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.BrokerItem;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_BROKER_REGISTRATION_SERVICE extends AionServerPacket {
  private BrokerItem itemForRegistration;
  private int message;

  public SM_BROKER_REGISTRATION_SERVICE(BrokerItem item) {
    this.message = 0;
    this.itemForRegistration = item;
  }

  public SM_BROKER_REGISTRATION_SERVICE(int message) {
    this.message = message;
    this.itemForRegistration = null;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.message);
    if (this.message == 0) {

      writeD(buf, this.itemForRegistration.getItemUniqueId());
      writeD(buf, this.itemForRegistration.getItemId());
      writeQ(buf, this.itemForRegistration.getPrice());
      writeQ(buf, this.itemForRegistration.getItem().getItemCount());
      writeQ(buf, this.itemForRegistration.getItem().getItemCount());
      writeH(buf, 8);
      writeC(buf, 0);
      writeD(buf, this.itemForRegistration.getItemId());
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeD(buf, 0);
      writeH(buf, 0);
    }
  }
}
