package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;


















public class SM_SHOW_NPC_ON_MAP
  extends AionServerPacket
{
  private int npcid;
  private int worldid;
  private float x;
  private float y;
  private float z;
  
  public SM_SHOW_NPC_ON_MAP(int npcid, int worldid, float x, float y, float z) {
    this.npcid = npcid;
    this.worldid = worldid;
    this.x = x;
    this.y = y;
    this.z = z;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.npcid);
    writeD(buf, this.worldid);
    writeD(buf, this.worldid);
    writeF(buf, this.x);
    writeF(buf, this.y);
    writeF(buf, this.z);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SHOW_NPC_ON_MAP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
