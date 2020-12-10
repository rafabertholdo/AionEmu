package com.aionemu.gameserver.skillengine.effect.modifier;

import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.SkillTargetRace;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TargetRaceDamageModifier")
public class TargetRaceDamageModifier
  extends ActionModifier
{
  @XmlAttribute(name = "race")
  private SkillTargetRace skillTargetRace;
  @XmlAttribute(required = true)
  protected int delta;
  @XmlAttribute(required = true)
  protected int value;
  
  public int analyze(Effect effect, int originalValue) {
    Creature effected = effect.getEffected();
    
    if (effected instanceof Player) {
      
      int newValue = originalValue + this.value + effect.getSkillLevel() * this.delta;
      Player player = (Player)effected;
      switch (this.skillTargetRace) {
        
        case ASMODIANS:
          if (player.getCommonData().getRace() == Race.ASMODIANS)
            return newValue; 
          break;
        case ELYOS:
          if (player.getCommonData().getRace() == Race.ELYOS)
            return newValue; 
          break;
      } 
    } 
    return originalValue;
  }


  
  public boolean check(Effect effect) {
    Creature effected = effect.getEffected();
    if (effected instanceof Player) {

      
      Player player = (Player)effected;
      Race race = player.getCommonData().getRace();
      return ((race == Race.ASMODIANS && this.skillTargetRace == SkillTargetRace.ASMODIANS) || (race == Race.ELYOS && this.skillTargetRace == SkillTargetRace.ELYOS));
    } 
    
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\modifier\TargetRaceDamageModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
