/*     */ package com.aionemu.gameserver.model.templates.spawn;
/*     */ 
/*     */ import com.aionemu.commons.utils.Rnd;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.spawnengine.SpawnHandlerType;
/*     */ import gnu.trove.TIntIntHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlAttribute;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
/*     */ import javax.xml.bind.annotation.XmlType;
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
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ @XmlType(name = "spawn")
/*     */ public class SpawnGroup
/*     */ {
/*     */   @XmlAttribute(name = "time")
/*     */   private SpawnTime spawnTime;
/*     */   @XmlAttribute(name = "anchor")
/*     */   private String anchor;
/*     */   @XmlAttribute(name = "handler")
/*     */   private SpawnHandlerType handler;
/*     */   @XmlAttribute(name = "interval")
/*     */   private int interval;
/*     */   @XmlAttribute(name = "pool")
/*     */   private int pool;
/*     */   @XmlAttribute(name = "npcid")
/*     */   private int npcid;
/*     */   @XmlAttribute(name = "map")
/*     */   private int mapid;
/*     */   @XmlAttribute(name = "rw")
/*     */   private int randomWalk;
/*     */   @XmlElement(name = "object")
/*     */   private List<SpawnTemplate> objects;
/*     */   @XmlTransient
/*  75 */   private TIntIntHashMap lastSpawnedTemplate = new TIntIntHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnGroup() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnGroup(int mapid, int npcid, int interval, int pool) {
/*  96 */     this.mapid = mapid;
/*  97 */     this.npcid = npcid;
/*  98 */     this.interval = interval;
/*  99 */     this.pool = pool;
/*     */   }
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/* 104 */     if (this.objects != null && this.pool > this.objects.size()) {
/*     */       
/* 106 */       Logger.getLogger(SpawnGroup.class).warn("Incorrect pool value for spawn group. MapId:" + this.mapid + " Npc: " + this.npcid);
/*     */       
/* 108 */       this.pool = this.objects.size();
/*     */     } 
/*     */     
/* 111 */     if (this.randomWalk != 0)
/*     */     {
/* 113 */       for (SpawnTemplate spawn : this.objects)
/*     */       {
/* 115 */         spawn.setRandomWalkNr(this.randomWalk);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMapid() {
/* 125 */     return this.mapid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNpcid() {
/* 135 */     return this.npcid;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInterval() {
/* 143 */     return this.interval;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPool() {
/* 151 */     return this.pool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<SpawnTemplate> getObjects() {
/* 159 */     if (this.objects == null) {
/* 160 */       this.objects = new ArrayList<SpawnTemplate>();
/*     */     }
/* 162 */     return this.objects;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnHandlerType getHandler() {
/* 170 */     return this.handler;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getAnchor() {
/* 178 */     return this.anchor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTime getSpawnTime() {
/* 186 */     return this.spawnTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTemplate getNextAvailableTemplate(int instance) {
/* 197 */     for (int i = 0; i < getObjects().size(); ) {
/*     */       
/* 199 */       Integer lastSpawnCounter = Integer.valueOf(this.lastSpawnedTemplate.get(instance));
/* 200 */       int nextSpawnCounter = (lastSpawnCounter == null) ? 0 : (lastSpawnCounter.intValue() + 1);
/*     */       
/* 202 */       if (nextSpawnCounter >= this.objects.size()) {
/* 203 */         nextSpawnCounter = 0;
/*     */       }
/* 205 */       SpawnTemplate nextSpawn = this.objects.get(nextSpawnCounter);
/* 206 */       if (nextSpawn.isSpawned(instance)) {
/*     */         i++; continue;
/*     */       } 
/* 209 */       this.lastSpawnedTemplate.put(instance, nextSpawnCounter);
/* 210 */       return nextSpawn;
/*     */     } 
/* 212 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 217 */     return getObjects().size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTemplate getNextRandomTemplate() {
/* 225 */     return this.objects.get(Rnd.get(0, size() - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetLastSpawnCounter(int instanceIndex) {
/* 235 */     if (this.lastSpawnedTemplate.containsKey(instanceIndex)) {
/* 236 */       this.lastSpawnedTemplate.remove(instanceIndex);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullPool() {
/* 247 */     return (this.pool == this.objects.size());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void exchangeSpawn(VisibleObject visibleObject) {
/* 255 */     if (isFullPool()) {
/*     */       return;
/*     */     }
/* 258 */     int instanceId = visibleObject.getInstanceId();
/* 259 */     SpawnTemplate nextSpawn = getNextAvailableTemplate(instanceId);
/* 260 */     if (nextSpawn != null)
/* 261 */       visibleObject.setSpawn(nextSpawn); 
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\templates\spawn\SpawnGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */