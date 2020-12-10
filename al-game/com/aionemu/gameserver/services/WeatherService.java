/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_WEATHER;
/*     */ import com.aionemu.gameserver.utils.PacketSendUtility;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import com.aionemu.gameserver.world.WorldMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javolution.util.FastMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WeatherService
/*     */ {
/*  47 */   private final long WEATHER_DURATION = 7200000L;
/*     */   
/*  49 */   private final long CHECK_INTERVAL = 120000L;
/*     */   
/*     */   private Map<WeatherKey, Integer> worldWeathers;
/*     */ 
/*     */   
/*     */   public static final WeatherService getInstance() {
/*  55 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private WeatherService() {
/*  60 */     this.worldWeathers = (Map<WeatherKey, Integer>)new FastMap();
/*  61 */     ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void run()
/*     */           {
/*  69 */             WeatherService.this.checkWeathersTime();
/*     */           }
/*     */         },  120000L, 120000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class WeatherKey
/*     */   {
/*     */     private Date created;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private WorldMap map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WeatherKey(Date date, WorldMap worldMap) {
/* 101 */       this.created = date;
/* 102 */       this.map = worldMap;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public WorldMap getMap() {
/* 110 */       return this.map;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isOutDated() {
/* 121 */       Date now = new Date();
/* 122 */       long nowTime = now.getTime();
/* 123 */       long createdTime = this.created.getTime();
/* 124 */       long delta = nowTime - createdTime;
/* 125 */       return (delta > 7200000L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkWeathersTime() {
/* 135 */     List<WeatherKey> toBeRefreshed = new ArrayList<WeatherKey>();
/* 136 */     for (WeatherKey key : this.worldWeathers.keySet()) {
/*     */       
/* 138 */       if (key.isOutDated())
/*     */       {
/* 140 */         toBeRefreshed.add(key);
/*     */       }
/*     */     } 
/* 143 */     for (WeatherKey key : toBeRefreshed) {
/*     */       
/* 145 */       this.worldWeathers.remove(key);
/* 146 */       onWeatherChange(key.getMap(), null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getRandomWeather() {
/* 155 */     return Rnd.get(0, 8);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadWeather(Player player) {
/* 165 */     WorldMap worldMap = player.getActiveRegion().getParent().getParent();
/* 166 */     onWeatherChange(worldMap, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WeatherKey getKeyFromMapByWorldMap(WorldMap map) {
/* 177 */     for (WeatherKey key : this.worldWeathers.keySet()) {
/*     */       
/* 179 */       if (key.getMap().equals(map))
/*     */       {
/* 181 */         return key;
/*     */       }
/*     */     } 
/* 184 */     WeatherKey newKey = new WeatherKey(new Date(), map);
/* 185 */     this.worldWeathers.put(newKey, Integer.valueOf(getRandomWeather()));
/* 186 */     return newKey;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getWeatherTypeByRegion(WorldMap worldMap) {
/* 195 */     WeatherKey key = getKeyFromMapByWorldMap(worldMap);
/* 196 */     return ((Integer)this.worldWeathers.get(key)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetWeather() {
/* 204 */     Set<WeatherKey> loadedWeathers = new HashSet<WeatherKey>(this.worldWeathers.keySet());
/* 205 */     this.worldWeathers.clear();
/* 206 */     for (WeatherKey key : loadedWeathers)
/*     */     {
/* 208 */       onWeatherChange(key.getMap(), null);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeRegionWeather(int regionId, Integer weatherType) {
/* 222 */     WorldMap worldMap = World.getInstance().getWorldMap(regionId);
/* 223 */     WeatherKey key = getKeyFromMapByWorldMap(worldMap);
/* 224 */     this.worldWeathers.put(key, weatherType);
/* 225 */     onWeatherChange(worldMap, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void onWeatherChange(WorldMap worldMap, Player player) {
/* 239 */     if (player == null) {
/*     */       
/* 241 */       for (Player currentPlayer : World.getInstance().getAllPlayers())
/*     */       {
/* 243 */         if (!currentPlayer.isSpawned()) {
/*     */           continue;
/*     */         }
/* 246 */         WorldMap currentPlayerWorldMap = currentPlayer.getActiveRegion().getParent().getParent();
/* 247 */         if (currentPlayerWorldMap.equals(worldMap))
/*     */         {
/* 249 */           PacketSendUtility.sendPacket(currentPlayer, (AionServerPacket)new SM_WEATHER(getWeatherTypeByRegion(currentPlayerWorldMap)));
/*     */         }
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 256 */       PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_WEATHER(getWeatherTypeByRegion(worldMap)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 263 */     protected static final WeatherService instance = new WeatherService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\WeatherService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */