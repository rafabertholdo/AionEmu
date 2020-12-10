package com.aionemu.gameserver.utils.collections.cachemap;

public interface CacheMap<K, V> {
  void put(K paramK, V paramV);
  
  V get(K paramK);
  
  boolean contains(K paramK);
  
  void remove(K paramK);
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\collections\cachemap\CacheMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */