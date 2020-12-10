/*     */ package com.aionemu.gameserver.skillengine.model;
/*     */ 
/*     */ import com.aionemu.gameserver.skillengine.action.Actions;
/*     */ import com.aionemu.gameserver.skillengine.condition.Conditions;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectTemplate;
/*     */ import com.aionemu.gameserver.skillengine.effect.Effects;
/*     */ import com.aionemu.gameserver.skillengine.properties.Properties;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "skillTemplate", propOrder = {"initproperties", "startconditions", "setproperties", "useconditions", "effects", "actions"})
/*     */ public class SkillTemplate
/*     */ {
/*     */   protected Properties initproperties;
/*     */   protected Conditions startconditions;
/*     */   protected Properties setproperties;
/*     */   protected Conditions useconditions;
/*     */   protected Effects effects;
/*     */   protected Actions actions;
/*     */   @XmlAttribute(name = "skill_id", required = true)
/*     */   protected int skillId;
/*     */   @XmlAttribute(required = true)
/*     */   protected String name;
/*     */   @XmlAttribute(required = true)
/*     */   protected int nameId;
/*     */   @XmlAttribute
/*  59 */   protected String stack = "NONE";
/*     */   
/*     */   @XmlAttribute
/*     */   protected int lvl;
/*     */   
/*     */   @XmlAttribute(name = "skilltype", required = true)
/*     */   protected SkillType type;
/*     */   
/*     */   @XmlAttribute(name = "skillsubtype", required = true)
/*     */   protected SkillSubType subType;
/*     */   
/*     */   @XmlAttribute(name = "tslot")
/*     */   protected SkillTargetSlot targetSlot;
/*     */   
/*     */   @XmlAttribute(name = "activation", required = true)
/*     */   protected ActivationAttribute activationAttribute;
/*     */   @XmlAttribute(required = true)
/*     */   protected int duration;
/*     */   @XmlAttribute(name = "cooldown")
/*     */   protected int cooldown;
/*     */   @XmlAttribute(name = "penalty_skill_id")
/*     */   protected int penaltySkillId;
/*     */   @XmlAttribute(name = "pvp_damage")
/*     */   protected int pvpDamage;
/*     */   @XmlAttribute(name = "pvp_duration")
/*     */   protected int pvpDuration;
/*     */   @XmlAttribute(name = "chain_skill_prob")
/*     */   protected int chainSkillProb;
/*     */   @XmlAttribute(name = "cancel_rate")
/*     */   protected int cancelRate;
/*     */   @XmlAttribute(name = "skillset_exception")
/*     */   protected int skillSetException;
/*     */   
/*     */   public Properties getInitproperties() {
/*  93 */     return this.initproperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Properties getSetproperties() {
/* 101 */     return this.setproperties;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions getStartconditions() {
/* 113 */     return this.startconditions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Conditions getUseconditions() {
/* 125 */     return this.useconditions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Effects getEffects() {
/* 137 */     return this.effects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Actions getActions() {
/* 149 */     return this.actions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillId() {
/* 157 */     return this.skillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 169 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNameId() {
/* 177 */     return this.nameId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStack() {
/* 185 */     return this.stack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLvl() {
/* 193 */     return this.lvl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillType getType() {
/* 205 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillSubType getSubType() {
/* 213 */     return this.subType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillTargetSlot getTargetSlot() {
/* 221 */     return this.targetSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDuration() {
/* 229 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActivationAttribute getActivationAttribute() {
/* 237 */     return this.activationAttribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassive() {
/* 242 */     return (this.activationAttribute == ActivationAttribute.PASSIVE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isToggle() {
/* 247 */     return (this.activationAttribute == ActivationAttribute.TOGGLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isProvoked() {
/* 252 */     return (this.activationAttribute == ActivationAttribute.PROVOKED);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 257 */     return (this.activationAttribute == ActivationAttribute.ACTIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EffectTemplate getEffectTemplate(int position) {
/* 267 */     return (this.effects != null && this.effects.getEffects().size() >= position) ? this.effects.getEffects().get(position - 1) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectsDuration() {
/* 278 */     return (this.effects != null) ? this.effects.getEffectsDuration() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCooldown() {
/* 286 */     return this.cooldown;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPenaltySkillId() {
/* 294 */     return this.penaltySkillId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPvpDamage() {
/* 302 */     return this.pvpDamage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPvpDuration() {
/* 310 */     return this.pvpDuration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getChainSkillProb() {
/* 316 */     return this.chainSkillProb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCancelRate() {
/* 323 */     return this.cancelRate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillSetException() {
/* 330 */     return this.skillSetException;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasResurrectEffect() {
/* 335 */     return (getEffects() != null && getEffects().isResurrect());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasItemHealFpEffect() {
/* 340 */     return (getEffects() != null && getEffects().isItemHealFp());
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\model\SkillTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */