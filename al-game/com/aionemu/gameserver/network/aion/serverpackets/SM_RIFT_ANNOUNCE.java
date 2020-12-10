package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;




























public class SM_RIFT_ANNOUNCE
  extends AionServerPacket
{
  private Race race;
  
  public SM_RIFT_ANNOUNCE(Race race) {
    this.race = race;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
    switch (this.race) {

      
      case ASMODIANS:
        writeD(buf, 1);
        writeD(buf, 0);
        break;
      case ELYOS:
        writeD(buf, 1);
        writeD(buf, 0);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_RIFT_ANNOUNCE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
