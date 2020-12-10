package com.aionemu.gameserver.model.gameobjects.player;

import com.aionemu.gameserver.configs.main.PricesConfig;

public class Prices {
  public int getGlobalPrices() {
    return PricesConfig.DEFAULT_PRICES;
  }

  public int getGlobalPricesModifier() {
    return PricesConfig.DEFAULT_MODIFIER;
  }

  public int getTaxes() {
    return PricesConfig.DEFAULT_TAXES;
  }

  public int getVendorBuyModifier() {
    return PricesConfig.VENDOR_BUY_MODIFIER;
  }

  public int getVendorSellModifier() {
    return (int) (((int) (((int) ((PricesConfig.VENDOR_SELL_MODIFIER * getGlobalPrices()) / 100.0F)
        * getGlobalPricesModifier()) / 100.0F) * getTaxes()) / 100.0F);
  }

  public long getPriceForService(long basePrice) {
    return (long) (((long) (((long) ((basePrice * getGlobalPrices()) / 100.0D) * getGlobalPricesModifier()) / 100.0D)
        * getTaxes()) / 100.0D);
  }

  public long getKinahForBuy(long requiredKinah) {
    return (long) (((long) (((long) (((long) ((requiredKinah * getVendorBuyModifier()) / 100.0D) * getGlobalPrices())
        / 100.0D) * getGlobalPricesModifier()) / 100.0D) * getTaxes()) / 100.0D);
  }

  public long getKinahForSell(long kinahReward) {
    return (long) ((kinahReward * getVendorSellModifier()) / 100.0D);
  }
}
