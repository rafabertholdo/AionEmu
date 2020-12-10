package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;





















public class SM_ITEM_USAGE_ANIMATION
  extends AionServerPacket
{
  private int playerObjId;
  private int itemObjId;
  private int itemId;
  private int time;
  private int end;
  private int unk;
  
  public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId) {
    this.playerObjId = playerObjId;
    this.itemObjId = itemObjId;
    this.itemId = itemId;
    this.time = 0;
    this.end = 1;
    this.unk = 1;
  }

  
  public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end, int unk) {
    this.playerObjId = playerObjId;
    this.itemObjId = itemObjId;
    this.itemId = itemId;
    this.time = time;
    this.end = end;
    this.unk = unk;
  }

  
  public SM_ITEM_USAGE_ANIMATION(int playerObjId, int itemObjId, int itemId, int time, int end) {
    this.playerObjId = playerObjId;
    this.itemObjId = itemObjId;
    this.itemId = itemId;
    this.time = time;
    this.end = end;
  }


  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.playerObjId);
    writeD(buf, this.playerObjId);
    
    writeD(buf, this.itemObjId);
    writeD(buf, this.itemId);
    
    writeD(buf, this.time);
    writeC(buf, this.end);
    writeC(buf, 1);
    writeC(buf, 0);
    writeD(buf, this.unk);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_ITEM_USAGE_ANIMATION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
