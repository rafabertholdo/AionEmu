/*    */ package com.aionemu.gameserver.controllers.attack;
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
/*    */ public class AttackResult
/*    */ {
/*    */   private int damage;
/*    */   private AttackStatus attackStatus;
/*    */   private int shieldType;
/*    */   
/*    */   public AttackResult(int damage, AttackStatus attackStatus) {
/* 33 */     this.damage = damage;
/* 34 */     this.attackStatus = attackStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDamage() {
/* 42 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDamage(int damage) {
/* 50 */     this.damage = damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AttackStatus getAttackStatus() {
/* 58 */     return this.attackStatus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getShieldType() {
/* 66 */     return this.shieldType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setShieldType(int shieldType) {
/* 74 */     this.shieldType = shieldType;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AttackResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */