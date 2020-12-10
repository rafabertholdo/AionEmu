package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_ATTACK_STATUS extends AionServerPacket {
  private Creature creature;
  private TYPE type;
  private int skillId;
  private int value;

  public enum TYPE {
    NATURAL_HP(3), REGULAR(5), DAMAGE(7), HP(7), MP(21), NATURAL_MP(22), FP_RINGS(23), FP(25), NATURAL_FP(26);

    private int value;

    TYPE(int value) {
      this.value = value;
    }

    public int getValue() {
      return this.value;
    }
  }

  public SM_ATTACK_STATUS(Creature creature, TYPE type, int skillId, int value) {
    this.creature = creature;
    this.type = type;
    this.skillId = skillId;
    this.value = value;
  }

  public SM_ATTACK_STATUS(Creature creature, int value) {
    this.creature = creature;
    this.type = TYPE.REGULAR;
    this.skillId = 0;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.creature.getObjectId());
    switch (this.type) {

      case DAMAGE:
        writeD(buf, -this.value);
        break;
      default:
        writeD(buf, this.value);
        break;
    }
    writeC(buf, this.type.getValue());
    writeC(buf, this.creature.getLifeStats().getHpPercentage());
    writeH(buf, this.skillId);
    writeH(buf, 166);
  }
}
