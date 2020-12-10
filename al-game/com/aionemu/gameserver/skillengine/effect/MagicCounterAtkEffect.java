package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.gameserver.controllers.movement.ActionObserver;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.skillengine.model.SkillType;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MagicCounterAtkEffect")
public class MagicCounterAtkEffect
  extends EffectTemplate
{
  @XmlAttribute
  protected int percent;
  @XmlAttribute
  protected int maxdmg;
  
  public void calculate(Effect effect) {
    if (calculateEffectResistRate(effect, null)) {
      effect.addSucessEffect(this);
    }
  }

  
  public void applyEffect(Effect effect) {
    effect.addToEffectedController();
  }


  
  public void startEffect(final Effect effect) {
    final Creature effector = effect.getEffector();
    final Creature effected = effect.getEffected();
    final CreatureLifeStats<? extends Creature> cls = effect.getEffected().getLifeStats();
    ActionObserver observer = null;
    
    observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
      {
        
        public void skilluse(Skill skill)
        {
          if (skill.getSkillTemplate().getType() == SkillType.MAGICAL)
          {
            if (cls.getMaxHp() / 100 * MagicCounterAtkEffect.this.percent <= MagicCounterAtkEffect.this.maxdmg) {
              effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, cls.getMaxHp() / 100 * MagicCounterAtkEffect.this.percent);
            } else {
              effected.getController().onAttack(effector, MagicCounterAtkEffect.this.maxdmg);
            } 
          }
        }
      };
    
    effect.setActionObserver(observer, this.position);
    effected.getObserveController().addObserver(observer);
  }


  
  public void endEffect(Effect effect) {
    ActionObserver observer = effect.getActionObserver(this.position);
    if (observer != null)
      effect.getEffected().getObserveController().removeObserver(observer); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\MagicCounterAtkEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
