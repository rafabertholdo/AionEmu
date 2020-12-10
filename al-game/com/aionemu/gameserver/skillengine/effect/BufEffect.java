/*     */ package com.aionemu.gameserver.skillengine.effect;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.SkillEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.id.StatEffectId;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.AddModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.RateModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.SetModifier;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.modifiers.StatModifier;
/*     */ import com.aionemu.gameserver.skillengine.change.Change;
/*     */ import com.aionemu.gameserver.skillengine.change.Func;
/*     */ import com.aionemu.gameserver.skillengine.model.Effect;
/*     */ import java.util.TreeSet;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlType;
/*     */ import org.apache.log4j.Logger;
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
/*     */ @XmlType(name = "BufEffect")
/*     */ public abstract class BufEffect
/*     */   extends EffectTemplate
/*     */ {
/*  44 */   private static final Logger log = Logger.getLogger(BufEffect.class);
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEffect(Effect effect) {
/*  49 */     effect.addToEffectedController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void calculate(Effect effect) {
/*  56 */     effect.addSucessEffect(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void endEffect(Effect effect) {
/*  65 */     Creature effected = effect.getEffected();
/*  66 */     int skillId = effect.getSkillId();
/*  67 */     effected.getGameStats().endEffect((StatEffectId)SkillEffectId.getInstance(skillId, this.effectid, this.position));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startEffect(Effect effect) {
/*  75 */     if (this.change == null) {
/*     */       return;
/*     */     }
/*  78 */     Creature effected = effect.getEffected();
/*  79 */     CreatureGameStats<? extends Creature> cgs = effected.getGameStats();
/*     */     
/*  81 */     TreeSet<StatModifier> modifiers = getModifiers(effect);
/*  82 */     SkillEffectId skillEffectId = getSkillEffectId(effect);
/*     */     
/*  84 */     if (modifiers.size() > 0) {
/*  85 */       cgs.addModifiers((StatEffectId)skillEffectId, modifiers);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SkillEffectId getSkillEffectId(Effect effect) {
/*  95 */     int skillId = effect.getSkillId();
/*  96 */     return SkillEffectId.getInstance(skillId, this.effectid, this.position);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected TreeSet<StatModifier> getModifiers(Effect effect) {
/* 106 */     int skillId = effect.getSkillId();
/* 107 */     int skillLvl = effect.getSkillLevel();
/*     */     
/* 109 */     TreeSet<StatModifier> modifiers = new TreeSet<StatModifier>();
/*     */     
/* 111 */     for (Change changeItem : this.change) {
/*     */       
/* 113 */       if (changeItem.getStat() == null) {
/*     */         
/* 115 */         log.warn("Skill stat has wrong name for skillid: " + skillId);
/*     */         
/*     */         continue;
/*     */       } 
/* 119 */       int valueWithDelta = changeItem.getValue() + changeItem.getDelta() * skillLvl;
/*     */       
/* 121 */       switch (changeItem.getFunc()) {
/*     */         
/*     */         case ADD:
/* 124 */           modifiers.add(AddModifier.newInstance(changeItem.getStat(), valueWithDelta, true));
/*     */         
/*     */         case PERCENT:
/* 127 */           modifiers.add(RateModifier.newInstance(changeItem.getStat(), valueWithDelta, true));
/*     */         
/*     */         case REPLACE:
/* 130 */           modifiers.add(SetModifier.newInstance(changeItem.getStat(), valueWithDelta, true));
/*     */       } 
/*     */     
/*     */     } 
/* 134 */     return modifiers;
/*     */   }
/*     */   
/*     */   public void onPeriodicAction(Effect effect) {}
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\BufEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */