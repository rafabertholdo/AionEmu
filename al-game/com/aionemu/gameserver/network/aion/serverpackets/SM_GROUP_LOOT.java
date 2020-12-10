package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_GROUP_LOOT extends AionServerPacket {
  private int groupId;
  private int unk1;
  private int unk2;
  private int itemId;
  private int unk3;
  private int lootCorpseId;
  private int distributionId;
  private int playerId;
  private int luck;

  public SM_GROUP_LOOT(int groupId, int itemId, int lootCorpseId, int distributionId) {
    this.groupId = groupId;
    this.unk1 = 1;
    this.unk2 = 1;
    this.itemId = itemId;
    this.unk3 = 0;
    this.lootCorpseId = lootCorpseId;
    this.distributionId = distributionId;
    this.playerId = 0;
    this.luck = 1;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeD(buf, this.groupId);
    writeD(buf, this.unk1);
    writeD(buf, this.unk2);
    writeD(buf, this.itemId);
    writeC(buf, this.unk3);
    writeD(buf, this.lootCorpseId);
    writeC(buf, this.distributionId);
    writeD(buf, this.playerId);
    writeD(buf, this.luck);
  }
}
