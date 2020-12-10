/*     */ package com.aionemu.gameserver.model.gameobjects.player;
/*     */ 
/*     */ import com.aionemu.gameserver.configs.main.PricesConfig;
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
/*     */ public class Prices
/*     */ {
/*     */   public int getGlobalPrices() {
/*  46 */     return PricesConfig.DEFAULT_PRICES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getGlobalPricesModifier() {
/*  55 */     return PricesConfig.DEFAULT_MODIFIER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTaxes() {
/*  64 */     return PricesConfig.DEFAULT_TAXES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVendorBuyModifier() {
/*  73 */     return PricesConfig.VENDOR_BUY_MODIFIER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVendorSellModifier() {
/*  83 */     return (int)(((int)(((int)((PricesConfig.VENDOR_SELL_MODIFIER * getGlobalPrices()) / 100.0F) * getGlobalPricesModifier()) / 100.0F) * getTaxes()) / 100.0F);
/*     */   }
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
/*     */   public long getPriceForService(long basePrice) {
/*  97 */     return (long)(((long)(((long)((basePrice * getGlobalPrices()) / 100.0D) * getGlobalPricesModifier()) / 100.0D) * getTaxes()) / 100.0D);
/*     */   }
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
/*     */   public long getKinahForBuy(long requiredKinah) {
/* 110 */     return (long)(((long)(((long)(((long)((requiredKinah * getVendorBuyModifier()) / 100.0D) * getGlobalPrices()) / 100.0D) * getGlobalPricesModifier()) / 100.0D) * getTaxes()) / 100.0D);
/*     */   }
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
/*     */   public long getKinahForSell(long kinahReward) {
/* 123 */     return (long)((kinahReward * getVendorSellModifier()) / 100.0D);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\player\Prices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */