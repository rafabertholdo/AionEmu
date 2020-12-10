package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestCondition")
@XmlSeeAlso({NpcIdCondition.class, DialogIdCondition.class, PcInventoryCondition.class, QuestVarCondition.class, QuestStatusCondition.class})
public abstract class QuestCondition
{
  @XmlAttribute(required = true)
  protected ConditionOperation op;
  
  public ConditionOperation getOp() {
    return this.op;
  }
  
  public abstract boolean doCheck(QuestEnv paramQuestEnv);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\QuestCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
