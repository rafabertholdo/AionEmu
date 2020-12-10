package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
























public class SM_LOOT_STATUS
  extends AionServerPacket
{
  private int targetObjectId;
  private int state;
  
  public SM_LOOT_STATUS(int targetObjectId, int state) {
    this.targetObjectId = targetObjectId;
    this.state = state;
  }






  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjectId);
    writeC(buf, this.state);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LOOT_STATUS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
