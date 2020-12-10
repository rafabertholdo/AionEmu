package com.aionemu.gameserver.skillengine.change;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Change")
public class Change {
  @XmlAttribute(required = true)
  private StatEnum stat;
  @XmlAttribute(required = true)
  private Func func;
  @XmlAttribute(required = true)
  private int value;
  @XmlAttribute
  private int delta;

  public StatEnum getStat() {
    return this.stat;
  }

  public Func getFunc() {
    return this.func;
  }

  public int getValue() {
    return this.value;
  }

  public int getDelta() {
    return this.delta;
  }
}
