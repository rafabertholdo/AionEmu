package com.aionemu.gameserver.model.templates.quest;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollectItems", propOrder = { "collectItem" })
public class CollectItems {
  @XmlElement(name = "collect_item")
  protected List<CollectItem> collectItem;

  public List<CollectItem> getCollectItem() {
    if (this.collectItem == null) {
      this.collectItem = new ArrayList<CollectItem>();
    }
    return this.collectItem;
  }
}
