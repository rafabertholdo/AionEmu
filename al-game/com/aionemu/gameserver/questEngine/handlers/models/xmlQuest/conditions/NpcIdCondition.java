package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.conditions;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.questEngine.model.ConditionOperation;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;






























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NpcIdCondition")
public class NpcIdCondition
  extends QuestCondition
{
  @XmlAttribute(required = true)
  protected int values;
  
  public boolean doCheck(QuestEnv env) {
    int id = 0;
    VisibleObject visibleObject = env.getVisibleObject();
    if (visibleObject != null && visibleObject instanceof Npc)
    {
      id = ((Npc)visibleObject).getNpcId();
    }
    switch (getOp()) {
      
      case EQUAL:
        return (id == this.values);
      case GREATER:
        return (id > this.values);
      case GREATER_EQUAL:
        return (id >= this.values);
      case LESSER:
        return (id < this.values);
      case LESSER_EQUAL:
        return (id <= this.values);
      case NOT_EQUAL:
        return (id != this.values);
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\conditions\NpcIdCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
