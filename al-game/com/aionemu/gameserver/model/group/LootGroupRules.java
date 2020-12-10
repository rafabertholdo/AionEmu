/*     */ package com.aionemu.gameserver.model.group;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.item.ItemQuality;
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
/*     */ public class LootGroupRules
/*     */ {
/*     */   private LootRuleType lootRule;
/*     */   private LootDistribution autodistribution;
/*     */   private int common_item_above;
/*     */   private int superior_item_above;
/*     */   private int heroic_item_above;
/*     */   private int fabled_item_above;
/*     */   private int ethernal_item_above;
/*     */   private int over_ethernal;
/*     */   private int over_over_ethernal;
/*     */   
/*     */   public LootGroupRules() {
/*  39 */     this.lootRule = LootRuleType.ROUNDROBIN;
/*  40 */     this.autodistribution = LootDistribution.NORMAL;
/*  41 */     this.common_item_above = 0;
/*  42 */     this.superior_item_above = 2;
/*  43 */     this.heroic_item_above = 2;
/*  44 */     this.fabled_item_above = 2;
/*  45 */     this.ethernal_item_above = 2;
/*  46 */     this.over_ethernal = 2;
/*  47 */     this.over_over_ethernal = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LootGroupRules(LootRuleType lootRule, LootDistribution autodistribution, int commonItemAbove, int superiorItemAbove, int heroicItemAbove, int fabledItemAbove, int ethernalItemAbove, int overEthernal, int overOverEthernal) {
/*  54 */     this.lootRule = lootRule;
/*  55 */     this.autodistribution = autodistribution;
/*  56 */     this.common_item_above = commonItemAbove;
/*  57 */     this.superior_item_above = superiorItemAbove;
/*  58 */     this.heroic_item_above = heroicItemAbove;
/*  59 */     this.fabled_item_above = fabledItemAbove;
/*  60 */     this.ethernal_item_above = ethernalItemAbove;
/*  61 */     this.over_ethernal = overEthernal;
/*  62 */     this.over_over_ethernal = overOverEthernal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getQualityRule(ItemQuality quality) {
/*  71 */     switch (quality) {
/*     */       
/*     */       case COMMON:
/*  74 */         return this.common_item_above;
/*     */       case RARE:
/*  76 */         return this.superior_item_above;
/*     */       case LEGEND:
/*  78 */         return this.heroic_item_above;
/*     */       case UNIQUE:
/*  80 */         return this.fabled_item_above;
/*     */       case EPIC:
/*  82 */         return this.ethernal_item_above;
/*     */       case MYTHIC:
/*  84 */         return this.over_ethernal;
/*     */     } 
/*  86 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LootRuleType getLootRule() {
/*  94 */     return this.lootRule;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LootDistribution getAutodistribution() {
/* 102 */     return this.autodistribution;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCommonItemAbove() {
/* 110 */     return this.common_item_above;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSuperiorItemAbove() {
/* 118 */     return this.superior_item_above;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHeroicItemAbove() {
/* 126 */     return this.heroic_item_above;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFabledItemAbove() {
/* 134 */     return this.fabled_item_above;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getEthernalItemAbove() {
/* 142 */     return this.ethernal_item_above;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOverEthernal() {
/* 150 */     return this.over_ethernal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOverOverEthernal() {
/* 158 */     return this.over_over_ethernal;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\group\LootGroupRules.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */