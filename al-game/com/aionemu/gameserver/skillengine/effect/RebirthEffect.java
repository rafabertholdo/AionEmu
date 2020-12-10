package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RebirthEffect")
public class RebirthEffect
  extends EffectTemplate
{
  @XmlAttribute(name = "resurrect_percent", required = true)
  protected int resurrectPercent;
  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void calculate(Effect effect) {
    effect.addSucessEffect(this);
  }

  
  public int getResurrectPercent() {
    return this.resurrectPercent;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\RebirthEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
