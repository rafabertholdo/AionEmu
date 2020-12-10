package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;





















public class SM_CUSTOM_SETTINGS
  extends AionServerPacket
{
  private Player player;
  
  public SM_CUSTOM_SETTINGS(Player player) {
    this.player = player;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.player.getObjectId());
    writeC(buf, 1);
    writeH(buf, this.player.getPlayerSettings().getDisplay());
    writeH(buf, this.player.getPlayerSettings().getDeny());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CUSTOM_SETTINGS.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
