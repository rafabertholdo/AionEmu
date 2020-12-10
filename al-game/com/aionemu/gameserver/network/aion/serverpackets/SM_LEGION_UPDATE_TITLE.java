package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_LEGION_UPDATE_TITLE
  extends AionServerPacket
{
  private int objectId;
  private int legionId;
  private String legionName;
  private int rank;
  
  public SM_LEGION_UPDATE_TITLE(int objectId, int legionId, String legionName, int rank) {
    this.objectId = objectId;
    this.legionId = legionId;
    this.legionName = legionName;
    this.rank = rank;
  }


  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.objectId);
    writeD(buf, this.legionId);
    writeS(buf, this.legionName);
    writeC(buf, this.rank);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_TITLE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
