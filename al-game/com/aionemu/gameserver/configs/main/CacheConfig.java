package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class CacheConfig {
  @Property(key = "cache.softcache", defaultValue = "false")
  public static boolean SOFT_CACHE_MAP = false;
  @Property(key = "cache.players", defaultValue = "false")
  public static boolean CACHE_PLAYERS = false;
  @Property(key = "cache.pcd", defaultValue = "false")
  public static boolean CACHE_COMMONDATA = false;
  @Property(key = "cache.accounts", defaultValue = "false")
  public static boolean CACHE_ACCOUNTS = false;
}
