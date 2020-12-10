/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.commons.utils.Rnd;
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackStatusObserver;
/*    */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
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
/*    */ @XmlAccessorType(XmlAccessType.FIELD)
/*    */ @XmlType(name = "BlindEffect")
/*    */ public class BlindEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute
/*    */   private int value;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 45 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 51 */     if (calculateEffectResistRate(effect, StatEnum.BLIND_RESISTANCE)) {
/* 52 */       effect.addSucessEffect(this);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 58 */     AttackStatusObserver attackStatusObserver = new AttackStatusObserver(this.value, AttackStatus.DODGE)
/*    */       {
/*    */ 
/*    */         
/*    */         public boolean checkAttackerStatus(AttackStatus status)
/*    */         {
/* 64 */           return (Rnd.get(0, this.value) <= this.value);
/*    */         }
/*    */       };
/*    */     
/* 68 */     effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver)attackStatusObserver);
/* 69 */     effect.setAttackStatusObserver((AttackCalcObserver)attackStatusObserver, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 75 */     AttackCalcObserver acObserver = effect.getAttackStatusObserver(this.position);
/* 76 */     if (acObserver != null)
/* 77 */       effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\BlindEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */