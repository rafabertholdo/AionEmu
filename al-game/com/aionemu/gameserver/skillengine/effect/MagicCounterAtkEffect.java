/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*    */ import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import com.aionemu.gameserver.skillengine.model.Skill;
/*    */ import com.aionemu.gameserver.skillengine.model.SkillType;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
/*    */ import javax.xml.bind.annotation.XmlType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "MagicCounterAtkEffect")
/*    */ public class MagicCounterAtkEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int percent;
/*    */   @XmlAttribute
/*    */   protected int maxdmg;
/*    */   
/*    */   public void calculate(Effect effect) {
/* 49 */     if (calculateEffectResistRate(effect, null)) {
/* 50 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 56 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 62 */     final Creature effector = effect.getEffector();
/* 63 */     final Creature effected = effect.getEffected();
/* 64 */     final CreatureLifeStats<? extends Creature> cls = effect.getEffected().getLifeStats();
/* 65 */     ActionObserver observer = null;
/*    */     
/* 67 */     observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
/*    */       {
/*    */         
/*    */         public void skilluse(Skill skill)
/*    */         {
/* 72 */           if (skill.getSkillTemplate().getType() == SkillType.MAGICAL)
/*    */           {
/* 74 */             if (cls.getMaxHp() / 100 * MagicCounterAtkEffect.this.percent <= MagicCounterAtkEffect.this.maxdmg) {
/* 75 */               effected.getController().onAttack(effector, effect.getSkillId(), SM_ATTACK_STATUS.TYPE.DAMAGE, cls.getMaxHp() / 100 * MagicCounterAtkEffect.this.percent);
/*    */             } else {
/* 77 */               effected.getController().onAttack(effector, MagicCounterAtkEffect.this.maxdmg);
/*    */             } 
/*    */           }
/*    */         }
/*    */       };
/*    */     
/* 83 */     effect.setActionObserver(observer, this.position);
/* 84 */     effected.getObserveController().addObserver(observer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 90 */     ActionObserver observer = effect.getActionObserver(this.position);
/* 91 */     if (observer != null)
/* 92 */       effect.getEffected().getObserveController().removeObserver(observer); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\MagicCounterAtkEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */