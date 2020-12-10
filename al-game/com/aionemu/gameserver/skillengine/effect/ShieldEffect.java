/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackShieldObserver;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
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
/*    */ @XmlType(name = "ShieldEffect")
/*    */ public class ShieldEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int hitdelta;
/*    */   @XmlAttribute
/*    */   protected int hitvalue;
/*    */   @XmlAttribute
/*    */   protected boolean percent;
/*    */   @XmlAttribute
/*    */   protected int delta;
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 51 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 57 */     int skillLvl = effect.getSkillLevel();
/* 58 */     int valueWithDelta = this.value + this.delta * skillLvl;
/* 59 */     int hitValueWithDelta = this.hitvalue + this.hitdelta * skillLvl;
/* 60 */     effect.setReserved2(valueWithDelta);
/* 61 */     effect.setReserved3(hitValueWithDelta);
/* 62 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 68 */     AttackShieldObserver asObserver = new AttackShieldObserver(effect.getReserved3(), effect.getReserved2(), this.percent, effect);
/*    */ 
/*    */     
/* 71 */     effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver)asObserver);
/* 72 */     effect.setAttackShieldObserver((AttackCalcObserver)asObserver, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 78 */     AttackCalcObserver acObserver = effect.getAttackShieldObserver(this.position);
/* 79 */     if (acObserver != null)
/* 80 */       effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\ShieldEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */