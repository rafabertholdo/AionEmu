package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.items.ItemCooldown;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.Map;

public class SM_ITEM_COOLDOWN extends AionServerPacket {
  private Map<Integer, ItemCooldown> cooldowns;

  public SM_ITEM_COOLDOWN(Map<Integer, ItemCooldown> cooldowns) {
    this.cooldowns = cooldowns;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.cooldowns.size());
    long currentTime = System.currentTimeMillis();
    for (Map.Entry<Integer, ItemCooldown> entry : this.cooldowns.entrySet()) {

      writeH(buf, ((Integer) entry.getKey()).intValue());
      int left = Math.round((float) ((((ItemCooldown) entry.getValue()).getReuseTime() - currentTime) / 1000L));
      writeD(buf, (left > 0) ? left : 0);
      writeD(buf, ((ItemCooldown) entry.getValue()).getUseDelay());
    }
  }
}
