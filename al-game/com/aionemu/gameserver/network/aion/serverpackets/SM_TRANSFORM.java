package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TRANSFORM extends AionServerPacket {
  private Creature creature;
  private int state;

  public SM_TRANSFORM(Creature creature) {
    this.creature = creature;
    this.state = creature.getState();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.creature.getObjectId());
    writeD(buf, this.creature.getTransformedModelId());
    writeH(buf, this.state);
    writeF(buf, 0.55F);
    writeF(buf, 1.5F);
    writeC(buf, 0);
  }
}
