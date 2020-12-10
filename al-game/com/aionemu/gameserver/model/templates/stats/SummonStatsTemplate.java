package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "summon_stats_template")
public class SummonStatsTemplate extends StatsTemplate {
  @XmlAttribute(name = "pdefense")
  private int pdefense;
  @XmlAttribute(name = "mresist")
  private int mresist;

  public int getPdefense() {
    return this.pdefense;
  }

  public int getMresist() {
    return this.mresist;
  }
}
