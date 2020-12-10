package com.aionemu.gameserver.questEngine.handlers.models.xmlQuest.operations;

import com.aionemu.gameserver.questEngine.model.QuestEnv;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestOperations", propOrder = { "operations" })
public class QuestOperations {
  @XmlElements({ @XmlElement(name = "take_item", type = TakeItemOperation.class),
      @XmlElement(name = "npc_dialog", type = NpcDialogOperation.class),
      @XmlElement(name = "set_quest_status", type = SetQuestStatusOperation.class),
      @XmlElement(name = "give_item", type = GiveItemOperation.class),
      @XmlElement(name = "start_quest", type = StartQuestOperation.class),
      @XmlElement(name = "npc_use", type = ActionItemUseOperation.class),
      @XmlElement(name = "set_quest_var", type = SetQuestVarOperation.class),
      @XmlElement(name = "collect_items", type = CollectItemQuestOperation.class) })
  protected List<QuestOperation> operations;
  @XmlAttribute
  protected Boolean override;

  public boolean isOverride() {
    if (this.override == null) {
      return true;
    }

    return this.override.booleanValue();
  }

  public boolean operate(QuestEnv env) {
    if (this.operations != null) {
      for (QuestOperation oper : this.operations) {
        oper.doOperate(env);
      }
    }
    return isOverride();
  }
}
