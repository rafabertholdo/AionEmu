package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;

import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestVar", propOrder = { "npc" })
public class QuestVar {
  protected List<QuestNpc> npc;
  @XmlAttribute(required = true)
  protected int value;

  public boolean operate(QuestEnv env, QuestState qs) {
    int var = -1;
    if (qs != null)
      var = qs.getQuestVars().getQuestVars();
    if (var != this.value)
      return false;
    for (QuestNpc questNpc : this.npc) {

      if (questNpc.operate(env, qs))
        return true;
    }
    return false;
  }
}
