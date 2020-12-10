package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DialogIdCondition")
public class DialogIdCondition extends QuestCondition {
  @XmlAttribute(required = true)
  protected int value;

  public int getValue() {
    return this.value;
  }

  public boolean doCheck(QuestEnv env) {
    int data = env.getDialogId().intValue();
    switch (getOp()) {

      case EQUAL:
        return (data == this.value);
      case NOT_EQUAL:
        return (data != this.value);
    }
    return false;
  }
}
