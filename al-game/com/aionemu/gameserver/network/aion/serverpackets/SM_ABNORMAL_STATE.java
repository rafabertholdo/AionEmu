package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.skillengine.model.Effect;
import java.nio.ByteBuffer;
import java.util.Collection;

public class SM_ABNORMAL_STATE extends AionServerPacket {
  private Collection<Effect> effects;
  private int abnormals;

  public SM_ABNORMAL_STATE(Collection<Effect> effects, int abnormals) {
    this.effects = effects;
    this.abnormals = abnormals;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.abnormals);
    writeH(buf, this.effects.size());

    for (Effect effect : this.effects) {

      writeD(buf, effect.getEffectorId());
      writeH(buf, effect.getSkillId());
      writeC(buf, effect.getSkillLevel());
      writeC(buf, effect.getTargetSlot());
      writeD(buf, effect.getElapsedTime());
    }
  }
}
