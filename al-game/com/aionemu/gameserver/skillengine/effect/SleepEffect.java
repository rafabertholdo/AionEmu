/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
/*    */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import javax.xml.bind.annotation.XmlAccessType;
/*    */ import javax.xml.bind.annotation.XmlAccessorType;
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
/*    */ @XmlType(name = "SleepEffect")
/*    */ public class SleepEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   public void applyEffect(Effect effect) {
/* 40 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 46 */     if (calculateEffectResistRate(effect, StatEnum.SLEEP_RESISTANCE)) {
/* 47 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 53 */     final Creature effected = effect.getEffected();
/* 54 */     effected.getController().cancelCurrentSkill();
/* 55 */     effected.getEffectController().setAbnormal(EffectId.SLEEP.getEffectId());
/*    */     
/* 57 */     effected.getObserveController().attach(new ActionObserver(ActionObserver.ObserverType.ATTACKED)
/*    */         {
/*    */ 
/*    */           
/*    */           public void attacked(Creature creature)
/*    */           {
/* 63 */             effected.getEffectController().removeEffect(effect.getSkillId());
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 72 */     effect.getEffected().getEffectController().unsetAbnormal(EffectId.SLEEP.getEffectId());
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\SleepEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */