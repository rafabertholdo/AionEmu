/*     */ package com.aionemu.gameserver.services;
/*     */ 
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.ZoneData;
/*     */ import com.aionemu.gameserver.model.TaskId;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.zone.ZoneTemplate;
/*     */ import com.aionemu.gameserver.taskmanager.AbstractFIFOPeriodicTaskManager;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import com.aionemu.gameserver.world.MapRegion;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import com.aionemu.gameserver.world.zone.ZoneInstance;
/*     */ import com.aionemu.gameserver.world.zone.ZoneName;
/*     */ import java.util.Collection;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
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
/*     */ public final class ZoneService
/*     */   extends AbstractFIFOPeriodicTaskManager<Player>
/*     */ {
/*     */   private Map<ZoneName, ZoneInstance> zoneMap;
/*     */   private Map<Integer, Collection<ZoneInstance>> zoneByMapIdMap;
/*     */   private ZoneData zoneData;
/*     */   private static final long DROWN_PERIOD = 2000L;
/*     */   
/*     */   public static final ZoneService getInstance() {
/*  54 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private ZoneService() {
/*  59 */     super(4000);
/*  60 */     this.zoneData = DataManager.ZONE_DATA;
/*  61 */     this.zoneMap = new HashMap<ZoneName, ZoneInstance>();
/*  62 */     this.zoneByMapIdMap = new HashMap<Integer, Collection<ZoneInstance>>();
/*  63 */     initializeZones();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void callTask(Player player) {
/*  70 */     if (player != null) {
/*     */       byte mask;
/*  72 */       while ((mask = player.getController().getZoneUpdateMask()) != 0) {
/*     */         
/*  74 */         for (ZoneUpdateMode mode : VALUES)
/*     */         {
/*  76 */           mode.tryUpdateZone(player, mask);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*  82 */   private static final ZoneUpdateMode[] VALUES = ZoneUpdateMode.values();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum ZoneUpdateMode
/*     */   {
/*  89 */     ZONE_UPDATE
/*     */     {
/*     */       public void zoneTask(Player player)
/*     */       {
/*  93 */         player.getController().updateZoneImpl();
/*  94 */         player.getController().checkWaterLevel();
/*     */       }
/*     */     },
/*  97 */     ZONE_REFRESH
/*     */     {
/*     */       public void zoneTask(Player player)
/*     */       {
/* 101 */         player.getController().refreshZoneImpl();
/*     */       }
/*     */     };
/*     */ 
/*     */     
/*     */     private final byte MASK;
/*     */ 
/*     */     
/*     */     ZoneUpdateMode() {
/* 110 */       this.MASK = (byte)(1 << ordinal());
/*     */     }
/*     */ 
/*     */     
/*     */     public byte mask() {
/* 115 */       return this.MASK;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected final void tryUpdateZone(Player player, byte mask) {
/* 122 */       if ((mask & mask()) == mask()) {
/*     */         
/* 124 */         zoneTask(player);
/* 125 */         player.getController().removeZoneUpdateMask(this);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected abstract void zoneTask(Player param1Player);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initializeZones() {
/* 137 */     Iterator<ZoneTemplate> iterator = this.zoneData.iterator();
/* 138 */     while (iterator.hasNext()) {
/*     */       
/* 140 */       ZoneTemplate template = iterator.next();
/* 141 */       ZoneInstance instance = new ZoneInstance(template);
/* 142 */       this.zoneMap.put(template.getName(), instance);
/*     */       
/* 144 */       Collection<ZoneInstance> zoneListForMap = this.zoneByMapIdMap.get(Integer.valueOf(template.getMapid()));
/* 145 */       if (zoneListForMap == null) {
/*     */         
/* 147 */         zoneListForMap = createZoneSetCollection();
/* 148 */         this.zoneByMapIdMap.put(Integer.valueOf(template.getMapid()), zoneListForMap);
/*     */       } 
/* 150 */       zoneListForMap.add(instance);
/*     */     } 
/*     */     
/* 153 */     for (ZoneInstance zoneInstance : this.zoneMap.values()) {
/*     */       
/* 155 */       ZoneTemplate template = zoneInstance.getTemplate();
/*     */       
/* 157 */       Collection<ZoneInstance> neighbors = createZoneSetCollection();
/* 158 */       for (ZoneName zone : template.getLink())
/*     */       {
/* 160 */         neighbors.add(this.zoneMap.get(zone));
/*     */       }
/* 162 */       zoneInstance.setNeighbors(neighbors);
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
/*     */   private Collection<ZoneInstance> createZoneSetCollection() {
/* 174 */     SortedSet<ZoneInstance> collection = new TreeSet<ZoneInstance>(new Comparator<ZoneInstance>()
/*     */         {
/*     */           
/*     */           public int compare(ZoneInstance o1, ZoneInstance o2)
/*     */           {
/* 179 */             return (o1.getPriority() > o2.getPriority()) ? 1 : -1;
/*     */           }
/*     */         });
/*     */     
/* 183 */     return collection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkZone(Player player) {
/* 193 */     ZoneInstance currentInstance = player.getZoneInstance();
/* 194 */     if (currentInstance == null) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 199 */     Collection<ZoneInstance> neighbors = currentInstance.getNeighbors();
/* 200 */     if (neighbors == null) {
/*     */       return;
/*     */     }
/* 203 */     for (ZoneInstance zone : neighbors) {
/*     */       
/* 205 */       if (checkPointInZone(zone, player.getPosition())) {
/*     */         
/* 207 */         player.setZoneInstance(zone);
/* 208 */         player.getController().onEnterZone(zone);
/* 209 */         player.getController().onLeaveZone(currentInstance);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void findZoneInCurrentMap(Player player) {
/* 220 */     MapRegion mapRegion = player.getActiveRegion();
/* 221 */     if (mapRegion == null) {
/*     */       return;
/*     */     }
/* 224 */     Collection<ZoneInstance> zones = this.zoneByMapIdMap.get(mapRegion.getMapId());
/* 225 */     if (zones == null) {
/*     */       
/* 227 */       player.getController().resetZone();
/*     */       
/*     */       return;
/*     */     } 
/* 231 */     for (ZoneInstance zone : zones) {
/*     */       
/* 233 */       if (checkPointInZone(zone, player.getPosition())) {
/*     */         
/* 235 */         player.setZoneInstance(zone);
/* 236 */         player.getController().onEnterZone(zone);
/*     */         return;
/*     */       } 
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
/*     */   public boolean isInsideZone(Player player, ZoneName zoneName) {
/* 251 */     ZoneInstance zoneInstance = this.zoneMap.get(zoneName);
/* 252 */     if (zoneInstance == null) {
/* 253 */       return false;
/*     */     }
/* 255 */     return checkPointInZone(zoneInstance, player.getPosition());
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
/*     */   private boolean checkPointInZone(ZoneInstance zone, WorldPosition position) {
/* 267 */     int corners = zone.getCorners();
/* 268 */     float[] xCoords = zone.getxCoordinates();
/* 269 */     float[] yCoords = zone.getyCoordinates();
/*     */     
/* 271 */     float top = zone.getTop();
/* 272 */     float bottom = zone.getBottom();
/*     */     
/* 274 */     float x = position.getX();
/* 275 */     float y = position.getY();
/* 276 */     float z = position.getZ();
/*     */ 
/*     */     
/* 279 */     if (top != 0.0F || bottom != 0.0F)
/*     */     {
/* 281 */       if (z > top || z < bottom) {
/* 282 */         return false;
/*     */       }
/*     */     }
/* 285 */     int j = corners - 1;
/* 286 */     boolean inside = false;
/*     */     
/* 288 */     for (int i = 0; i < corners; i++) {
/*     */       
/* 290 */       if ((yCoords[i] < y && yCoords[j] >= y) || (yCoords[j] < y && yCoords[i] >= y))
/*     */       {
/* 292 */         if (xCoords[i] + (y - yCoords[i]) / (yCoords[j] - yCoords[i]) * (xCoords[j] - xCoords[i]) < x)
/*     */         {
/* 294 */           inside = !inside;
/*     */         }
/*     */       }
/* 297 */       j = i;
/*     */     } 
/*     */     
/* 300 */     return inside;
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
/*     */   public void startDrowning(Player player) {
/* 313 */     if (!isDrowning(player)) {
/* 314 */       scheduleDrowningTask(player);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopDrowning(Player player) {
/* 323 */     if (isDrowning(player))
/*     */     {
/* 325 */       player.getController().cancelTask(TaskId.DROWN);
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
/*     */   private boolean isDrowning(Player player) {
/* 337 */     return !(player.getController().getTask(TaskId.DROWN) == null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void scheduleDrowningTask(final Player player) {
/* 346 */     player.getController().addTask(TaskId.DROWN, ThreadPoolManager.getInstance().scheduleAtFixedRate(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 350 */               int value = Math.round((player.getLifeStats().getMaxHp() / 10));
/*     */               
/* 352 */               if (!player.getLifeStats().isAlreadyDead()) {
/*     */                 
/* 354 */                 player.getLifeStats().reduceHp(value, null);
/* 355 */                 player.getLifeStats().sendHpPacketUpdate();
/*     */               }
/*     */               else {
/*     */                 
/* 359 */                 ZoneService.this.stopDrowning(player);
/*     */               } 
/*     */             }
/*     */           }0L, 2000L));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getCalledMethodName() {
/* 372 */     return "zoneService()";
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 378 */     protected static final ZoneService instance = new ZoneService();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\ZoneService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */