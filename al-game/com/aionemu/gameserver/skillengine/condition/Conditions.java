package com.aionemu.gameserver.skillengine.condition;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Conditions", propOrder = { "conditions" })
public class Conditions {
  @XmlElements({ @XmlElement(name = "target", type = TargetCondition.class),
      @XmlElement(name = "mp", type = MpCondition.class), @XmlElement(name = "hp", type = HpCondition.class),
      @XmlElement(name = "dp", type = DpCondition.class),
      @XmlElement(name = "playermove", type = PlayerMovedCondition.class),
      @XmlElement(name = "arrowcheck", type = ArrowCheckCondition.class) })
  protected List<Condition> conditions;

  public List<Condition> getConditions() {
    if (this.conditions == null) {
      this.conditions = new ArrayList<Condition>();
    }
    return this.conditions;
  }
}
