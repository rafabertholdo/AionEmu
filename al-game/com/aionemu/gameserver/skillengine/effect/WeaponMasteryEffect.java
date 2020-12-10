package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.WeaponType;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WeaponMasteryEffect")
public class WeaponMasteryEffect
  extends BufEffect
{
  @XmlAttribute(name = "weapon")
  private WeaponType weaponType;
  
  public WeaponType getWeaponType() {
    return this.weaponType;
  }



  
  public void calculate(Effect effect) {
    Player player = (Player)effect.getEffector();
    
    Integer skillId = player.getSkillList().getWeaponMasterySkill(this.weaponType);
    if (skillId != null && skillId.intValue() != effect.getSkillId()) {
      return;
    }
    boolean weaponMasterySet = player.getEffectController().isWeaponMasterySet(skillId.intValue());
    boolean isWeaponEquiped = player.getEquipment().isWeaponEquipped(this.weaponType);
    if (!weaponMasterySet && isWeaponEquiped) {
      effect.addSucessEffect(this);
    }
  }

  
  public void endEffect(Effect effect) {
    super.endEffect(effect);
    Player player = (Player)effect.getEffector();
    player.getEffectController().unsetWeaponMastery();
  }


  
  public void startEffect(Effect effect) {
    Player player = (Player)effect.getEffector();
    player.getEffectController().removeEffect(player.getEffectController().getWeaponMastery());
    super.startEffect(effect);
    player.getEffectController().setWeaponMastery(effect.getSkillId());
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\WeaponMasteryEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
