package com.aionemu.gameserver.skillengine.properties;

import com.aionemu.gameserver.skillengine.model.Skill;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Property")
public abstract class Property {
  public abstract boolean set(Skill paramSkill);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\properties\Property.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
