package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;





















public class SM_SKILL_CANCEL
  extends AionServerPacket
{
  private Creature creature;
  private int skillId;
  
  public SM_SKILL_CANCEL(Creature creature, int skillId) {
    this.creature = creature;
    this.skillId = skillId;
  }





  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.creature.getObjectId());
    writeH(buf, this.skillId);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_SKILL_CANCEL.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
