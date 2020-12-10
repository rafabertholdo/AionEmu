package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Map;

public class SM_SKILL_COOLDOWN extends AionServerPacket {
  private Map<Integer, Long> cooldowns;

  public SM_SKILL_COOLDOWN(Map<Integer, Long> cooldowns) {
    this.cooldowns = cooldowns;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.cooldowns.size());
    long currentTime = System.currentTimeMillis();
    for (Map.Entry<Integer, Long> entry : this.cooldowns.entrySet()) {

      writeH(buf, ((Integer) entry.getKey()).intValue());
      int left = Math.round((float) ((((Long) entry.getValue()).longValue() - currentTime) / 1000L));
      writeD(buf, (left > 0) ? left : 0);
    }
  }
}
