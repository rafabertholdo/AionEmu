package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_USE_OBJECT
  extends AionServerPacket
{
  private int playerObjId;
  private int targetObjId;
  private int time;
  private int actionType;
  
  public SM_USE_OBJECT(int playerObjId, int targetObjId, int time, int actionType) {
    this.playerObjId = playerObjId;
    this.targetObjId = targetObjId;
    this.time = time;
    this.actionType = actionType;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeD(buf, this.targetObjId);
    writeD(buf, this.time);
    writeC(buf, this.actionType);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_USE_OBJECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
