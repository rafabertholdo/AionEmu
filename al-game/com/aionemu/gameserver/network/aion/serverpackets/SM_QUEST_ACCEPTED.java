package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.nio.ByteBuffer;



























public class SM_QUEST_ACCEPTED
  extends AionServerPacket
{
  private int questId;
  private int status;
  private int step;
  private int action;
  private int timer;
  
  public SM_QUEST_ACCEPTED(int questId, int status, int step) {
    this.action = 1;
    this.questId = questId;
    this.status = status;
    this.step = step;
  }




  
  public SM_QUEST_ACCEPTED(int questId, QuestStatus status, int step) {
    this.action = 2;
    this.questId = questId;
    this.status = status.value();
    this.step = step;
  }




  
  public SM_QUEST_ACCEPTED(int questId) {
    this.action = 3;
    this.questId = questId;
    this.status = 0;
    this.step = 0;
  }




  
  public SM_QUEST_ACCEPTED(int questId, int timer) {
    this.action = 4;
    this.questId = questId;
    this.timer = timer;
    this.step = 0;
  }




  
  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    switch (this.action) {
      
      case 1:
      case 2:
      case 3:
        writeC(buf, this.action);
        writeD(buf, this.questId);
        writeC(buf, this.status);
        writeC(buf, 0);
        writeD(buf, this.step);
        writeH(buf, 0);
        break;
      case 4:
        writeC(buf, this.action);
        writeD(buf, this.questId);
        writeD(buf, this.timer);
        writeC(buf, 1);
        writeH(buf, 0);
        writeC(buf, 1);
        break;
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_QUEST_ACCEPTED.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
