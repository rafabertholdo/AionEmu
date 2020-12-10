package com.aionemu.gameserver.model.templates.goods;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GoodsList")
public class GoodsList {
  protected List<Item> item;
  @XmlAttribute
  protected int id;
  protected List<Integer> itemIdList;

  void afterUnmarshal(Unmarshaller u, Object parent) {
    this.itemIdList = new ArrayList<Integer>();

    if (this.item == null) {
      return;
    }
    for (Item it : this.item) {
      this.itemIdList.add(Integer.valueOf(it.getId()));
    }
    this.item = null;
  }

  public int getId() {
    return this.id;
  }

  public List<Integer> getItemIdList() {
    return this.itemIdList;
  }

  @XmlAccessorType(XmlAccessType.FIELD)
  @XmlType(name = "")
  public static class Item {
    @XmlAttribute
    protected int id;

    public int getId() {
      return this.id;
    }
  }
}
