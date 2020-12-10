package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest;

import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions.QuestConditions;
import com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations.QuestOperations;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestDialog", propOrder = { "conditions", "operations" })
public class QuestDialog {
  protected QuestConditions conditions;
  protected QuestOperations operations;
  @XmlAttribute(required = true)
  protected int id;

  public boolean operate(QuestEnv env, QuestState qs) {
    if (env.getDialogId().intValue() != this.id)
      return false;
    if (this.conditions == null || this.conditions.checkConditionOfSet(env)) {
      if (this.operations != null) {
        return this.operations.operate(env);
      }
    }
    return false;
  }
}
