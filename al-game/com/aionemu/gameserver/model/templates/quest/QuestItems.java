package com.aionemu.gameserver.model.templates.quest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QuestItems")
public class QuestItems {
  @XmlAttribute(name = "item_id")
  protected Integer itemId;
  @XmlAttribute
  protected Integer count;

  public QuestItems() {
  }

  public QuestItems(int itemId, int count) {
    this.itemId = Integer.valueOf(itemId);
    this.count = Integer.valueOf(count);
  }

  public Integer getItemId() {
    return this.itemId;
  }

  public Integer getCount() {
    return this.count;
  }
}
