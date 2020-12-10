package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_RESURRECT extends AionServerPacket {
  private String name;
  private int skillId;

  public SM_RESURRECT(Creature creature) {
    this(creature, 0);
  }

  public SM_RESURRECT(Creature creature, int skillId) {
    this.name = creature.getName();
    this.skillId = skillId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeS(buf, this.name);
    writeH(buf, this.skillId);
    writeD(buf, 0);
  }
}
