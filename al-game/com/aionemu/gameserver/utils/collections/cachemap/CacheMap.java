package com.aionemu.gameserver.utils.collections.cachemap;

public interface CacheMap<K, V> {
  void put(K paramK, V paramV);

  V get(K paramK);

  boolean contains(K paramK);

  void remove(K paramK);
}
