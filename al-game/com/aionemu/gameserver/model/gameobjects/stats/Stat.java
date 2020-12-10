/*     */ package com.aionemu.gameserver.model.gameobjects.stats;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Stat
/*     */ {
/*     */   private StatEnum type;
/*     */   private int origin;
/*     */   private int base;
/*     */   private int bonus;
/*     */   private int old;
/*     */   
/*     */   public Stat(StatEnum type, int origin) {
/*  33 */     this.type = type;
/*  34 */     this.origin = origin;
/*  35 */     this.base = origin;
/*  36 */     this.bonus = 0;
/*  37 */     this.old = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public Stat(StatEnum type) {
/*  42 */     this(type, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public StatEnum getType() {
/*  47 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public void increase(int amount, boolean bonus) {
/*  52 */     if (bonus) {
/*     */       
/*  54 */       this.bonus += amount;
/*     */     }
/*     */     else {
/*     */       
/*  58 */       this.base = amount;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void set(int value, boolean bonus) {
/*  64 */     if (bonus) {
/*     */       
/*  66 */       this.bonus = value;
/*     */     }
/*     */     else {
/*     */       
/*  70 */       this.base = value;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOrigin() {
/*  76 */     return this.origin;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBase() {
/*  81 */     return this.base;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBonus() {
/*  86 */     return this.bonus;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrent() {
/*  91 */     return this.base + this.bonus;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOld() {
/*  96 */     return this.old;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reset() {
/* 101 */     this.old = this.base + this.bonus;
/* 102 */     this.base = this.origin;
/* 103 */     this.bonus = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 109 */     String s = this.type + ":" + this.base + "+" + this.bonus;
/* 110 */     return s;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\Stat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */