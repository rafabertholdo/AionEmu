package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






















public class SM_PLAYER_ID
  extends AionServerPacket
{
  private AionObject playerAionObject;
  
  public SM_PLAYER_ID(AionObject playerAionObject) {
    this.playerAionObject = playerAionObject;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, 2);
    writeD(buf, 0);
    writeH(buf, 1);
    writeD(buf, this.playerAionObject.getObjectId());
    writeH(buf, 0);
    writeS(buf, this.playerAionObject.getName());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_ID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
