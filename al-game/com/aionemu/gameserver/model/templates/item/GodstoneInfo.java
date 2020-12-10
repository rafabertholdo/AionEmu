package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Godstone")
public class GodstoneInfo {
  @XmlAttribute
  private int skillid;
  @XmlAttribute
  private int skilllvl;
  @XmlAttribute
  private int probability;
  @XmlAttribute
  private int probabilityleft;

  public int getSkillid() {
    return this.skillid;
  }

  public int getSkilllvl() {
    return this.skilllvl;
  }

  public int getProbability() {
    return this.probability;
  }

  public int getProbabilityleft() {
    return this.probabilityleft;
  }
}
