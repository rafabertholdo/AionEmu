package com.aionemu.gameserver.network.loginserver.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.loginserver.LoginServerConnection;
import com.aionemu.gameserver.network.loginserver.LsServerPacket;
import java.nio.ByteBuffer;
import java.util.Map;































public class SM_ACCOUNT_LIST
  extends LsServerPacket
{
  private final Map<Integer, AionConnection> accounts;
  
  public SM_ACCOUNT_LIST(Map<Integer, AionConnection> accounts) {
    super(4);
    this.accounts = accounts;
  }


  
  protected void writeImpl(LoginServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.accounts.size());
    for (AionConnection ac : this.accounts.values())
    {
      writeS(buf, ac.getAccount().getName());
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\serverpackets\SM_ACCOUNT_LIST.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
