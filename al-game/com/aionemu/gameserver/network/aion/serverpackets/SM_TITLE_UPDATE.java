package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;


























public class SM_TITLE_UPDATE
  extends AionServerPacket
{
  private int objectId;
  private int titleId;
  
  public SM_TITLE_UPDATE(Player player, int titleId) {
    this.objectId = player.getObjectId();
    this.titleId = titleId;
  }

  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.objectId);
    writeD(buf, this.titleId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_TITLE_UPDATE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
