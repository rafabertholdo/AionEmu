package com.aionemu.gameserver.model.templates.item;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
























@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "Godstone")
public class GodstoneInfo
{
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


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\item\GodstoneInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
