package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.utils.PositionUtil;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;



























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BackDamageModifier")
public class BackDamageModifier
  extends ActionModifier
{
  @XmlAttribute(required = true)
  protected int delta;
  @XmlAttribute(required = true)
  protected int value;
  
  public int analyze(Effect effect, int originalValue) {
    return originalValue + this.value + effect.getSkillLevel() * this.delta;
  }


  
  public boolean check(Effect effect) {
    return PositionUtil.isBehindTarget((VisibleObject)effect.getEffector(), (VisibleObject)effect.getEffected());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\BackDamageModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
