package com.aionemu.gameserver.network.chatserver.clientpackets;

import com.aionemu.gameserver.network.chatserver.CsClientPacket;
import com.aionemu.gameserver.services.ChatService;
import org.apache.log4j.Logger;




















public class CM_CS_PLAYER_AUTH_RESPONSE
  extends CsClientPacket
{
  protected static final Logger log = Logger.getLogger(CM_CS_PLAYER_AUTH_RESPONSE.class);



  
  private int playerId;



  
  private byte[] token;



  
  public CM_CS_PLAYER_AUTH_RESPONSE(int opcode) {
    super(opcode);
  }


  
  protected void readImpl() {
    this.playerId = readD();
    int tokenLenght = readC();
    this.token = readB(tokenLenght);
  }


  
  protected void runImpl() {
    ChatService.playerAuthed(this.playerId, this.token);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\clientpackets\CM_CS_PLAYER_AUTH_RESPONSE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
