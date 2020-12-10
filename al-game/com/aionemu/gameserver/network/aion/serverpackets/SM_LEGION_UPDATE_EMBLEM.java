package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;




























public class SM_LEGION_UPDATE_EMBLEM
  extends AionServerPacket
{
  private int legionId;
  private int emblemId;
  private int color_r;
  private int color_g;
  private int color_b;
  
  public SM_LEGION_UPDATE_EMBLEM(int legionId, int emblemId, int color_r, int color_g, int color_b) {
    this.legionId = legionId;
    this.emblemId = emblemId;
    this.color_r = color_r;
    this.color_g = color_g;
    this.color_b = color_b;
  }


  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.legionId);
    writeH(buf, this.emblemId);
    writeC(buf, 255);
    writeC(buf, this.color_r);
    writeC(buf, this.color_g);
    writeC(buf, this.color_b);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_LEGION_UPDATE_EMBLEM.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
