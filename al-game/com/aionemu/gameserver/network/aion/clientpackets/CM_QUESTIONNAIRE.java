package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;





















public class CM_QUESTIONNAIRE
  extends AionClientPacket
{
  private int objectId;
  
  public CM_QUESTIONNAIRE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.objectId = readD();
    readH();
    readH();
    readH();
    readH();
  }
  
  protected void runImpl() {}
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUESTIONNAIRE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
