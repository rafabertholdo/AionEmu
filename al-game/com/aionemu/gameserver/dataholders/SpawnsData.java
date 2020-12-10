/*     */ package com.aionemu.gameserver.dataholders;
/*     */ 
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.spawnengine.DayNightSpawnManager;
/*     */ import gnu.trove.TIntObjectHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.bind.Unmarshaller;
/*     */ import javax.xml.bind.annotation.XmlAccessType;
/*     */ import javax.xml.bind.annotation.XmlAccessorType;
/*     */ import javax.xml.bind.annotation.XmlElement;
/*     */ import javax.xml.bind.annotation.XmlRootElement;
/*     */ import javax.xml.bind.annotation.XmlTransient;
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
/*     */ @XmlRootElement(name = "spawns")
/*     */ @XmlAccessorType(XmlAccessType.FIELD)
/*     */ public class SpawnsData
/*     */ {
/*     */   @XmlElement(name = "spawn")
/*     */   protected List<SpawnGroup> spawnGroups;
/*     */   @XmlTransient
/*  47 */   private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByMapId = new TIntObjectHashMap();
/*     */   
/*     */   @XmlTransient
/*  50 */   private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByNpcID = new TIntObjectHashMap();
/*     */   
/*     */   @XmlTransient
/*  53 */   private TIntObjectHashMap<ArrayList<SpawnGroup>> spawnsByMapIdNew = new TIntObjectHashMap();
/*     */   @XmlTransient
/*  55 */   private int counter = 0;
/*     */ 
/*     */ 
/*     */   
/*     */   void afterUnmarshal(Unmarshaller u, Object parent) {
/*  60 */     this.spawnsByMapId.clear();
/*  61 */     this.spawnsByMapIdNew.clear();
/*  62 */     this.spawnsByNpcID.clear();
/*     */     
/*  64 */     for (SpawnGroup spawnGroup : this.spawnGroups) {
/*     */ 
/*     */       
/*  67 */       for (SpawnTemplate template : spawnGroup.getObjects()) {
/*     */         
/*  69 */         template.setSpawnGroup(spawnGroup);
/*  70 */         if (spawnGroup.getSpawnTime() != null)
/*     */         {
/*  72 */           DayNightSpawnManager.getInstance().addSpawnTemplate(template);
/*     */         }
/*     */       } 
/*     */       
/*  76 */       if (spawnGroup.getSpawnTime() != null)
/*     */         continue; 
/*  78 */       addNewSpawnGroup(spawnGroup, spawnGroup.getMapid(), spawnGroup.getNpcid(), false);
/*  79 */       this.counter += spawnGroup.getObjects().size();
/*     */     } 
/*  81 */     this.spawnGroups = null;
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
/*     */   public SpawnTemplate getFirstSpawnByNpcId(int npcId) {
/*  93 */     List<SpawnGroup> spawnGroups = (List<SpawnGroup>)this.spawnsByNpcID.get(npcId);
/*  94 */     if (spawnGroups == null) {
/*  95 */       return null;
/*     */     }
/*  97 */     for (SpawnGroup spawnGroup : spawnGroups) {
/*     */       
/*  99 */       if (spawnGroup.getObjects() != null)
/*     */       {
/* 101 */         return spawnGroup.getObjects().get(0);
/*     */       }
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SpawnGroup> getSpawnsForWorld(int worldId) {
/* 109 */     return (List<SpawnGroup>)this.spawnsByMapId.get(worldId);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SpawnGroup> getNewSpawnsForWorld(int worldId) {
/* 114 */     return (List<SpawnGroup>)this.spawnsByMapIdNew.get(worldId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 122 */     return this.counter;
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
/*     */   public void addNewSpawnGroup(SpawnGroup spawnGroup, int worldId, int npcId, boolean isNew) {
/* 135 */     ArrayList<SpawnGroup> mapSpawnGroups = (ArrayList<SpawnGroup>)this.spawnsByMapId.get(worldId);
/* 136 */     if (mapSpawnGroups == null) {
/*     */       
/* 138 */       mapSpawnGroups = new ArrayList<SpawnGroup>();
/* 139 */       this.spawnsByMapId.put(worldId, mapSpawnGroups);
/*     */     } 
/* 141 */     mapSpawnGroups.add(spawnGroup);
/*     */ 
/*     */     
/* 144 */     ArrayList<SpawnGroup> npcIdSpawnGroups = (ArrayList<SpawnGroup>)this.spawnsByNpcID.get(npcId);
/* 145 */     if (npcIdSpawnGroups == null) {
/*     */       
/* 147 */       npcIdSpawnGroups = new ArrayList<SpawnGroup>();
/* 148 */       this.spawnsByNpcID.put(npcId, npcIdSpawnGroups);
/*     */     } 
/* 150 */     npcIdSpawnGroups.add(spawnGroup);
/*     */ 
/*     */     
/* 153 */     if (isNew) {
/*     */ 
/*     */       
/* 156 */       ArrayList<SpawnGroup> mapNewSpawnGroups = (ArrayList<SpawnGroup>)this.spawnsByMapIdNew.get(worldId);
/* 157 */       if (mapNewSpawnGroups == null) {
/*     */         
/* 159 */         mapNewSpawnGroups = new ArrayList<SpawnGroup>();
/* 160 */         this.spawnsByMapIdNew.put(worldId, mapNewSpawnGroups);
/*     */       } 
/* 162 */       mapNewSpawnGroups.add(spawnGroup);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSpawn(SpawnTemplate spawn) {
/* 171 */     if (spawn.getSpawnGroup().size() > 1) {
/*     */       
/* 173 */       spawn.getSpawnGroup().getObjects().remove(spawn);
/*     */       
/*     */       return;
/*     */     } 
/* 177 */     List<SpawnGroup> worldSpawns = (List<SpawnGroup>)this.spawnsByMapId.get(spawn.getWorldId());
/* 178 */     if (worldSpawns != null)
/*     */     {
/* 180 */       worldSpawns.remove(spawn.getSpawnGroup());
/*     */     }
/*     */     
/* 183 */     List<SpawnGroup> worldNewSpawns = (List<SpawnGroup>)this.spawnsByMapIdNew.get(spawn.getWorldId());
/* 184 */     if (worldNewSpawns != null)
/*     */     {
/* 186 */       worldNewSpawns.remove(spawn.getSpawnGroup());
/*     */     }
/*     */     
/* 189 */     List<SpawnGroup> spawnsByNpc = (List<SpawnGroup>)this.spawnsByNpcID.get(spawn.getSpawnGroup().getNpcid());
/* 190 */     if (spawnsByNpc != null)
/*     */     {
/* 192 */       spawnsByNpc.remove(spawn.getSpawnGroup());
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
/*     */   public List<SpawnGroup> getSpawnGroups() {
/* 205 */     if (this.spawnGroups == null)
/* 206 */       this.spawnGroups = new ArrayList<SpawnGroup>(); 
/* 207 */     return this.spawnGroups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawns(List<SpawnGroup> spawns) {
/* 217 */     this.spawnGroups = spawns;
/* 218 */     afterUnmarshal(null, null);
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\dataholders\SpawnsData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */