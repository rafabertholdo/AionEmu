package com.aionemu.gameserver.skillengine.effect;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.SkillElement;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.skillengine.change.Change;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifiers;
import com.aionemu.gameserver.skillengine.model.Effect;
import com.aionemu.gameserver.skillengine.model.HopType;
import com.aionemu.gameserver.skillengine.model.SkillTemplate;
import com.aionemu.gameserver.utils.stats.StatFunctions;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;























@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Effect")
public abstract class EffectTemplate
{
  protected ActionModifiers modifiers;
  protected List<Change> change;
  @XmlAttribute
  protected int effectid;
  @XmlAttribute(required = true)
  protected int duration;
  @XmlAttribute(name = "randomtime")
  protected int randomTime;
  @XmlAttribute(name = "e")
  protected int position;
  @XmlAttribute(name = "basiclvl")
  protected int basicLvl;
  @XmlAttribute(name = "element")
  protected SkillElement element = SkillElement.NONE;
  
  @XmlElement(name = "subeffect")
  protected SubEffect subEffect;
  
  @XmlAttribute(name = "hoptype")
  protected HopType hopType;
  
  @XmlAttribute(name = "hopa")
  protected int hopA;
  
  @XmlAttribute(name = "hopb")
  protected int hopB;

  
  public int getDuration() {
    return this.duration;
  }




  
  public int getRandomTime() {
    return this.randomTime;
  }





  
  public ActionModifiers getModifiers() {
    return this.modifiers;
  }





  
  public List<Change> getChange() {
    return this.change;
  }




  
  public int getEffectid() {
    return this.effectid;
  }




  
  public int getPosition() {
    return this.position;
  }




  
  public int getBasicLvl() {
    return this.basicLvl;
  }




  
  public SkillElement getElement() {
    return this.element;
  }






  
  protected int applyActionModifiers(Effect effect, int value) {
    if (this.modifiers == null) {
      return value;
    }


    
    for (ActionModifier modifier : this.modifiers.getActionModifiers()) {
      
      if (modifier.check(effect)) {
        return modifier.analyze(effect, value);
      }
    } 
    return value;
  }





  
  public abstract void calculate(Effect paramEffect);





  
  public abstract void applyEffect(Effect paramEffect);




  
  public void startEffect(Effect effect) {}




  
  public void calculateSubEffect(Effect effect) {
    if (this.subEffect == null) {
      return;
    }
    SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.subEffect.getSkillId());
    int duration = template.getEffectsDuration();
    Effect newEffect = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), duration);
    newEffect.initialize();
    effect.setSpellStatus(newEffect.getSpellStatus());
    effect.setSubEffect(newEffect);
  }







  
  public void calculateHate(Effect effect) {
    if (this.hopType == null) {
      return;
    }
    if (effect.getSuccessEffect().isEmpty()) {
      return;
    }
    int currentHate = effect.getEffectHate();
    if (this.hopType != null) {
      int skillLvl;
      switch (this.hopType) {
        
        case DAMAGE:
          currentHate += effect.getReserved1();
          break;
        case SKILLLV:
          skillLvl = effect.getSkillLevel();
          currentHate += this.hopB + this.hopA * skillLvl;
          break;
      } 
    
    } 
    if (currentHate == 0)
      currentHate = 1; 
    effect.setEffectHate(StatFunctions.calculateHate(effect.getEffector(), currentHate));
  }





  
  public void startSubEffect(Effect effect) {
    if (this.subEffect == null) {
      return;
    }
    effect.getSubEffect().applyEffect();
  }




  
  public void onPeriodicAction(Effect effect) {}



  
  public void endEffect(Effect effect) {}



  
  public boolean calculateEffectResistRate(Effect effect, StatEnum statEnum) {
    int effectPower = 1000;

    
    if (statEnum != null) {
      
      int stat = effect.getEffected().getGameStats().getCurrentStat(statEnum);
      effectPower -= stat;
    } 
    
    int attackerLevel = effect.getEffector().getLevel();
    int targetLevel = effect.getEffected().getLevel();
    
    float multipler = 0.0F;
    int differ = targetLevel - attackerLevel;
    
    if (differ > 0 && differ < 8) {
      
      multipler = differ / 10.0F;
      effectPower -= Math.round(effectPower * multipler);
    }
    else if (differ >= 8) {
      
      effectPower -= Math.round(effectPower * 0.8F);
    } 
    if (effect.getEffected() instanceof Npc) {
      
      float hpGaugeMod = ((Npc)effect.getEffected()).getObjectTemplate().getHpGauge();
      effectPower = (int)(effectPower - 200.0F * (1.0F + hpGaugeMod / 10.0F));
    } 
    return (Rnd.get() * 1000.0F < effectPower);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\EffectTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
