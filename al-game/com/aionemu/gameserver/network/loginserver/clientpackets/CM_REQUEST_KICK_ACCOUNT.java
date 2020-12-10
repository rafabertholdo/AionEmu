package com.aionemu.gameserver.network.loginserver.clientpackets;

import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.network.loginserver.LsClientPacket;






























public class CM_REQUEST_KICK_ACCOUNT
  extends LsClientPacket
{
  private int accountId;
  
  public CM_REQUEST_KICK_ACCOUNT(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.accountId = readD();
  }





  
  protected void runImpl() {
    LoginServer.getInstance().kickAccount(this.accountId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\clientpackets\CM_REQUEST_KICK_ACCOUNT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
