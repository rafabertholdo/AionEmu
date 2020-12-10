package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;




























public class CM_DISCONNECT
  extends AionClientPacket
{
  boolean unk;
  
  public CM_DISCONNECT(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.unk = (readC() == 0);
  }






  
  protected void runImpl() {
    if (this.unk) {
      
      AionConnection client = (AionConnection)getConnection();


      
      client.close(false);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_DISCONNECT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
