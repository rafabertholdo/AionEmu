package com.aionemu.gameserver.skillengine.condition;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DpCondition")
public class DpCondition
  extends Condition
{
  @XmlAttribute(required = true)
  protected int value;
  
  public boolean verify(Skill skill) {
    return (((Player)skill.getEffector()).getCommonData().getDp() >= this.value);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\condition\DpCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
