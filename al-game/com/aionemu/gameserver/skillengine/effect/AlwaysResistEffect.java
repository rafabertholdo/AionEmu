/*    */ package com.aionemu.gameserver.skillengine.effect;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackStatus;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackCalcObserver;
/*    */ import com.aionemu.gameserver.controllers.movement.AttackStatusObserver;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import javax.xml.bind.annotation.XmlAttribute;
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
/*    */ public class AlwaysResistEffect
/*    */   extends EffectTemplate
/*    */ {
/*    */   @XmlAttribute
/*    */   protected int value;
/*    */   
/*    */   public void applyEffect(Effect effect) {
/* 38 */     effect.addToEffectedController();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void calculate(Effect effect) {
/* 44 */     effect.addSucessEffect(this);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startEffect(Effect effect) {
/* 50 */     AttackStatusObserver attackStatusObserver = new AttackStatusObserver(this.value, AttackStatus.RESIST)
/*    */       {
/*    */ 
/*    */         
/*    */         public boolean checkStatus(AttackStatus status)
/*    */         {
/* 56 */           if (status == AttackStatus.RESIST && this.value > 0) {
/*    */             
/* 58 */             this.value--;
/* 59 */             return true;
/*    */           } 
/* 61 */           return false;
/*    */         }
/*    */       };
/*    */     
/* 65 */     effect.getEffected().getObserveController().addAttackCalcObserver((AttackCalcObserver)attackStatusObserver);
/* 66 */     effect.setAttackStatusObserver((AttackCalcObserver)attackStatusObserver, this.position);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void endEffect(Effect effect) {
/* 72 */     AttackCalcObserver acObserver = effect.getAttackStatusObserver(this.position);
/* 73 */     if (acObserver != null)
/* 74 */       effect.getEffected().getObserveController().removeAttackCalcObserver(acObserver); 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\skillengine\effect\AlwaysResistEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */