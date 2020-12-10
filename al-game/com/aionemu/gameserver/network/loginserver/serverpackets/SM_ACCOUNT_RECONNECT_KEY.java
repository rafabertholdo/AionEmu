package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;


































public class SM_ACCOUNT_RECONNECT_KEY
  extends LsServerPacket
{
  private final int accountId;
  
  public SM_ACCOUNT_RECONNECT_KEY(int accountId) {
    super(2);
    this.accountId = accountId;
  }





  
  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.accountId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_ACCOUNT_RECONNECT_KEY.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
