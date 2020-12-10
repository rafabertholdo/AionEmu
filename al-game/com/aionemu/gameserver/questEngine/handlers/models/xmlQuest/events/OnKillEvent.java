package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.events;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations.QuestOperations;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OnKillEvent", propOrder = { "monsterInfos", "complite" })
public class OnKillEvent extends QuestEvent {
  @XmlElement(name = "monster_infos")
  protected List<MonsterInfo> monsterInfos;
  protected QuestOperations complite;

  public List<MonsterInfo> getMonsterInfos() {
    if (this.monsterInfos == null) {
      this.monsterInfos = new ArrayList<MonsterInfo>();
    }
    return this.monsterInfos;
  }

  public boolean operate(QuestEnv env) {
    if (this.monsterInfos == null || !(env.getVisibleObject() instanceof Npc)) {
      return false;
    }
    QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId().intValue());
    if (qs == null) {
      return false;
    }
    Npc npc = (Npc) env.getVisibleObject();
    for (MonsterInfo monsterInfo : this.monsterInfos) {

      if (monsterInfo.getNpcId() == npc.getNpcId()) {

        int var = qs.getQuestVarById(monsterInfo.getVarId());
        if (var >= ((monsterInfo.getMinVarValue() == null) ? 0 : monsterInfo.getMinVarValue().intValue())
            && var < monsterInfo.getMaxKill()) {

          qs.setQuestVarById(monsterInfo.getVarId(), var + 1);
          PacketSendUtility.sendPacket(env.getPlayer(),
              (AionServerPacket) new SM_QUEST_ACCEPTED(env.getQuestId().intValue(), qs.getStatus(),
                  qs.getQuestVars().getQuestVars()));
        }
      }
    }

    if (this.complite != null) {

      for (MonsterInfo monsterInfo : this.monsterInfos) {

        if (qs.getQuestVarById(monsterInfo.getVarId()) != qs.getQuestVarById(monsterInfo.getVarId()))
          return false;
      }
      this.complite.operate(env);
    }
    return false;
  }
}
