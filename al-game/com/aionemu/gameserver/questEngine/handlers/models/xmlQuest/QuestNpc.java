package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestNpc", propOrder = { "dialog" })
public class QuestNpc {
  protected List<QuestDialog> dialog;
  @XmlAttribute(required = true)
  protected int id;

  public boolean operate(QuestEnv env, QuestState qs) {
    int npcId = -1;
    if (env.getVisibleObject() instanceof Npc)
      npcId = ((Npc) env.getVisibleObject()).getNpcId();
    if (npcId != this.id)
      return false;
    for (QuestDialog questDialog : this.dialog) {

      if (questDialog.operate(env, qs))
        return true;
    }
    return false;
  }
}
