package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;

import com.aionemu.gameserver.questEngine.model.QuestEnv;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestOperation")
@XmlSeeAlso({TakeItemOperation.class, StartQuestOperation.class, SetQuestVarOperation.class, NpcDialogOperation.class, GiveItemOperation.class, SetQuestStatusOperation.class})
public abstract class QuestOperation {
  public abstract void doOperate(QuestEnv paramQuestEnv);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\questEngine\handlers\models\xmlQuest\operations\QuestOperation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
