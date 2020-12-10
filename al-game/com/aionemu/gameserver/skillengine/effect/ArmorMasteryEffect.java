package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.item.ArmorType;
import com.aionemu.gameserver.skillengine.model.Effect;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArmorMasteryEffect")
public class ArmorMasteryEffect
  extends BufEffect
{
  @XmlAttribute(name = "armor")
  private ArmorType armorType;
  
  public ArmorType getArmorType() {
    return this.armorType;
  }



  
  public void calculate(Effect effect) {
    Player player = (Player)effect.getEffector();
    
    Integer skillId = player.getSkillList().getArmorMasterySkill(this.armorType);
    if (skillId != null && skillId.intValue() != effect.getSkillId()) {
      return;
    }
    boolean armorMasterySet = player.getEffectController().isArmorMasterySet(skillId.intValue());
    boolean isArmorEquiped = player.getEquipment().isArmorEquipped(this.armorType);
    if (!armorMasterySet && isArmorEquiped) {
      effect.addSucessEffect(this);
    }
  }

  
  public void startEffect(Effect effect) {
    super.startEffect(effect);
    Player player = (Player)effect.getEffector();
    player.getEffectController().removeEffect(effect.getSkillId());
    player.getEffectController().setArmorMastery(effect.getSkillId());
  }


  
  public void endEffect(Effect effect) {
    super.endEffect(effect);
    Player player = (Player)effect.getEffector();
    player.getEffectController().unsetArmorMastery();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ArmorMasteryEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
