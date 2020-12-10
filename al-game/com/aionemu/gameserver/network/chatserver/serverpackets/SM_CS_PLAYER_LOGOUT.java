package com.aionemu.gameserver.network.chatserver.serverpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;
import java.nio.ByteBuffer;





















public class SM_CS_PLAYER_LOGOUT
  extends CsServerPacket
{
  private int playerId;
  
  public SM_CS_PLAYER_LOGOUT(int playerId) {
    super(2);
    this.playerId = playerId;
  }


  
  protected void writeImpl(ChatServerConnection con, ByteBuffer buf) {
    writeC(buf, getOpcode());
    writeD(buf, this.playerId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\serverpackets\SM_CS_PLAYER_LOGOUT.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
