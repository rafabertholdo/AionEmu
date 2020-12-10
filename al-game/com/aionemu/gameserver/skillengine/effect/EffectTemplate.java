/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.SkillElement;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.skillengine.change.Change;
/*     */ import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifier;
/*     */ import com.aionemu.gameserver.skillengine.effect.modifier.ActionModifiers;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.HopType;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
/*     */ import com.aionemu.gameserver.utils.stats.StatFunctions;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "Effect")
/*     */ public abstract class EffectTemplate
/*     */ {
/*     */   protected ActionModifiers modifiers;
/*     */   protected List<Change> change;
/*     */   @XmlAttribute
/*     */   protected int effectid;
/*     */   @XmlAttribute(required = true)
/*     */   protected int duration;
/*     */   @XmlAttribute(name = "randomtime")
/*     */   protected int randomTime;
/*     */   @XmlAttribute(name = "e")
/*     */   protected int position;
/*     */   @XmlAttribute(name = "basiclvl")
/*     */   protected int basicLvl;
/*     */   @XmlAttribute(name = "element")
/*  61 */   protected SkillElement element = SkillElement.NONE;
/*     */   
/*     */   @XmlElement(name = "subeffect")
/*     */   protected SubEffect subEffect;
/*     */   
/*     */   @XmlAttribute(name = "hoptype")
/*     */   protected HopType hopType;
/*     */   
/*     */   @XmlAttribute(name = "hopa")
/*     */   protected int hopA;
/*     */   
/*     */   @XmlAttribute(name = "hopb")
/*     */   protected int hopB;
/*     */ 
/*     */   
/*     */   public int getDuration() {
/*  77 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRandomTime() {
/*  85 */     return this.randomTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ActionModifiers getModifiers() {
/*  94 */     return this.modifiers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Change> getChange() {
/* 103 */     return this.change;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEffectid() {
/* 111 */     return this.effectid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 119 */     return this.position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getBasicLvl() {
/* 127 */     return this.basicLvl;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SkillElement getElement() {
/* 135 */     return this.element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int applyActionModifiers(Effect effect, int value) {
/* 145 */     if (this.modifiers == null) {
/* 146 */       return value;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 151 */     for (ActionModifier modifier : this.modifiers.getActionModifiers()) {
/*     */       
/* 153 */       if (modifier.check(effect)) {
/* 154 */         return modifier.analyze(effect, value);
/*     */       }
/*     */     } 
/* 157 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void calculate(Effect paramEffect);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract void applyEffect(Effect paramEffect);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(Effect effect) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateSubEffect(Effect effect) {
/* 185 */     if (this.subEffect == null) {
/*     */       return;
/*     */     }
/* 188 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.subEffect.getSkillId());
/* 189 */     int duration = template.getEffectsDuration();
/* 190 */     Effect newEffect = new Effect(effect.getEffector(), effect.getEffected(), template, template.getLvl(), duration);
/* 191 */     newEffect.initialize();
/* 192 */     effect.setSpellStatus(newEffect.getSpellStatus());
/* 193 */     effect.setSubEffect(newEffect);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculateHate(Effect effect) {
/* 204 */     if (this.hopType == null) {
/*     */       return;
/*     */     }
/* 207 */     if (effect.getSuccessEffect().isEmpty()) {
/*     */       return;
/*     */     }
/* 210 */     int currentHate = effect.getEffectHate();
/* 211 */     if (this.hopType != null) {
/*     */       int skillLvl;
/* 213 */       switch (this.hopType) {
/*     */         
/*     */         case DAMAGE:
/* 216 */           currentHate += effect.getReserved1();
/*     */           break;
/*     */         case SKILLLV:
/* 219 */           skillLvl = effect.getSkillLevel();
/* 220 */           currentHate += this.hopB + this.hopA * skillLvl;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 225 */     if (currentHate == 0)
/* 226 */       currentHate = 1; 
/* 227 */     effect.setEffectHate(StatFunctions.calculateHate(effect.getEffector(), currentHate));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startSubEffect(Effect effect) {
/* 236 */     if (this.subEffect == null) {
/*     */       return;
/*     */     }
/* 239 */     effect.getSubEffect().applyEffect();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPeriodicAction(Effect effect) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean calculateEffectResistRate(Effect effect, StatEnum statEnum) {
/* 257 */     int effectPower = 1000;
/*     */ 
/*     */     
/* 260 */     if (statEnum != null) {
/*     */       
/* 262 */       int stat = effect.getEffected().getGameStats().getCurrentStat(statEnum);
/* 263 */       effectPower -= stat;
/*     */     } 
/*     */     
/* 266 */     int attackerLevel = effect.getEffector().getLevel();
/* 267 */     int targetLevel = effect.getEffected().getLevel();
/*     */     
/* 269 */     float multipler = 0.0F;
/* 270 */     int differ = targetLevel - attackerLevel;
/*     */     
/* 272 */     if (differ > 0 && differ < 8) {
/*     */       
/* 274 */       multipler = differ / 10.0F;
/* 275 */       effectPower -= Math.round(effectPower * multipler);
/*     */     }
/* 277 */     else if (differ >= 8) {
/*     */       
/* 279 */       effectPower -= Math.round(effectPower * 0.8F);
/*     */     } 
/* 281 */     if (effect.getEffected() instanceof Npc) {
/*     */       
/* 283 */       float hpGaugeMod = ((Npc)effect.getEffected()).getObjectTemplate().getHpGauge();
/* 284 */       effectPower = (int)(effectPower - 200.0F * (1.0F + hpGaugeMod / 10.0F));
/*     */     } 
/* 286 */     return (Rnd.get() * 1000.0F < effectPower);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\EffectTemplate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */