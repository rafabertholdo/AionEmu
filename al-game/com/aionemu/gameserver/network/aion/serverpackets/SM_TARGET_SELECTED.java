package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_TARGET_SELECTED extends AionServerPacket {
  private Player player;
  private int level;
  private int maxHp;
  private int currentHp;
  private int targetObjId;

  public SM_TARGET_SELECTED(Player player) {
    this.player = player;
    if (player.getTarget() instanceof Creature) {

      this.level = ((Creature) player.getTarget()).getLevel();
      this.maxHp = ((Creature) player.getTarget()).getLifeStats().getMaxHp();
      this.currentHp = ((Creature) player.getTarget()).getLifeStats().getCurrentHp();

    } else {

      this.level = 1;
      this.maxHp = 1;
      this.currentHp = 1;
    }

    if (player.getTarget() != null) {
      this.targetObjId = player.getTarget().getObjectId();
    }
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.targetObjId);
    writeH(buf, this.level);
    writeD(buf, this.maxHp);
    writeD(buf, this.currentHp);
  }
}
