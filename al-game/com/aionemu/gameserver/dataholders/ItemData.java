package com.aionemu.gameserver.dataholders;

import com.aionemu.gameserver.model.templates.item.ItemTemplate;
import gnu.trove.TIntObjectHashMap;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "item_templates")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemData {
  @XmlElement(name = "item_template")
  private List<ItemTemplate> its;
  private TIntObjectHashMap<ItemTemplate> items;

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.items = new TIntObjectHashMap();
    for (ItemTemplate it : this.its) {
      this.items.put(it.getTemplateId(), it);
    }
    this.its = null;
  }

  public ItemTemplate getItemTemplate(int itemId) {
    return (ItemTemplate) this.items.get(itemId);
  }

  public int size() {
    return this.items.size();
  }
}
