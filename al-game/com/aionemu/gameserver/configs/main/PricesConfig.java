package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class PricesConfig {
  @Property(key = "prices.default.prices", defaultValue = "100")
  public static int DEFAULT_PRICES;
  
  @Property(key = "prices.default.modifier", defaultValue = "100")
  public static int DEFAULT_MODIFIER;
  
  @Property(key = "prices.default.taxes", defaultValue = "100")
  public static int DEFAULT_TAXES;
  
  @Property(key = "prices.vendor.buymod", defaultValue = "100")
  public static int VENDOR_BUY_MODIFIER;
  
  @Property(key = "prices.vendor.sellmod", defaultValue = "20")
  public static int VENDOR_SELL_MODIFIER;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\PricesConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
