package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;




















public class SM_DELETE_ITEM
  extends AionServerPacket
{
  private int itemUniqueId;
  
  public SM_DELETE_ITEM(int itemUniqueId) {
    this.itemUniqueId = itemUniqueId;
  }



  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.itemUniqueId);
    writeC(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DELETE_ITEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
