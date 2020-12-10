package com.aionemu.gameserver.model.templates.siegelocation;

import com.aionemu.gameserver.model.siege.SiegeType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "siegelocation")
public class SiegeLocationTemplate {
  @XmlAttribute(name = "id")
  protected int id;
  @XmlAttribute(name = "type")
  protected SiegeType type;
  @XmlAttribute(name = "world")
  protected int world;

  public int getId() {
    return this.id;
  }

  public SiegeType getType() {
    return this.type;
  }

  public int getWorldId() {
    return this.world;
  }
}
