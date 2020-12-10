package com.aionemu.gameserver.model.templates.itemset;

import java.util.List;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "itemset")
@XmlAccessorType(XmlAccessType.FIELD)
public class ItemSetTemplate {
  @XmlElement(required = true)
  protected List<ItemPart> itempart;
  @XmlElement(required = true)
  protected List<PartBonus> partbonus;
  protected FullBonus fullbonus;
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected int id;

  void afterUnmarshal(Unmarshaller u, Object parent) {
    if (this.fullbonus != null) {

      this.fullbonus.setNumberOfItems(this.itempart.size());
    }
  }

  public List<ItemPart> getItempart() {
    return this.itempart;
  }

  public List<PartBonus> getPartbonus() {
    return this.partbonus;
  }

  public FullBonus getFullbonus() {
    return this.fullbonus;
  }

  public String getName() {
    return this.name;
  }

  public int getId() {
    return this.id;
  }
}
