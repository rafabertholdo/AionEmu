/*    */ package com.aionemu.gameserver.controllers.attack;
/*    */ 
/*    */ import com.aionemu.gameserver.model.gameobjects.AionObject;
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
/*    */ public class AggroInfo
/*    */ {
/*    */   private AionObject attacker;
/*    */   private int hate;
/*    */   private int damage;
/*    */   
/*    */   AggroInfo(AionObject attacker) {
/* 38 */     this.attacker = attacker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AionObject getAttacker() {
/* 46 */     return this.attacker;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addDamage(int damage) {
/* 54 */     this.damage += damage;
/* 55 */     if (this.damage < 0) {
/* 56 */       this.damage = 0;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addHate(int damage) {
/* 64 */     this.hate += damage;
/* 65 */     if (this.hate < 1) {
/* 66 */       this.hate = 1;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getHate() {
/* 74 */     return this.hate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHate(int hate) {
/* 82 */     this.hate = hate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDamage() {
/* 90 */     return this.damage;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setDamage(int damage) {
/* 98 */     this.damage = damage;
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\controllers\attack\AggroInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */