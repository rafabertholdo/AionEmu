package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;




























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestStatusCondition")
public class QuestStatusCondition
  extends QuestCondition
{
  @XmlAttribute(required = true)
  protected QuestStatus value;
  @XmlAttribute(name = "quest_id")
  protected Integer questId;
  
  public boolean doCheck(QuestEnv env) {
    Player player = env.getPlayer();
    int qstatus = 0;
    int id = env.getQuestId().intValue();
    if (this.questId != null)
      id = this.questId.intValue(); 
    QuestState qs = player.getQuestStateList().getQuestState(id);
    if (qs != null) {
      qstatus = qs.getStatus().value();
    }
    switch (getOp()) {
      
      case EQUAL:
        return (qstatus == this.value.value());
      case GREATER:
        return (qstatus > this.value.value());
      case GREATER_EQUAL:
        return (qstatus >= this.value.value());
      case LESSER:
        return (qstatus < this.value.value());
      case LESSER_EQUAL:
        return (qstatus <= this.value.value());
      case NOT_EQUAL:
        return (qstatus != this.value.value());
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\QuestStatusCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
