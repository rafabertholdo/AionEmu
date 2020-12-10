package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;























public class SM_LEGION_UPDATE_NICKNAME
  extends AionServerPacket
{
  private int playerObjId;
  private String newNickname;
  
  public SM_LEGION_UPDATE_NICKNAME(int playerObjId, String newNickname) {
    this.playerObjId = playerObjId;
    this.newNickname = newNickname;
  }


  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeS(buf, this.newNickname);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_NICKNAME.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
