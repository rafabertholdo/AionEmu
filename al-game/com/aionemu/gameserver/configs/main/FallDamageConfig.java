package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class FallDamageConfig {
  @Property(key = "fall.damage.active", defaultValue = "true")
  public static boolean ACTIVE_FALL_DAMAGE;

  @Property(key = "fall.damage.percentage", defaultValue = "1.0")
  public static float FALL_DAMAGE_PERCENTAGE;

  @Property(key = "fall.damage.distance.minimum", defaultValue = "10")
  public static int MINIMUM_DISTANCE_DAMAGE;

  @Property(key = "fall.damage.distance.maximum", defaultValue = "50")
  public static int MAXIMUM_DISTANCE_DAMAGE;

  @Property(key = "fall.damage.distance.midair", defaultValue = "200")
  public static int MAXIMUM_DISTANCE_MIDAIR;
}
