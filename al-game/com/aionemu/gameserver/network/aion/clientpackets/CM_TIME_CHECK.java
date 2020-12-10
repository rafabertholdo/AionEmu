package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TIME_CHECK;





























public class CM_TIME_CHECK
  extends AionClientPacket
{
  private int nanoTime;
  
  public CM_TIME_CHECK(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.nanoTime = readD();
  }





  
  protected void runImpl() {
    AionConnection client = (AionConnection)getConnection();
    int timeNow = (int)(System.nanoTime() / 1000000L);
    
    int diff = timeNow - this.nanoTime;
    client.sendPacket((AionServerPacket)new SM_TIME_CHECK(this.nanoTime));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_TIME_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
