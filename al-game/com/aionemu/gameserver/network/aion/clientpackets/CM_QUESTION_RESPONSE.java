package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;






















public class CM_QUESTION_RESPONSE
  extends AionClientPacket
{
  private int questionid;
  private int response;
  private int senderid;
  
  public CM_QUESTION_RESPONSE(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.questionid = readD();
    
    this.response = readC();
    readC();
    readH();
    this.senderid = readD();
    readD();
    readH();
  }





  
  protected void runImpl() {
    Player player = ((AionConnection)getConnection()).getActivePlayer();
    player.getResponseRequester().respond(this.questionid, this.response);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_QUESTION_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
