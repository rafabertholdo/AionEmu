package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;






























public class SM_L2AUTH_LOGIN_CHECK
  extends AionServerPacket
{
  private final boolean ok;
  
  public SM_L2AUTH_LOGIN_CHECK(boolean ok) {
    this.ok = ok;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    Account account = con.getAccount();
    
    writeD(buf, this.ok ? 0 : 1);
    writeS(buf, account.getName());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_L2AUTH_LOGIN_CHECK.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
