package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class OptionsConfig {
  @Property(key = "detectors.deadlock.enabled", defaultValue = "true")
  public static boolean DEADLOCK_DETECTOR_ENABLED;

  @Property(key = "detectors.deadlock.interval", defaultValue = "300")
  public static int DEADLOCK_DETECTOR_INTERVAL;

  @Property(key = "player.tasks.general", defaultValue = "900")
  public static int PLAYER_GENERAL;

  @Property(key = "tasks.items", defaultValue = "60")
  public static int ITEMS;

  @Property(key = "world.region.size", defaultValue = "500")
  public static int REGION_SIZE;

  @Property(key = "world.region.maxsize", defaultValue = "10000")
  public static int MAX_WORLD_SIZE;

  @Property(key = "log.audit", defaultValue = "true")
  public static boolean LOG_AUDIT;

  @Property(key = "log.chat", defaultValue = "true")
  public static boolean LOG_CHAT;

  @Property(key = "log.gmaudit", defaultValue = "true")
  public static boolean LOG_GMAUDIT;

  @Property(key = "log.item", defaultValue = "true")
  public static boolean LOG_ITEM;

  @Property(key = "log.packets", defaultValue = "true")
  public static boolean LOG_PACKETS;
}
