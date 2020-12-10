package com.aionemu.gameserver.model.templates.stats;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;





















@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "player_stats_template")
public class PlayerStatsTemplate
  extends StatsTemplate
{
  @XmlAttribute(name = "power")
  private int power;
  @XmlAttribute(name = "health")
  private int health;
  @XmlAttribute(name = "agility")
  private int agility;
  @XmlAttribute(name = "accuracy")
  private int accuracy;
  @XmlAttribute(name = "knowledge")
  private int knowledge;
  @XmlAttribute(name = "will")
  private int will;
  
  public int getPower() {
    return this.power;
  }

  
  public int getHealth() {
    return this.health;
  }

  
  public int getAgility() {
    return this.agility;
  }

  
  public int getAccuracy() {
    return this.accuracy;
  }

  
  public int getKnowledge() {
    return this.knowledge;
  }

  
  public int getWill() {
    return this.will;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\stats\PlayerStatsTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
