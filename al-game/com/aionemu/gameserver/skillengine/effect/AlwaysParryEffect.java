/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackStatusObserver;
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
/*    */ @XmlType(name = "AlwaysParryEffect")
/*    */ public class AlwaysParryEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 43 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 49 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 55 */     AttackStatusObserver attackStatusObserver = new AttackStatusObserver(this.value, AttackStatus.PARRY)
/*    */       {
/*    */ 
/*    */         
/*    */         public boolean checkStatus(AttackStatus status)
/*    */         {
/* 61 */           if (status == AttackStatus.PARRY && this.value > 0) {
/*    */             
/* 63 */             this.value--;
/* 64 */             return true;
/*    */           } 
/* 66 */           return false;
/*    */         }
/*    */       };
/*    */     
/* 70 */     effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver)attackStatusObserver);
/* 71 */     effect.setAttackStatusObserver((AttackCalcObserver)attackStatusObserver, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 77 */     AttackCalcObserver acObserver = effect.getAttackStatusObserver(this.position);
/* 78 */     effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AlwaysParryEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */