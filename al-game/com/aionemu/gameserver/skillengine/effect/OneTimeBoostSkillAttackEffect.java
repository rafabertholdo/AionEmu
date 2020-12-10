/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.ActionObserver;
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
/*    */ @XmlType(name = "OneTimeBoostSkillAttackEffect")
/*    */ public class OneTimeBoostSkillAttackEffect
/*    */   extends BufEffect
/*    */ {
/*    */   @XmlAttribute
/*    */   private int count;
/*    */   
/*    */   public void startEffect(final Effect effect) {
/* 44 */     super.startEffect(effect);
/*    */     
/* 46 */     final int stopCount = this.count;
/* 47 */     ActionObserver observer = new ActionObserver(ActionObserver.ObserverType.SKILLUSE)
/*    */       {
/* 49 */         private int count = 0;
/*    */ 
/*    */         
/*    */         public void skilluse(Skill skill) {
/* 53 */           if (this.count < stopCount && skill.getSkillTemplate().getType() == SkillType.PHYSICAL) {
/* 54 */             this.count++;
/*    */           }
/* 56 */           if (this.count == stopCount) {
/* 57 */             effect.endEffect();
/*    */           }
/*    */         }
/*    */       };
/* 61 */     effect.getEffected().getObserveController().addObserver(observer);
/* 62 */     effect.setActionObserver(observer, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 68 */     super.endEffect(effect);
/* 69 */     ActionObserver observer = effect.getActionObserver(this.position);
/* 70 */     effect.getEffected().getObserveController().removeObserver(observer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 76 */     super.calculate(effect);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\OneTimeBoostSkillAttackEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */