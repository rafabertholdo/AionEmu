package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SM_QUEST_LIST extends AionServerPacket {
  private SortedMap<Integer, QuestState> completeQuestList = new TreeMap<Integer, QuestState>();
  private List<QuestState> startedQuestList = new ArrayList<QuestState>();

  public SM_QUEST_LIST(Player player) {
    for (QuestState qs : player.getQuestStateList().getAllQuestState()) {

      if (qs.getStatus() == QuestStatus.COMPLETE) {
        this.completeQuestList.put(Integer.valueOf(qs.getQuestId()), qs);
        continue;
      }
      if (qs.getStatus() != QuestStatus.NONE) {
        this.startedQuestList.add(qs);
      }
    }
  }

  protected void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.completeQuestList.size());
    for (QuestState qs : this.completeQuestList.values()) {

      writeH(buf, qs.getQuestId());
      writeH(buf, 0);
      writeC(buf, qs.getCompliteCount());
    }
    writeC(buf, this.startedQuestList.size());
    for (QuestState qs : this.startedQuestList) {

      writeH(buf, qs.getQuestId());
      writeH(buf, 0);
    }
    for (QuestState qs : this.startedQuestList) {

      writeC(buf, qs.getStatus().value());
      writeD(buf, qs.getQuestVars().getQuestVars());
      writeC(buf, 0);
    }
  }
}
