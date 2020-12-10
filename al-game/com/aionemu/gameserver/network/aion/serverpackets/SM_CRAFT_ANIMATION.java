package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;




























public class SM_CRAFT_ANIMATION
  extends AionServerPacket
{
  private int senderObjectId;
  private int targetObjectId;
  private int skillId;
  private int action;
  
  public SM_CRAFT_ANIMATION(int senderObjectId, int targetObjectId, int skillId, int action) {
    this.senderObjectId = senderObjectId;
    this.targetObjectId = targetObjectId;
    this.skillId = skillId;
    this.action = action;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.senderObjectId);
    writeD(buf, this.targetObjectId);
    writeH(buf, this.skillId);
    writeC(buf, this.action);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_CRAFT_ANIMATION.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
