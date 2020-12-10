/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import com.aionemu.gameserver.skillengine.model.ProvokeTarget;
/*     */ import com.aionemu.gameserver.skillengine.model.ProvokeType;
/*     */ import com.aionemu.gameserver.skillengine.model.SkillTemplate;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "ProvokerEffect")
/*     */ public class ProvokerEffect
/*     */   extends EffectTemplate
/*     */ {
/*     */   @XmlAttribute
/*     */   protected int prob2;
/*     */   @XmlAttribute
/*     */   protected int prob1;
/*     */   @XmlAttribute(name = "provoke_target")
/*     */   protected ProvokeTarget provokeTarget;
/*     */   @XmlAttribute(name = "provoke_type")
/*     */   protected ProvokeType provokeType;
/*     */   @XmlAttribute(name = "skill_id")
/*     */   protected int skillId;
/*     */   
/*     */   public void applyEffect(Effect effect) {
/*  56 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/*  62 */     effect.addSucessEffect(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(Effect effect) {
/*  68 */     ActionObserver observer = null;
/*  69 */     final Creature effector = effect.getEffector();
/*  70 */     switch (this.provokeType) {
/*     */       
/*     */       case ME:
/*  73 */         observer = new ActionObserver(ActionObserver.ObserverType.ATTACK)
/*     */           {
/*     */             
/*     */             public void attack(Creature creature)
/*     */             {
/*  78 */               if (Rnd.get(0, 100) <= ProvokerEffect.this.prob2) {
/*     */                 
/*  80 */                 Creature target = ProvokerEffect.this.getProvokeTarget(ProvokerEffect.this.provokeTarget, effector, creature);
/*  81 */                 ProvokerEffect.this.createProvokedEffect(effector, target);
/*     */               } 
/*     */             }
/*     */           };
/*     */         break;
/*     */       
/*     */       case OPPONENT:
/*  88 */         observer = new ActionObserver(ActionObserver.ObserverType.ATTACKED)
/*     */           {
/*     */             
/*     */             public void attacked(Creature creature)
/*     */             {
/*  93 */               if (Rnd.get(0, 100) <= ProvokerEffect.this.prob2) {
/*     */                 
/*  95 */                 Creature target = ProvokerEffect.this.getProvokeTarget(ProvokerEffect.this.provokeTarget, effector, creature);
/*  96 */                 ProvokerEffect.this.createProvokedEffect(effector, target);
/*     */               } 
/*     */             }
/*     */           };
/*     */         break;
/*     */     } 
/*     */     
/* 103 */     if (observer == null) {
/*     */       return;
/*     */     }
/* 106 */     effect.setActionObserver(observer, this.position);
/* 107 */     effect.getEffected().getObserveController().addObserver(observer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createProvokedEffect(Creature effector, Creature target) {
/* 117 */     SkillTemplate template = DataManager.SKILL_DATA.getSkillTemplate(this.skillId);
/* 118 */     Effect e = new Effect(effector, target, template, template.getLvl(), template.getEffectsDuration());
/* 119 */     e.initialize();
/* 120 */     e.applyEffect();
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
/*     */   private Creature getProvokeTarget(ProvokeTarget provokeTarget, Creature effector, Creature target) {
/* 132 */     switch (provokeTarget) {
/*     */       
/*     */       case ME:
/* 135 */         return effector;
/*     */       case OPPONENT:
/* 137 */         return target;
/*     */     } 
/* 139 */     throw new IllegalArgumentException("Provoker target is invalid " + provokeTarget);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/* 145 */     ActionObserver observer = effect.getActionObserver(this.position);
/* 146 */     if (observer != null)
/* 147 */       effect.getEffected().getObserveController().removeObserver(observer); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ProvokerEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */