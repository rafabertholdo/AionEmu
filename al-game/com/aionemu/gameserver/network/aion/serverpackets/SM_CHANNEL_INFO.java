package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.world.WorldPosition;
import java.nio.ByteBuffer;






















public class SM_CHANNEL_INFO
  extends AionServerPacket
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CHANNEL_INFO.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
