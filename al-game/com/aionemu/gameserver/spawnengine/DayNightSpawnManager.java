/*     */ package com.aionemu.gameserver.spawnengine;
/*     */ 
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTime;
/*     */ import com.aionemu.gameserver.utils.gametime.DayTime;
/*     */ import com.aionemu.gameserver.utils.gametime.GameTimeManager;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class DayNightSpawnManager
/*     */ {
/*  38 */   private static Logger log = Logger.getLogger(DayNightSpawnManager.class);
/*     */   
/*     */   private final List<SpawnTemplate> daySpawns;
/*     */   
/*     */   private final List<SpawnTemplate> nightSpawns;
/*     */   private final List<VisibleObject> spawnedObjects;
/*  44 */   private SpawnTime currentSpawnTime = null;
/*     */ 
/*     */   
/*     */   public static final DayNightSpawnManager getInstance() {
/*  48 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private DayNightSpawnManager() {
/*  53 */     this.daySpawns = new ArrayList<SpawnTemplate>();
/*  54 */     this.nightSpawns = new ArrayList<SpawnTemplate>();
/*  55 */     this.spawnedObjects = new ArrayList<VisibleObject>();
/*  56 */     log.info("DayNightSpawnManager: Day/Night handler initialized");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSpawnTemplate(SpawnTemplate spawnTemplate) {
/*  61 */     if (spawnTemplate.getSpawnGroup().getSpawnTime() == SpawnTime.DAY) {
/*  62 */       this.daySpawns.add(spawnTemplate);
/*     */     } else {
/*  64 */       this.nightSpawns.add(spawnTemplate);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void spawnNpcs(List<SpawnTemplate> spawns) {
/*  69 */     for (SpawnTemplate spawnTemplate : spawns) {
/*     */       
/*  71 */       Set<Integer> instanceIds = World.getInstance().getWorldMap(spawnTemplate.getWorldId()).getInstanceIds();
/*  72 */       for (Integer instanceId : instanceIds) {
/*     */         
/*  74 */         VisibleObject object = SpawnEngine.getInstance().spawnObject(spawnTemplate, instanceId.intValue());
/*  75 */         if (object != null) {
/*  76 */           this.spawnedObjects.add(object);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void deleteObjects() {
/*  83 */     for (VisibleObject object : this.spawnedObjects)
/*  84 */       object.getController().delete(); 
/*  85 */     this.spawnedObjects.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyChangeMode() {
/*  90 */     deleteObjects();
/*  91 */     DayTime dayTime = GameTimeManager.getGameTime().getDayTime();
/*  92 */     if (dayTime == DayTime.NIGHT && (this.currentSpawnTime == null || this.currentSpawnTime == SpawnTime.DAY)) {
/*     */       
/*  94 */       spawnNpcs(this.nightSpawns);
/*  95 */       this.currentSpawnTime = SpawnTime.NIGHT;
/*  96 */       log.info("DayNightSpawnManager: " + this.spawnedObjects.size() + " night objects spawned.");
/*     */     
/*     */     }
/*  99 */     else if (dayTime != DayTime.NIGHT && (this.currentSpawnTime == null || this.currentSpawnTime == SpawnTime.NIGHT)) {
/*     */       
/* 101 */       spawnNpcs(this.daySpawns);
/* 102 */       this.currentSpawnTime = SpawnTime.DAY;
/* 103 */       log.info("DayNightSpawnManager: " + this.spawnedObjects.size() + " day objects spawned.");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 110 */     protected static final DayNightSpawnManager instance = new DayNightSpawnManager();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\spawnengine\DayNightSpawnManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */