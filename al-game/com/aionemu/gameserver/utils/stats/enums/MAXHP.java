/*    */ package com.aionemu.gameserver.utils.stats.enums;
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
/*    */ public enum MAXHP
/*    */ {
/* 26 */   WARRIOR(1.1688F, 1.1688F, 284.0F),
/* 27 */   GLADIATOR(1.3393F, 48.246F, 342.0F),
/* 28 */   TEMPLAR(1.3288F, 51.878F, 281.0F),
/* 29 */   SCOUT(1.0297F, 40.823F, 219.0F),
/* 30 */   ASSASSIN(1.0488F, 40.38F, 222.0F),
/* 31 */   RANGER(0.5F, 38.5F, 133.0F),
/* 32 */   MAGE(0.7554F, 29.457F, 132.0F),
/* 33 */   SORCERER(0.6352F, 24.852F, 112.0F),
/* 34 */   SPIRIT_MASTER(1.0F, 20.6F, 157.0F),
/* 35 */   PRIEST(1.0303F, 40.824F, 201.0F),
/* 36 */   CLERIC(0.9277F, 35.988F, 229.0F),
/* 37 */   CHANTER(0.9277F, 35.988F, 229.0F);
/*    */   
/*    */   private float a;
/*    */   
/*    */   private float b;
/*    */   private float c;
/*    */   
/*    */   MAXHP(float a, float b, float c) {
/* 45 */     this.a = a;
/* 46 */     this.b = b;
/* 47 */     this.c = c;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMaxHpFor(int level) {
/* 52 */     return Math.round(this.a * (level - 1) * (level - 1) + this.b * (level - 1) + this.c);
/*    */   }
/*    */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\stats\enums\MAXHP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */