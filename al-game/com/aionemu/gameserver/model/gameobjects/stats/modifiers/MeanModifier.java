package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MeanModifier")
public class MeanModifier extends StatModifier {
  @XmlAttribute
  private int min;
  @XmlAttribute
  private int max;

  public int apply(int baseStat, int currentStat) {
    return baseStat + Math.round((this.min + this.max) / 2.0F);
  }

  public StatModifierPriority getPriority() {
    return StatModifierPriority.HIGH;
  }

  public String toString() {
    String s = super.toString() + ",m:" + this.min + ",M:" + this.max;
    return s;
  }
}
