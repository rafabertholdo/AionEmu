package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;





















public class SM_PLAYER_MOVE
  extends AionServerPacket
{
  private float x;
  private float y;
  private float z;
  private byte heading;
  
  public SM_PLAYER_MOVE(float x, float y, float z, byte heading) {
    this.x = x;
    this.y = y;
    this.z = z;
    this.heading = heading;
  }

  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeF(buf, this.x);
    writeF(buf, this.y);
    writeF(buf, this.z);
    writeC(buf, this.heading);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_MOVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
