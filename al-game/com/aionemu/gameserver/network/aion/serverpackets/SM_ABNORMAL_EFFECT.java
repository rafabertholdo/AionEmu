package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.nio.ByteBuffer;
import java.util.Collection;

public class SM_ABNORMAL_EFFECT extends AionServerPacket {
  private int effectedId;
  private int abnormals;
  private Collection<Effect> effects;

  public SM_ABNORMAL_EFFECT(int effectedId, int abnormals, Collection<Effect> effects) {
    this.effects = effects;
    this.abnormals = abnormals;
    this.effectedId = effectedId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.effectedId);
    writeC(buf, 1);
    writeD(buf, 0);
    writeD(buf, this.abnormals);

    writeH(buf, this.effects.size());

    for (Effect effect : this.effects) {

      writeH(buf, effect.getSkillId());
      writeC(buf, effect.getSkillLevel());
      writeC(buf, effect.getTargetSlot());
      writeD(buf, effect.getElapsedTime());
    }
  }
}
