package com.aionemu.gameserver.services;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_WEATHER;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.WorldMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javolution.util.FastMap;

public class WeatherService {
  private final long WEATHER_DURATION = 7200000L;

  private final long CHECK_INTERVAL = 120000L;

  private Map<WeatherKey, Integer> worldWeathers;

  public static final WeatherService getInstance() {
    return SingletonHolder.instance;
  }

  private WeatherService() {
    this.worldWeathers = (Map<WeatherKey, Integer>) new FastMap();
    ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable() {

      public void run() {
        WeatherService.this.checkWeathersTime();
      }
    }, 120000L, 120000L);
  }

  private class WeatherKey {
    private Date created;

    private WorldMap map;

    public WeatherKey(Date date, WorldMap worldMap) {
      this.created = date;
      this.map = worldMap;
    }

    public WorldMap getMap() {
      return this.map;
    }

    public boolean isOutDated() {
      Date now = new Date();
      long nowTime = now.getTime();
      long createdTime = this.created.getTime();
      long delta = nowTime - createdTime;
      return (delta > 7200000L);
    }
  }

  private void checkWeathersTime() {
    List<WeatherKey> toBeRefreshed = new ArrayList<WeatherKey>();
    for (WeatherKey key : this.worldWeathers.keySet()) {

      if (key.isOutDated()) {
        toBeRefreshed.add(key);
      }
    }
    for (WeatherKey key : toBeRefreshed) {

      this.worldWeathers.remove(key);
      onWeatherChange(key.getMap(), null);
    }
  }

  private int getRandomWeather() {
    return Rnd.get(0, 8);
  }

  public void loadWeather(Player player) {
    WorldMap worldMap = player.getActiveRegion().getParent().getParent();
    onWeatherChange(worldMap, player);
  }

  private WeatherKey getKeyFromMapByWorldMap(WorldMap map) {
    for (WeatherKey key : this.worldWeathers.keySet()) {

      if (key.getMap().equals(map)) {
        return key;
      }
    }
    WeatherKey newKey = new WeatherKey(new Date(), map);
    this.worldWeathers.put(newKey, Integer.valueOf(getRandomWeather()));
    return newKey;
  }

  private int getWeatherTypeByRegion(WorldMap worldMap) {
    WeatherKey key = getKeyFromMapByWorldMap(worldMap);
    return ((Integer) this.worldWeathers.get(key)).intValue();
  }

  public void resetWeather() {
    Set<WeatherKey> loadedWeathers = new HashSet<WeatherKey>(this.worldWeathers.keySet());
    this.worldWeathers.clear();
    for (WeatherKey key : loadedWeathers) {
      onWeatherChange(key.getMap(), null);
    }
  }

  public void changeRegionWeather(int regionId, Integer weatherType) {
    WorldMap worldMap = World.getInstance().getWorldMap(regionId);
    WeatherKey key = getKeyFromMapByWorldMap(worldMap);
    this.worldWeathers.put(key, weatherType);
    onWeatherChange(worldMap, null);
  }

  private void onWeatherChange(WorldMap worldMap, Player player) {
    if (player == null) {

      for (Player currentPlayer : World.getInstance().getAllPlayers()) {
        if (!currentPlayer.isSpawned()) {
          continue;
        }
        WorldMap currentPlayerWorldMap = currentPlayer.getActiveRegion().getParent().getParent();
        if (currentPlayerWorldMap.equals(worldMap)) {
          PacketSendUtility.sendPacket(currentPlayer,
              (AionServerPacket) new SM_WEATHER(getWeatherTypeByRegion(currentPlayerWorldMap)));
        }
      }

    } else {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_WEATHER(getWeatherTypeByRegion(worldMap)));
    }
  }

  private static class SingletonHolder {
    protected static final WeatherService instance = new WeatherService();
  }
}
