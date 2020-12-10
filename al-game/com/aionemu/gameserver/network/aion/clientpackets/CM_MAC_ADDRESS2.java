package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;











public class CM_MAC_ADDRESS2
  extends AionClientPacket
{
  public CM_MAC_ADDRESS2(int opcode) {
    super(opcode);
  }






  
  protected void readImpl() {
    int objectId = readD();
    
    byte[] macAddress = readB(6);
  }
  
  protected void runImpl() {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_MAC_ADDRESS2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
