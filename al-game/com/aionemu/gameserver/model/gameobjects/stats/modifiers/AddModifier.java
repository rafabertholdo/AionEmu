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
/*     */ public class AddModifier
/*     */   extends SimpleModifier
/*     */ {
/*     */   public int apply(int stat, int currentStat) {
/*  31 */     int minLimit = 0;
/*  32 */     int maxLimit = 0;
/*     */ 
/*     */     
/*  35 */     switch (getStat()) {
/*     */       
/*     */       case ATTACK_SPEED:
/*     */       case SPEED:
/*  39 */         minLimit = 600;
/*  40 */         maxLimit = 12000;
/*     */         break;
/*     */       case FLY_SPEED:
/*  43 */         minLimit = 600;
/*  44 */         maxLimit = 16000;
/*     */         break;
/*     */     } 
/*     */     
/*  48 */     if (isBonus()) {
/*     */       
/*  50 */       int i = Math.round(this.value);
/*  51 */       if (minLimit == 0 && maxLimit == 0) {
/*     */ 
/*     */         
/*  54 */         if (i + currentStat < 0) {
/*  55 */           return -currentStat;
/*     */         }
/*  57 */         return i;
/*     */       } 
/*     */ 
/*     */       
/*  61 */       if (i + currentStat < minLimit) {
/*     */         
/*  63 */         i = currentStat - minLimit;
/*  64 */         return -i;
/*     */       } 
/*  66 */       if (i + currentStat > maxLimit) {
/*     */         
/*  68 */         i = maxLimit - currentStat;
/*  69 */         return i;
/*     */       } 
/*     */       
/*  72 */       return i;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  77 */     int chkValue = Math.round((stat + this.value));
/*  78 */     if (minLimit == 0 && maxLimit == 0) {
/*     */       
/*  80 */       if (chkValue < 0) {
/*  81 */         return 0;
/*     */       }
/*  83 */       return chkValue;
/*     */     } 
/*     */ 
/*     */     
/*  87 */     if (chkValue + currentStat < minLimit) {
/*     */       
/*  89 */       chkValue = currentStat - minLimit;
/*  90 */       return -chkValue;
/*     */     } 
/*  92 */     if (chkValue + currentStat > maxLimit) {
/*     */       
/*  94 */       chkValue = maxLimit - currentStat;
/*  95 */       return chkValue;
/*     */     } 
/*     */     
/*  98 */     return chkValue;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public StatModifierPriority getPriority() {
/* 106 */     return StatModifierPriority.MEDIUM;
/*     */   }
/*     */ 
/*     */   
/*     */   public static AddModifier newInstance(StatEnum stat, int value, boolean isBonus) {
/* 111 */     AddModifier m = new AddModifier();
/* 112 */     m.setStat(stat);
/* 113 */     m.setValue(value);
/* 114 */     m.setBonus(isBonus);
/* 115 */     m.nextId();
/* 116 */     return m;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\stats\modifiers\AddModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */