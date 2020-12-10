package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;

public class SM_CRAFT_UPDATE extends AionServerPacket {
  private int skillId;
  private int itemId;
  private int action;
  private int success;
  private int failure;
  private int nameId;

  public SM_CRAFT_UPDATE(int skillId, ItemTemplate item, int success, int failure, int action) {
    this.action = action;
    this.skillId = skillId;
    this.itemId = item.getTemplateId();
    this.success = success;
    this.failure = failure;
    this.nameId = item.getNameId();
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.skillId);
    writeC(buf, this.action);
    writeD(buf, this.itemId);

    switch (this.action) {

      case 0:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 0);
        writeD(buf, 1200);
        writeD(buf, 1330048);
        writeH(buf, 36);
        writeD(buf, this.nameId);
        writeH(buf, 0);
        break;

      case 1:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      case 2:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      case 5:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      case 6:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 700);
        writeD(buf, 1200);
        writeD(buf, 0);
        writeH(buf, 0);
        break;

      case 7:
        writeD(buf, this.success);
        writeD(buf, this.failure);
        writeD(buf, 0);
        writeD(buf, 1200);
        writeD(buf, 1330050);
        writeH(buf, 36);
        writeD(buf, this.nameId);
        writeH(buf, 0);
        break;
    }
  }
}
