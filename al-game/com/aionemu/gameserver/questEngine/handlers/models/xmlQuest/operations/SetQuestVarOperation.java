package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SetQuestVarOperation")
public class SetQuestVarOperation extends QuestOperation {
  @XmlAttribute(name = "var_id", required = true)
  protected int varId;
  @XmlAttribute(required = true)
  protected int value;

  public void doOperate(QuestEnv env) {
    Player player = env.getPlayer();
    int questId = env.getQuestId().intValue();
    QuestState qs = player.getQuestStateList().getQuestState(questId);
    if (qs != null) {

      qs.getQuestVars().setVarById(this.varId, this.value);
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_QUEST_ACCEPTED(questId, qs.getStatus(), qs.getQuestVars().getQuestVars()));
    }
  }
}
