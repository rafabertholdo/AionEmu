package com.aionemu.gameserver.skillengine.effect;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElements;
import javax.xml.bind.annotation.XmlType;































































































































@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Effects")
public class Effects
{
  @XmlElements({@XmlElement(name = "root", type = RootEffect.class), @XmlElement(name = "buf", type = BufEffect.class), @XmlElement(name = "dot", type = DamageOverTimeEffect.class), @XmlElement(name = "hot", type = HealOverTimeEffect.class), @XmlElement(name = "transform", type = TransformEffect.class), @XmlElement(name = "poison", type = PoisonEffect.class), @XmlElement(name = "stun", type = StunEffect.class), @XmlElement(name = "sleep", type = SleepEffect.class), @XmlElement(name = "bleed", type = BleedEffect.class), @XmlElement(name = "hide", type = HideEffect.class), @XmlElement(name = "search", type = SearchEffect.class), @XmlElement(name = "statup", type = StatupEffect.class), @XmlElement(name = "statdown", type = StatdownEffect.class), @XmlElement(name = "statboost", type = StatboostEffect.class), @XmlElement(name = "weaponstatboost", type = WeaponStatboostEffect.class), @XmlElement(name = "wpnmastery", type = WeaponMasteryEffect.class), @XmlElement(name = "snare", type = SnareEffect.class), @XmlElement(name = "slow", type = SlowEffect.class), @XmlElement(name = "stumble", type = StumbleEffect.class), @XmlElement(name = "spin", type = SpinEffect.class), @XmlElement(name = "stagger", type = StaggerEffect.class), @XmlElement(name = "openaerial", type = OpenAerialEffect.class), @XmlElement(name = "closeaerial", type = CloseAerialEffect.class), @XmlElement(name = "shield", type = ShieldEffect.class), @XmlElement(name = "bind", type = BindEffect.class), @XmlElement(name = "dispel", type = DispelEffect.class), @XmlElement(name = "skillatk", type = SkillAttackEffect.class), @XmlElement(name = "spellatk", type = SpellAttackEffect.class), @XmlElement(name = "dash", type = DashEffect.class), @XmlElement(name = "backdash", type = BackDashEffect.class), @XmlElement(name = "delaydamage", type = DelayDamageEffect.class), @XmlElement(name = "return", type = ReturnEffect.class), @XmlElement(name = "heal", type = HealEffect.class), @XmlElement(name = "healmp", type = HealMpEffect.class), @XmlElement(name = "healdp", type = HealDpEffect.class), @XmlElement(name = "healfp", type = HealFpEffect.class), @XmlElement(name = "itemheal", type = ItemHealEffect.class), @XmlElement(name = "itemhealmp", type = ItemHealMpEffect.class), @XmlElement(name = "itemhealdp", type = ItemHealDpEffect.class), @XmlElement(name = "itemhealfp", type = ItemHealFpEffect.class), @XmlElement(name = "carvesignet", type = CarveSignetEffect.class), @XmlElement(name = "signet", type = SignetEffect.class), @XmlElement(name = "signetburst", type = SignetBurstEffect.class), @XmlElement(name = "silence", type = SilenceEffect.class), @XmlElement(name = "curse", type = CurseEffect.class), @XmlElement(name = "blind", type = BlindEffect.class), @XmlElement(name = "boosthate", type = BoostHateEffect.class), @XmlElement(name = "hostileup", type = HostileUpEffect.class), @XmlElement(name = "paralyze", type = ParalyzeEffect.class), @XmlElement(name = "confuse", type = ConfuseEffect.class), @XmlElement(name = "dispeldebuffphysical", type = DispelDebuffPhysicalEffect.class), @XmlElement(name = "dispeldebuffmental", type = DispelDebuffMentalEffect.class), @XmlElement(name = "dispeldebuff", type = DispelDebuffEffect.class), @XmlElement(name = "alwaysdodge", type = AlwaysDodgeEffect.class), @XmlElement(name = "alwaysparry", type = AlwaysParryEffect.class), @XmlElement(name = "alwaysresist", type = AlwaysResistEffect.class), @XmlElement(name = "alwaysblock", type = AlwaysBlockEffect.class), @XmlElement(name = "mpuseovertime", type = MpUseOverTimeEffect.class), @XmlElement(name = "switchhpmp", type = SwitchHpMpEffect.class), @XmlElement(name = "summon", type = SummonEffect.class), @XmlElement(name = "aura", type = AuraEffect.class), @XmlElement(name = "resurrect", type = ResurrectEffect.class), @XmlElement(name = "returnpoint", type = ReturnPointEffect.class), @XmlElement(name = "provoker", type = ProvokerEffect.class), @XmlElement(name = "reflector", type = ReflectorEffect.class), @XmlElement(name = "spellatkdraininstant", type = SpellAtkDrainInstantEffect.class), @XmlElement(name = "onetimeboostskillattack", type = OneTimeBoostSkillAttackEffect.class), @XmlElement(name = "armormastery", type = ArmorMasteryEffect.class), @XmlElement(name = "weaponstatup", type = WeaponStatupEffect.class), @XmlElement(name = "boostskillcastingtime", type = BoostSkillCastingTimeEffect.class), @XmlElement(name = "summontrap", type = SummonTrapEffect.class), @XmlElement(name = "summonservant", type = SummonServantEffect.class), @XmlElement(name = "skillatkdraininstant", type = SkillAtkDrainInstantEffect.class), @XmlElement(name = "petorderuseultraskill", type = PetOrderUseUltraSkillEffect.class), @XmlElement(name = "boostheal", type = BoostHealEffect.class), @XmlElement(name = "dispelbuff", type = DispelBuffEffect.class), @XmlElement(name = "skilllauncher", type = SkillLauncherEffect.class), @XmlElement(name = "pulled", type = PulledEffect.class), @XmlElement(name = "fear", type = FearEffect.class), @XmlElement(name = "movebehind", type = MoveBehindEffect.class), @XmlElement(name = "rebirth", type = RebirthEffect.class), @XmlElement(name = "changemp", type = ChangeMpConsumptionEffect.class), @XmlElement(name = "magiccounteratk", type = MagicCounterAtkEffect.class)})
  protected List<EffectTemplate> effects;
  @XmlAttribute
  protected boolean food;
  
  public List<EffectTemplate> getEffects() {
    if (this.effects == null) {
      this.effects = new ArrayList<EffectTemplate>();
    }
    return this.effects;
  }




  
  public boolean isFood() {
    return this.food;
  }





  
  public int getEffectsDuration() {
    int duration = 0;
    for (EffectTemplate template : getEffects())
    {
      duration = (duration > template.getDuration()) ? duration : template.getDuration();
    }
    return duration;
  }






  
  public boolean isResurrect() {
    for (EffectTemplate template : getEffects()) {
      
      if (template instanceof ResurrectEffect)
        return true; 
    } 
    return false;
  }





  
  public boolean isItemHealFp() {
    for (EffectTemplate template : getEffects()) {
      
      if (template instanceof ItemHealFpEffect)
        return true; 
    } 
    return false;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\Effects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
