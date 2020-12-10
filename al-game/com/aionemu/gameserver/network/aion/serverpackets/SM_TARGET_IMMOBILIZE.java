package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TARGET_IMMOBILIZE extends AionServerPacket {
  private Creature creature;

  public SM_TARGET_IMMOBILIZE(Creature creature) {
    this.creature = creature;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.creature.getObjectId());
    writeF(buf, this.creature.getX());
    writeF(buf, this.creature.getY());
    writeF(buf, this.creature.getZ());
    writeC(buf, this.creature.getHeading());
  }
}
