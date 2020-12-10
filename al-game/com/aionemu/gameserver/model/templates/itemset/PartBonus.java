package com.aionemu.gameserver.model.templates.itemset;

import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
import com.aionemu.gameserver.model.templates.stats.ModifiersTemplate;
import java.util.TreeSet;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "PartBonus")
@XmlAccessorType(XmlAccessType.FIELD)
public class PartBonus {
  @XmlAttribute
  protected int count;
  @XmlElement(name = "modifiers", required = false)
  protected ModifiersTemplate modifiers;

  public TreeSet<StatModifier> getModifiers() {
    return (this.modifiers != null) ? this.modifiers.getModifiers() : null;
  }

  public int getCount() {
    return this.count;
  }
}
