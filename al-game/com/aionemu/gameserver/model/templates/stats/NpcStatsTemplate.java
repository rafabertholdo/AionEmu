package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "npc_stats_template")
public class NpcStatsTemplate extends StatsTemplate {
  @XmlAttribute(name = "run_speed_fight")
  private float runSpeedFight;
  @XmlAttribute(name = "pdef")
  private int pdef;
  @XmlAttribute(name = "mdef")
  private int mdef;
  @XmlAttribute(name = "crit")
  private int crit;
  @XmlAttribute(name = "accuracy")
  private int accuracy;
  @XmlAttribute(name = "power")
  private int power;
  @XmlAttribute(name = "maxXp")
  private int maxXp;

  public float getRunSpeedFight() {
    return this.runSpeedFight;
  }

  public float getPdef() {
    return this.pdef;
  }

  public float getMdef() {
    return this.mdef;
  }

  public float getCrit() {
    return this.crit;
  }

  public float getAccuracy() {
    return this.accuracy;
  }

  public int getPower() {
    return this.power;
  }

  public int getMaxXp() {
    return this.maxXp;
  }
}
