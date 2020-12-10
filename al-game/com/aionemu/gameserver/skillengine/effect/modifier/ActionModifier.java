package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ActionModifier")
public abstract class ActionModifier {
  public abstract int analyze(Effect paramEffect, int paramInt);
  
  public abstract boolean check(Effect paramEffect);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\ActionModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
