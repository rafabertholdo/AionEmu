package com.aionemu.gameserver.model.templates.gather;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Material")
public class Material {
  @XmlAttribute
  protected String name;
  @XmlAttribute
  protected Integer itemid;
  @XmlAttribute
  protected Integer nameid;
  @XmlAttribute
  protected Integer rate;

  public String getName() {
    return this.name;
  }

  public Integer getItemid() {
    return this.itemid;
  }

  public Integer getNameid() {
    return this.nameid;
  }

  public Integer getRate() {
    return this.rate;
  }
}
