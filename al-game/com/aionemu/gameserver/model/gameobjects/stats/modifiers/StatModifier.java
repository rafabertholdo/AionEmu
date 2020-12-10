package com.aionemu.gameserver.model.gameobjects.stats.modifiers;

import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javolution.text.TextBuilder;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Modifier")
public abstract class StatModifier implements Comparable<StatModifier> {
  @XmlAttribute
  private StatEnum name;
  @XmlAttribute
  private boolean bonus;
  protected static int MODIFIER_ID = 0;

  protected int id;

  public StatModifier() {
    nextId();
  }

  protected void setStat(StatEnum stat) {
    this.name = stat;
  }

  protected void setBonus(boolean bonus) {
    this.bonus = bonus;
  }

  protected void nextId() {
    MODIFIER_ID = (MODIFIER_ID + 1) % Integer.MAX_VALUE;
    this.id = MODIFIER_ID;
  }

  public StatEnum getStat() {
    return this.name;
  }

  public boolean isBonus() {
    return this.bonus;
  }

  public int compareTo(StatModifier o) {
    int result = getPriority().getValue() - o.getPriority().getValue();
    if (result == 0) {
      result = this.id - o.id;
    }
    return result;
  }

  public boolean equals(Object o) {
    boolean result = (o != null);
    result = (result && o instanceof StatModifier);
    result = (result && ((StatModifier) o).id == this.id);
    return result;
  }

  public int hashCode() {
    return this.id;
  }

  public String toString() {
    TextBuilder tb = TextBuilder.newInstance();

    tb.append(getClass().getSimpleName() + ",");
    tb.append("i:" + this.id + ",");
    tb.append("s:" + this.name + ",");
    tb.append("b:" + this.bonus);

    String toString = tb.toString();
    TextBuilder.recycle(tb);

    return toString;
  }

  public abstract int apply(int paramInt1, int paramInt2);

  public abstract StatModifierPriority getPriority();
}
