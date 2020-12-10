package com.aionemu.gameserver.configs.main;

import com.aionemu.commons.configuration.Property;

public class ThreadConfig {
  @Property(key = "thread.basepoolsize", defaultValue = "2")
  public static int BASE_THREAD_POOL_SIZE;
  @Property(key = "thread.threadpercore", defaultValue = "4")
  public static int EXTRA_THREAD_PER_CORE;
  public static int THREAD_POOL_SIZE;

  public static void load() {
    int baseThreadPoolSize = BASE_THREAD_POOL_SIZE;
    int extraThreadPerCore = EXTRA_THREAD_PER_CORE;

    THREAD_POOL_SIZE = baseThreadPoolSize + Runtime.getRuntime().availableProcessors() * extraThreadPerCore;
  }
}
