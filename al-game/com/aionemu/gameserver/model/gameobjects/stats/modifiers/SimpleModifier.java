package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SimpleModifier")
public abstract class SimpleModifier extends StatModifier {
  @XmlAttribute
  protected int value;

  public int getValue() {
    return this.value;
  }

  protected void setValue(int value) {
    this.value = value;
  }

  public String toString() {
    String s = super.toString() + ",v:" + this.value;
    return s;
  }
}
