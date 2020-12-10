package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.Date;



























public class SM_TIME_CHECK
  extends AionServerPacket
{
  private int nanoTime;
  private int time;
  private Timestamp dateTime;
  
  public SM_TIME_CHECK(int nanoTime) {
    this.dateTime = new Timestamp((new Date()).getTime());
    this.nanoTime = nanoTime;
    this.time = (int)this.dateTime.getTime();
  }






  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.time);
    writeD(buf, this.nanoTime);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TIME_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
