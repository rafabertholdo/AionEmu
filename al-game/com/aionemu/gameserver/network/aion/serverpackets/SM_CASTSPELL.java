package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_CASTSPELL extends AionServerPacket {
  private int attackerObjectId;
  private int spellId;
  private int level;
  private int targetType;
  private int duration;
  private int targetObjectId;
  private float x;
  private float y;
  private float z;

  public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, int targetObjectId, int duration) {
    this.attackerObjectId = attackerObjectId;
    this.spellId = spellId;
    this.level = level;
    this.targetType = targetType;
    this.targetObjectId = targetObjectId;
    this.duration = duration;
  }

  public SM_CASTSPELL(int attackerObjectId, int spellId, int level, int targetType, float x, float y, float z,
      int duration) {
    this(attackerObjectId, spellId, level, targetType, 0, duration);
    this.x = x;
    this.y = y;
    this.z = z;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.attackerObjectId);
    writeH(buf, this.spellId);
    writeC(buf, this.level);

    writeC(buf, this.targetType);
    switch (this.targetType) {

      case 0:
        writeD(buf, this.targetObjectId);
        break;
      case 1:
        writeF(buf, this.x);
        writeF(buf, this.y);
        writeF(buf, this.z);
        break;
    }

    writeH(buf, this.duration);
    writeD(buf, 0);
  }
}
