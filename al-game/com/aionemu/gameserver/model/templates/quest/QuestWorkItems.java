package com.aionemu.gameserver.model.templates.quest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestWorkItems", propOrder = { "questWorkItem" })
public class QuestWorkItems {
  @XmlElement(name = "quest_work_item")
  protected List<QuestItems> questWorkItem;

  public List<QuestItems> getQuestWorkItem() {
    if (this.questWorkItem == null) {
      this.questWorkItem = new ArrayList<QuestItems>();
    }
    return this.questWorkItem;
  }
}
