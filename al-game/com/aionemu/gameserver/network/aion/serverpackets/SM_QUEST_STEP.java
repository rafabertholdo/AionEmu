package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.nio.ByteBuffer;

public class SM_QUEST_STEP extends AionServerPacket {
  private int questId;
  private int status;
  private int vars;

  public SM_QUEST_STEP(int questId, QuestStatus status, int vars) {
    this.questId = questId;
    this.status = status.value();
    this.vars = vars;
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.questId);
    writeC(buf, this.status);
    writeD(buf, this.vars);
    writeC(buf, 0);
  }
}
