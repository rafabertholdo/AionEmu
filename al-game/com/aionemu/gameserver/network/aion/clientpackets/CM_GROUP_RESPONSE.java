package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import org.apache.log4j.Logger;





















public class CM_GROUP_RESPONSE
  extends AionClientPacket
{
  private static Logger log = Logger.getLogger(CM_GROUP_RESPONSE.class);
  
  private int unk1;
  private int unk2;
  
  public CM_GROUP_RESPONSE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.unk1 = readD();
    this.unk2 = readC();
  }





  
  protected void runImpl() {
    log.debug(String.valueOf(this.unk1) + "," + String.valueOf(this.unk2));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_GROUP_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
