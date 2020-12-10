package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;





















public class SM_FORCED_MOVE
  extends AionServerPacket
{
  private Creature creature;
  private Creature target;
  
  public SM_FORCED_MOVE(Creature creature, Creature target) {
    this.creature = creature;
    this.target = target;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.creature.getObjectId());
    writeD(buf, this.target.getObjectId());
    writeC(buf, 16);
    writeF(buf, this.target.getX());
    writeF(buf, this.target.getY());
    writeF(buf, this.target.getZ() + 0.25F);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_FORCED_MOVE.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
