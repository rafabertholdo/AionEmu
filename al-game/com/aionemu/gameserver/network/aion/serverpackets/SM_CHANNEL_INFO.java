package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.WorldPosition;
import java.nio.ByteBuffer;

public class SM_CHANNEL_INFO extends AionServerPacket {
  int instanceCount = 0;
  int currentChannel = 0;

  public SM_CHANNEL_INFO(WorldPosition position) {
    this.instanceCount = position.getInstanceCount();
    this.currentChannel = position.getInstanceId() - 1;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.currentChannel);
    writeD(buf, this.instanceCount);
  }
}
