package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.loginserver.LoginServer;













































public class CM_L2AUTH_LOGIN_CHECK
  extends AionClientPacket
{
  private int playOk2;
  private int playOk1;
  private int accountId;
  private int loginOk;
  
  public CM_L2AUTH_LOGIN_CHECK(int opcode) {
    super(opcode);
  }





  
  protected void readImpl() {
    this.playOk2 = readD();
    this.playOk1 = readD();
    this.accountId = readD();
    this.loginOk = readD();
  }





  
  protected void runImpl() {
    LoginServer.getInstance().requestAuthenticationOfClient(this.accountId, (AionConnection)getConnection(), this.loginOk, this.playOk1, this.playOk2);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\clientpackets\CM_L2AUTH_LOGIN_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
