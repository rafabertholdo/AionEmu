/*     */ package com.aionemu.gameserver.model.gameobjects.stats.modifiers;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.StatModifierPriority;
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
/*     */ 
/*     */ public class RateModifier
/*     */   extends SimpleModifier
/*     */ {
/*     */   public int apply(int baseStat, int currentStat) {
/*  32 */     int minLimit = 0;
/*  33 */     int maxLimit = 0;
/*     */ 
/*     */     
/*  36 */     switch (getStat()) {
/*     */       
/*     */       case ATTACK_SPEED:
/*     */       case SPEED:
/*  40 */         minLimit = 600;
/*  41 */         maxLimit = 12000;
/*     */         break;
/*     */       case FLY_SPEED:
/*  44 */         minLimit = 600;
/*  45 */         maxLimit = 16000;
/*     */         break;
/*     */     } 
/*     */     
/*  49 */     if (isBonus()) {
/*     */       
/*  51 */       int i = Math.round((this.value * baseStat) / 100.0F);
/*  52 */       if (minLimit == 0 && maxLimit == 0) {
/*     */ 
/*     */         
/*  55 */         if (i + currentStat < 0) {
/*  56 */           return -currentStat;
/*     */         }
/*  58 */         return i;
/*     */       } 
/*     */ 
/*     */       
/*  62 */       if (i + currentStat < minLimit) {
/*     */         
/*  64 */         i = currentStat - minLimit;
/*  65 */         return -i;
/*     */       } 
/*  67 */       if (i + currentStat > maxLimit) {
/*     */         
/*  69 */         i = maxLimit - currentStat;
/*  70 */         return i;
/*     */       } 
/*     */       
/*  73 */       return i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  78 */     int chkValue = Math.round(baseStat * (1.0F + this.value / 100.0F));
/*  79 */     if (minLimit == 0 && maxLimit == 0) {
/*     */       
/*  81 */       if (chkValue < 0) {
/*  82 */         return 0;
/*     */       }
/*  84 */       return chkValue;
/*     */     } 
/*     */ 
/*     */     
/*  88 */     if (chkValue + currentStat < minLimit) {
/*     */       
/*  90 */       chkValue = currentStat - minLimit;
/*  91 */       return -chkValue;
/*     */     } 
/*  93 */     if (chkValue + currentStat > maxLimit) {
/*     */       
/*  95 */       chkValue = maxLimit - currentStat;
/*  96 */       return chkValue;
/*     */     } 
/*     */     
/*  99 */     return chkValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatModifierPriority getPriority() {
/* 107 */     return StatModifierPriority.LOW;
/*     */   }
/*     */ 
/*     */   
/*     */   public static RateModifier newInstance(StatEnum stat, int value, boolean isBonus) {
/* 112 */     RateModifier m = new RateModifier();
/* 113 */     m.setStat(stat);
/* 114 */     m.setValue(value);
/* 115 */     m.setBonus(isBonus);
/* 116 */     m.nextId();
/* 117 */     return m;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\RateModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */