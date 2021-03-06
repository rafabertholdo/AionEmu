package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.model.legion.LegionEmblem;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEGION_SEND_EMBLEM;
import com.aionemu.gameserver.services.LegionService;

public class CM_LEGION_SEND_EMBLEM extends AionClientPacket {
  private int legionId;

  public CM_LEGION_SEND_EMBLEM(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.legionId = readD();
  }

  protected void runImpl() {
    Legion legion = LegionService.getInstance().getLegion(this.legionId);
    LegionEmblem legionEmblem = legion.getLegionEmblem();
    sendPacket((AionServerPacket) new SM_LEGION_SEND_EMBLEM(this.legionId, legionEmblem.getEmblemId(),
        legionEmblem.getColor_r(), legionEmblem.getColor_g(), legionEmblem.getColor_b(), legion.getLegionName()));
  }
}
