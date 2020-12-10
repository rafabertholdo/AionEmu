package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestVarCondition")
public class QuestVarCondition extends QuestCondition {
  @XmlAttribute(required = true)
  protected int value;
  @XmlAttribute(name = "var_id", required = true)
  protected int varId;

  public boolean doCheck(QuestEnv env) {
    QuestState qs = env.getPlayer().getQuestStateList().getQuestState(env.getQuestId().intValue());
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVars().getVarById(this.varId);
    switch (getOp()) {

      case EQUAL:
        return (var == this.value);
      case GREATER:
        return (var > this.value);
      case GREATER_EQUAL:
        return (var >= this.value);
      case LESSER:
        return (var < this.value);
      case LESSER_EQUAL:
        return (var <= this.value);
      case NOT_EQUAL:
        return (var != this.value);
    }
    return false;
  }
}
