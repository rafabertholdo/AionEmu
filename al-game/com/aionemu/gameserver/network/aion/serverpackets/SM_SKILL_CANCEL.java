package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_SKILL_CANCEL extends AionServerPacket {
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
