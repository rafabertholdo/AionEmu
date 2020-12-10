package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.nio.ByteBuffer;






















public class SM_QUEST_STEP
  extends AionServerPacket
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_QUEST_STEP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
