package com.aionemu.gameserver.skillengine.model;

import com.aionemu.gameserver.skillengine.action.Actions;
import com.aionemu.gameserver.skillengine.condition.Conditions;
import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
import com.aionemu.gameserver.skillengine.effect.Effects;
import com.aionemu.gameserver.skillengine.properties.Properties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "skillTemplate", propOrder = { "initproperties", "startconditions", "setproperties", "useconditions",
    "effects", "actions" })
public class SkillTemplate {
  protected Properties initproperties;
  protected Conditions startconditions;
  protected Properties setproperties;
  protected Conditions useconditions;
  protected Effects effects;
  protected Actions actions;
  @XmlAttribute(name = "skill_id", required = true)
  protected int skillId;
  @XmlAttribute(required = true)
  protected String name;
  @XmlAttribute(required = true)
  protected int nameId;
  @XmlAttribute
  protected String stack = "NONE";

  @XmlAttribute
  protected int lvl;

  @XmlAttribute(name = "skilltype", required = true)
  protected SkillType type;

  @XmlAttribute(name = "skillsubtype", required = true)
  protected SkillSubType subType;

  @XmlAttribute(name = "tslot")
  protected SkillTargetSlot targetSlot;

  @XmlAttribute(name = "activation", required = true)
  protected ActivationAttribute activationAttribute;
  @XmlAttribute(required = true)
  protected int duration;
  @XmlAttribute(name = "cooldown")
  protected int cooldown;
  @XmlAttribute(name = "penalty_skill_id")
  protected int penaltySkillId;
  @XmlAttribute(name = "pvp_damage")
  protected int pvpDamage;
  @XmlAttribute(name = "pvp_duration")
  protected int pvpDuration;
  @XmlAttribute(name = "chain_skill_prob")
  protected int chainSkillProb;
  @XmlAttribute(name = "cancel_rate")
  protected int cancelRate;
  @XmlAttribute(name = "skillset_exception")
  protected int skillSetException;

  public Properties getInitproperties() {
    return this.initproperties;
  }

  public Properties getSetproperties() {
    return this.setproperties;
  }

  public Conditions getStartconditions() {
    return this.startconditions;
  }

  public Conditions getUseconditions() {
    return this.useconditions;
  }

  public Effects getEffects() {
    return this.effects;
  }

  public Actions getActions() {
    return this.actions;
  }

  public int getSkillId() {
    return this.skillId;
  }

  public String getName() {
    return this.name;
  }

  public int getNameId() {
    return this.nameId;
  }

  public String getStack() {
    return this.stack;
  }

  public int getLvl() {
    return this.lvl;
  }

  public SkillType getType() {
    return this.type;
  }

  public SkillSubType getSubType() {
    return this.subType;
  }

  public SkillTargetSlot getTargetSlot() {
    return this.targetSlot;
  }

  public int getDuration() {
    return this.duration;
  }

  public ActivationAttribute getActivationAttribute() {
    return this.activationAttribute;
  }

  public boolean isPassive() {
    return (this.activationAttribute == ActivationAttribute.PASSIVE);
  }

  public boolean isToggle() {
    return (this.activationAttribute == ActivationAttribute.TOGGLE);
  }

  public boolean isProvoked() {
    return (this.activationAttribute == ActivationAttribute.PROVOKED);
  }

  public boolean isActive() {
    return (this.activationAttribute == ActivationAttribute.ACTIVE);
  }

  public EffectTemplate getEffectTemplate(int position) {
    return (this.effects != null && this.effects.getEffects().size() >= position)
        ? this.effects.getEffects().get(position - 1)
        : null;
  }

  public int getEffectsDuration() {
    return (this.effects != null) ? this.effects.getEffectsDuration() : 0;
  }

  public int getCooldown() {
    return this.cooldown;
  }

  public int getPenaltySkillId() {
    return this.penaltySkillId;
  }

  public int getPvpDamage() {
    return this.pvpDamage;
  }

  public int getPvpDuration() {
    return this.pvpDuration;
  }

  public int getChainSkillProb() {
    return this.chainSkillProb;
  }

  public int getCancelRate() {
    return this.cancelRate;
  }

  public int getSkillSetException() {
    return this.skillSetException;
  }

  public boolean hasResurrectEffect() {
    return (getEffects() != null && getEffects().isResurrect());
  }

  public boolean hasItemHealFpEffect() {
    return (getEffects() != null && getEffects().isItemHealFp());
  }
}
