package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;



















public class SM_PONG
  extends AionServerPacket
{
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeC(buf, 0);
    writeC(buf, 0);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PONG.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
