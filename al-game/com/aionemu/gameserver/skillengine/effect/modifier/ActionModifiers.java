package com.aionemu.gameserver.skillengine.effect.modifier;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActionModifiers")
public class ActionModifiers {
  @XmlElements({ @XmlElement(name = "stumbledamage", type = StumbleDamageModifier.class),
      @XmlElement(name = "frontdamage", type = FrontDamageModifier.class),
      @XmlElement(name = "backdamage", type = BackDamageModifier.class),
      @XmlElement(name = "stundamage", type = StunDamageModifier.class),
      @XmlElement(name = "poisondamage", type = PoisonDamageModifier.class),
      @XmlElement(name = "targetrace", type = TargetRaceDamageModifier.class) })
  protected List<ActionModifier> actionModifiers;

  public List<ActionModifier> getActionModifiers() {
    if (this.actionModifiers == null) {
      this.actionModifiers = new ArrayList<ActionModifier>();
    }
    return this.actionModifiers;
  }
}
