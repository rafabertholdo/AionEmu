package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;



























public class SM_DELETE_CHARACTER
  extends AionServerPacket
{
  private int playerObjId;
  private int deletionTime;
  
  public SM_DELETE_CHARACTER(int playerObjId, int deletionTime) {
    this.playerObjId = playerObjId;
    this.deletionTime = deletionTime;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    if (this.playerObjId != 0) {
      
      writeD(buf, 0);
      writeD(buf, this.playerObjId);
      writeD(buf, this.deletionTime);
    }
    else {
      
      writeD(buf, 16);
      writeD(buf, 0);
      writeD(buf, 0);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_DELETE_CHARACTER.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
