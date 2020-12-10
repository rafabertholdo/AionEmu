package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;























public class SM_NAME_CHANGE
  extends AionServerPacket
{
  private int playerObjectId;
  private String oldName;
  private String newName;
  
  public SM_NAME_CHANGE(int playerObjectId, String oldName, String newName) {
    this.playerObjectId = playerObjectId;
    this.oldName = oldName;
    this.newName = newName;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
    writeD(buf, 0);
    writeD(buf, this.playerObjectId);
    writeS(buf, this.oldName);
    writeS(buf, this.newName);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_NAME_CHANGE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
