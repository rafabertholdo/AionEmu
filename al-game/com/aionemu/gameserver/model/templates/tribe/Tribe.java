package com.aionemu.gameserver.model.templates.tribe;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Tribe")
public class Tribe {
  public static final String GUARD_DARK = "GUARD_DARK";
  public static final String GUARD_LIGHT = "GUARD";
  @XmlElement(name = "aggro")
  protected AggroRelations aggroRelations;
  @XmlElement(name = "friend")
  protected FriendlyRelations friendlyRelations;
  @XmlElement(name = "support")
  protected SupportRelations supportRelations;
  @XmlElement(name = "neutral")
  protected NeutralRelations neutralRelations;
  @XmlElement(name = "hostile")
  protected HostileRelations hostileRelations;
  @XmlAttribute(required = true)
  protected String name;
  @XmlAttribute
  protected String base;

  public AggroRelations getAggroRelations() {
    return this.aggroRelations;
  }

  public SupportRelations getSupportRelations() {
    return this.supportRelations;
  }

  public FriendlyRelations getFriendlyRelations() {
    return this.friendlyRelations;
  }

  public NeutralRelations getNeutralRelations() {
    return this.neutralRelations;
  }

  public HostileRelations getHostileRelations() {
    return this.hostileRelations;
  }

  public String getName() {
    return this.name;
  }

  public String getBase() {
    return this.base;
  }
}
