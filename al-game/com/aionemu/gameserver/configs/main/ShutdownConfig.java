package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class ShutdownConfig {
  @Property(key = "shutdown.mode", defaultValue = "1")
  public static int HOOK_MODE;
  
  @Property(key = "shutdown.delay", defaultValue = "3")
  public static int HOOK_DELAY;
  
  @Property(key = "shutdown.interval", defaultValue = "1")
  public static int ANNOUNCE_INTERVAL;
  
  @Property(key = "shutdown.safereboot", defaultValue = "true")
  public static boolean SAFE_REBOOT;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\main\ShutdownConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */