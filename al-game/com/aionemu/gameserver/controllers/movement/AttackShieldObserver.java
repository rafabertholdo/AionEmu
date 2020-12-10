/*    */ package com.aionemu.gameserver.controllers.movement;
/*    */ 
/*    */ import com.aionemu.gameserver.controllers.attack.AttackResult;
/*    */ import com.aionemu.gameserver.skillengine.model.Effect;
/*    */ import java.util.List;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AttackShieldObserver
/*    */   extends AttackCalcObserver
/*    */ {
/*    */   private int hit;
/*    */   private int totalHit;
/*    */   private Effect effect;
/*    */   private boolean percent;
/*    */   
/*    */   public AttackShieldObserver(int hit, int totalHit, boolean percent, Effect effect) {
/* 44 */     this.hit = hit;
/* 45 */     this.totalHit = totalHit;
/* 46 */     this.effect = effect;
/* 47 */     this.percent = percent;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void checkShield(List<AttackResult> attackList) {
/* 53 */     for (AttackResult attackResult : attackList) {
/*    */       
/* 55 */       int damage = attackResult.getDamage();
/*    */       
/* 57 */       int absorbedDamage = 0;
/* 58 */       if (this.percent) {
/* 59 */         absorbedDamage = damage * this.hit / 100;
/*    */       } else {
/* 61 */         absorbedDamage = (damage >= this.hit) ? this.hit : damage;
/*    */       } 
/* 63 */       absorbedDamage = (absorbedDamage >= this.totalHit) ? this.totalHit : absorbedDamage;
/* 64 */       this.totalHit -= absorbedDamage;
/*    */       
/* 66 */       if (absorbedDamage > 0)
/* 67 */         attackResult.setShieldType(2); 
/* 68 */       attackResult.setDamage(damage - absorbedDamage);
/*    */       
/* 70 */       if (this.totalHit <= 0) {
/*    */         
/* 72 */         this.effect.endEffect();
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\movement\AttackShieldObserver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */