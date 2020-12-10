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
