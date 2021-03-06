package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class SiegeConfig {
  @Property(key = "siege.enabled", defaultValue = "true")
  public static boolean SIEGE_ENABLED;

  @Property(key = "siege.interval", defaultValue = "7200")
  public static int SIEGE_TIMER_INTERVAL;

  @Property(key = "siege.influence.fortress", defaultValue = "10")
  public static int SIEGE_POINTS_FORTRESS;

  @Property(key = "siege.influence.artifact", defaultValue = "1")
  public static int SIEGE_POINTS_ARTIFACT;
}
