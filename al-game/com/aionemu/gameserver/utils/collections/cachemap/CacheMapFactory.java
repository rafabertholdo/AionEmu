package com.aionemu.gameserver.utils.collections.cachemap;

import com.aionemu.gameserver.configs.main.CacheConfig;

public class CacheMapFactory {
  public static <K, V> CacheMap<K, V> createCacheMap(String cacheName, String valueName) {
    if (CacheConfig.SOFT_CACHE_MAP) {
      return createSoftCacheMap(cacheName, valueName);
    }
    return createWeakCacheMap(cacheName, valueName);
  }

  public static <K, V> CacheMap<K, V> createSoftCacheMap(String cacheName, String valueName) {
    return new SoftCacheMap<K, V>(cacheName, valueName);
  }

  public static <K, V> CacheMap<K, V> createWeakCacheMap(String cacheName, String valueName) {
    return new WeakCacheMap<K, V>(cacheName, valueName);
  }
}
