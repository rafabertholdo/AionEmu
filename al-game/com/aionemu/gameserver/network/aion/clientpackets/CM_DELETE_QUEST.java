package com.aionemu.gameserver.network.aion.clientpackets;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.QuestsData;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionClientPacket;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.QuestEngine;

public class CM_DELETE_QUEST extends AionClientPacket {
  static QuestsData questsData = DataManager.QUEST_DATA;

  public int questId;

  public CM_DELETE_QUEST(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.questId = readH();
  }

  protected void runImpl() {
    Player player = ((AionConnection) getConnection()).getActivePlayer();
    if (questsData.getQuestById(this.questId).isTimer()) {

      player.getController().cancelTask(TaskId.QUEST_TIMER);
      sendPacket((AionServerPacket) new SM_QUEST_ACCEPTED(this.questId, 0));
    }
    if (!QuestEngine.getInstance().deleteQuest(player, this.questId))
      return;
    sendPacket((AionServerPacket) new SM_QUEST_ACCEPTED(this.questId));
    player.getController().updateNearbyQuests();
  }
}
