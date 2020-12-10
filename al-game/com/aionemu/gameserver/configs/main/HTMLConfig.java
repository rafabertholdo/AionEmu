package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class HTMLConfig {
  @Property(key = "html.root", defaultValue = "./data/static_data/HTML/")
  public static String HTML_ROOT;
  
  @Property(key = "html.cache.file", defaultValue = "./html.cache")
  public static String HTML_CACHE_FILE;
  
  @Property(key = "html.encoding", defaultValue = "UTF-8")
  public static String HTML_ENCODING;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\HTMLConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
