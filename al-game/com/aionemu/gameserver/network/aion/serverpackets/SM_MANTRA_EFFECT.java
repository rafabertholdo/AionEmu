package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_MANTRA_EFFECT extends AionServerPacket {
  private Player player;
  private int subEffectId;

  public SM_MANTRA_EFFECT(Player player, int subEffectId) {
    this.player = player;
    this.subEffectId = subEffectId;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, 0);
    writeD(buf, this.player.getObjectId());
    writeH(buf, this.subEffectId);
  }
}
