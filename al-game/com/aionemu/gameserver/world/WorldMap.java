/*     */ package com.aionemu.gameserver.world;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.WorldMapTemplate;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
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
/*     */ public class WorldMap
/*     */ {
/*     */   private WorldMapTemplate worldMapTemplate;
/*  38 */   private AtomicInteger nextInstanceId = new AtomicInteger(0);
/*     */ 
/*     */ 
/*     */   
/*  42 */   private Map<Integer, WorldMapInstance> instances = (Map<Integer, WorldMapInstance>)(new FastMap()).shared();
/*     */ 
/*     */   
/*     */   private World world;
/*     */ 
/*     */   
/*     */   public WorldMap(WorldMapTemplate worldMapTemplate, World world) {
/*  49 */     this.world = world;
/*  50 */     this.worldMapTemplate = worldMapTemplate;
/*     */     
/*  52 */     if (worldMapTemplate.getTwinCount() != 0) {
/*     */       
/*  54 */       for (int i = 1; i <= worldMapTemplate.getTwinCount(); i++)
/*     */       {
/*  56 */         int nextId = getNextInstanceId();
/*  57 */         addInstance(nextId);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  62 */       int nextId = getNextInstanceId();
/*  63 */       addInstance(nextId);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  74 */     return this.worldMapTemplate.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaterLevel() {
/*  83 */     return this.worldMapTemplate.getWaterLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDeathLevel() {
/*  92 */     return this.worldMapTemplate.getDeathLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldType getWorldType() {
/* 101 */     return this.worldMapTemplate.getWorldType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Integer getMapId() {
/* 111 */     return this.worldMapTemplate.getMapId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInstanceCount() {
/* 120 */     int twinCount = this.worldMapTemplate.getTwinCount();
/* 121 */     return (twinCount > 0) ? twinCount : 1;
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
/*     */   public WorldMapInstance getWorldMapInstance() {
/* 134 */     return getWorldMapInstance(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapInstance getWorldMapInstanceById(int instanceId) {
/* 145 */     if (this.worldMapTemplate.getTwinCount() != 0)
/*     */     {
/* 147 */       if (instanceId > this.worldMapTemplate.getTwinCount())
/*     */       {
/* 149 */         throw new IllegalArgumentException("WorldMapInstance " + this.worldMapTemplate.getMapId() + " has lower instances count than " + instanceId);
/*     */       }
/*     */     }
/* 152 */     return getWorldMapInstance(instanceId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private WorldMapInstance getWorldMapInstance(int instanceId) {
/* 163 */     return this.instances.get(Integer.valueOf(instanceId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeWorldMapInstance(int instanceId) {
/* 173 */     this.instances.remove(Integer.valueOf(instanceId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldMapInstance addInstance(int instanceId) {
/* 184 */     WorldMapInstance worldMapInstance = null;
/* 185 */     if (this.worldMapTemplate.getMapId().intValue() == WorldMapType.RESHANTA.getId()) {
/* 186 */       worldMapInstance = new WorldMapInstance3D(this, instanceId);
/*     */     } else {
/* 188 */       worldMapInstance = new WorldMapInstance(this, instanceId);
/*     */     } 
/* 190 */     this.instances.put(Integer.valueOf(instanceId), worldMapInstance);
/* 191 */     return worldMapInstance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public World getWorld() {
/* 198 */     return this.world;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNextInstanceId() {
/* 206 */     return this.nextInstanceId.incrementAndGet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInstanceType() {
/* 216 */     return this.worldMapTemplate.isInstance();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<WorldMapInstance> getAllWorldMapInstances() {
/* 224 */     return this.instances.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<Integer> getInstanceIds() {
/* 229 */     return this.instances.keySet();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\world\WorldMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */