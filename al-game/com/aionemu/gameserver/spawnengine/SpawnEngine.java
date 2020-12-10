/*     */ package com.aionemu.gameserver.spawnengine;
/*     */ 
/*     */ import com.aionemu.gameserver.controllers.ActionitemController;
/*     */ import com.aionemu.gameserver.controllers.BindpointController;
/*     */ import com.aionemu.gameserver.controllers.CreatureController;
/*     */ import com.aionemu.gameserver.controllers.GatherableController;
/*     */ import com.aionemu.gameserver.controllers.KiskController;
/*     */ import com.aionemu.gameserver.controllers.MonsterController;
/*     */ import com.aionemu.gameserver.controllers.NpcController;
/*     */ import com.aionemu.gameserver.controllers.PortalController;
/*     */ import com.aionemu.gameserver.controllers.PostboxController;
/*     */ import com.aionemu.gameserver.controllers.ServantController;
/*     */ import com.aionemu.gameserver.controllers.SummonController;
/*     */ import com.aionemu.gameserver.controllers.effect.EffectController;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.dataholders.NpcData;
/*     */ import com.aionemu.gameserver.model.NpcType;
/*     */ import com.aionemu.gameserver.model.gameobjects.AionObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.Creature;
/*     */ import com.aionemu.gameserver.model.gameobjects.Gatherable;
/*     */ import com.aionemu.gameserver.model.gameobjects.Kisk;
/*     */ import com.aionemu.gameserver.model.gameobjects.Monster;
/*     */ import com.aionemu.gameserver.model.gameobjects.Npc;
/*     */ import com.aionemu.gameserver.model.gameobjects.Servant;
/*     */ import com.aionemu.gameserver.model.gameobjects.Summon;
/*     */ import com.aionemu.gameserver.model.gameobjects.Trap;
/*     */ import com.aionemu.gameserver.model.gameobjects.VisibleObject;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.templates.GatherableTemplate;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.WorldMapTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
/*     */ import com.aionemu.gameserver.utils.idfactory.IDFactory;
/*     */ import com.aionemu.gameserver.world.KnownList;
/*     */ import com.aionemu.gameserver.world.StaticObjectKnownList;
/*     */ import com.aionemu.gameserver.world.World;
/*     */ import java.util.List;
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
/*     */ public class SpawnEngine
/*     */ {
/*  71 */   private static Logger log = Logger.getLogger(SpawnEngine.class);
/*     */ 
/*     */   
/*  74 */   private int npcCounter = 0;
/*     */   
/*  76 */   private int gatherableCounter = 0;
/*     */ 
/*     */   
/*     */   public static final SpawnEngine getInstance() {
/*  80 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */   
/*     */   private SpawnEngine() {
/*  85 */     spawnAll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VisibleObject spawnObject(SpawnTemplate spawn, int instanceIndex) {
/*     */     NpcTemplate npcTemplate;
/*  96 */     VisibleObjectTemplate template = null;
/*  97 */     int objectId = spawn.getSpawnGroup().getNpcid();
/*  98 */     NpcData npcData = DataManager.NPC_DATA;
/*  99 */     if (objectId > 400000 && objectId < 499999) {
/*     */       
/* 101 */       GatherableTemplate gatherableTemplate = DataManager.GATHERABLE_DATA.getGatherableTemplate(objectId);
/* 102 */       if (gatherableTemplate == null)
/* 103 */         return null; 
/* 104 */       this.gatherableCounter++;
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 109 */       npcTemplate = npcData.getNpcTemplate(objectId);
/* 110 */       if (npcTemplate == null)
/* 111 */         return null; 
/* 112 */       this.npcCounter++;
/*     */     } 
/* 114 */     IDFactory iDFactory = IDFactory.getInstance();
/* 115 */     if (npcTemplate instanceof NpcTemplate) {
/*     */       Monster monster; Npc npc1; BindpointController bindPointController;
/* 117 */       NpcType npcType = npcTemplate.getNpcType();
/* 118 */       Npc npc = null;
/*     */       
/* 120 */       switch (npcType) {
/*     */         
/*     */         case RIFT:
/*     */         case STATIC:
/* 124 */           monster = new Monster(iDFactory.nextId(), new MonsterController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */           
/* 126 */           monster.setKnownlist(new KnownList((VisibleObject)monster));
/*     */           break;
/*     */         case null:
/* 129 */           npc1 = new Npc(iDFactory.nextId(), (NpcController)new PostboxController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */           
/* 131 */           npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
/*     */           break;
/*     */         case null:
/* 134 */           bindPointController = new BindpointController();
/* 135 */           bindPointController.setBindPointTemplate(DataManager.BIND_POINT_DATA.getBindPointTemplate(objectId));
/* 136 */           npc1 = new Npc(iDFactory.nextId(), (NpcController)bindPointController, spawn, (VisibleObjectTemplate)npcTemplate);
/* 137 */           npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
/*     */           break;
/*     */         case null:
/* 140 */           npc1 = new Npc(iDFactory.nextId(), (NpcController)new ActionitemController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */           
/* 142 */           npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
/*     */           break;
/*     */         case null:
/* 145 */           npc1 = new Npc(iDFactory.nextId(), (NpcController)new PortalController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */           
/* 147 */           npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
/*     */           break;
/*     */         default:
/* 150 */           npc1 = new Npc(iDFactory.nextId(), new NpcController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */           
/* 152 */           npc1.setKnownlist(new KnownList((VisibleObject)npc1));
/*     */           break;
/*     */       } 
/*     */       
/* 156 */       npc1.setNpcSkillList(DataManager.NPC_SKILL_DATA.getNpcSkillList(npcTemplate.getTemplateId()));
/* 157 */       npc1.setEffectController(new EffectController((Creature)npc1));
/* 158 */       npc1.getController().onRespawn();
/* 159 */       bringIntoWorld((VisibleObject)npc1, spawn, instanceIndex);
/* 160 */       if (npc1.hasWalkRoutes())
/* 161 */         npc1.getAi().schedule(); 
/* 162 */       return (VisibleObject)npc1;
/*     */     } 
/* 164 */     if (npcTemplate instanceof GatherableTemplate) {
/*     */       
/* 166 */       Gatherable gatherable = new Gatherable(spawn, (VisibleObjectTemplate)npcTemplate, iDFactory.nextId(), new GatherableController());
/* 167 */       gatherable.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)gatherable));
/* 168 */       bringIntoWorld((VisibleObject)gatherable, spawn, instanceIndex);
/* 169 */       return (VisibleObject)gatherable;
/*     */     } 
/* 171 */     return null;
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
/*     */   public Trap spawnTrap(SpawnTemplate spawn, int instanceIndex, Creature creator, int skillId) {
/* 183 */     int objectId = spawn.getSpawnGroup().getNpcid();
/* 184 */     NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(objectId);
/* 185 */     Trap trap = new Trap(IDFactory.getInstance().nextId(), new NpcController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */     
/* 187 */     trap.setKnownlist(new KnownList((VisibleObject)trap));
/* 188 */     trap.setEffectController(new EffectController((Creature)trap));
/* 189 */     trap.setCreator(creator);
/* 190 */     trap.setSkillId(skillId);
/* 191 */     trap.getController().onRespawn();
/* 192 */     bringIntoWorld((VisibleObject)trap, spawn, instanceIndex);
/* 193 */     return trap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Kisk spawnKisk(SpawnTemplate spawn, int instanceIndex, Player creator) {
/* 204 */     int npcId = spawn.getSpawnGroup().getNpcid();
/* 205 */     NpcTemplate template = DataManager.NPC_DATA.getNpcTemplate(npcId);
/* 206 */     Kisk kisk = new Kisk(IDFactory.getInstance().nextId(), (NpcController)new KiskController(), spawn, template, creator);
/*     */     
/* 208 */     kisk.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)kisk));
/* 209 */     kisk.setEffectController(new EffectController((Creature)kisk));
/* 210 */     kisk.getController().onRespawn();
/* 211 */     bringIntoWorld((VisibleObject)kisk, spawn, instanceIndex);
/* 212 */     return kisk;
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
/*     */   public Servant spawnServant(SpawnTemplate spawn, int instanceIndex, Creature creator, int skillId, int hpRatio) {
/* 225 */     int objectId = spawn.getSpawnGroup().getNpcid();
/* 226 */     NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(objectId);
/* 227 */     Servant servant = new Servant(IDFactory.getInstance().nextId(), (NpcController)new ServantController(), spawn, (VisibleObjectTemplate)npcTemplate);
/*     */     
/* 229 */     servant.setKnownlist(new KnownList((VisibleObject)servant));
/* 230 */     servant.setEffectController(new EffectController((Creature)servant));
/* 231 */     servant.setCreator(creator);
/* 232 */     servant.setSkillId(skillId);
/* 233 */     servant.setTarget(creator.getTarget());
/* 234 */     servant.setHpRatio(hpRatio);
/* 235 */     servant.getController().onRespawn();
/* 236 */     bringIntoWorld((VisibleObject)servant, spawn, instanceIndex);
/* 237 */     return servant;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Summon spawnSummon(Player creator, int npcId, int skillLvl) {
/* 248 */     float x = creator.getX();
/* 249 */     float y = creator.getY();
/* 250 */     float z = creator.getZ();
/* 251 */     byte heading = creator.getHeading();
/* 252 */     int worldId = creator.getWorldId();
/* 253 */     int instanceId = creator.getInstanceId();
/*     */     
/* 255 */     SpawnTemplate spawn = createSpawnTemplate(worldId, npcId, x, y, z, heading, 0, 0);
/* 256 */     NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(npcId);
/*     */     
/* 258 */     byte level = (byte)(npcTemplate.getLevel() + skillLvl - 1);
/* 259 */     SummonStatsTemplate statsTemplate = DataManager.SUMMON_STATS_DATA.getSummonTemplate(npcId, level);
/* 260 */     Summon summon = new Summon(IDFactory.getInstance().nextId(), (CreatureController)new SummonController(), spawn, (VisibleObjectTemplate)npcTemplate, statsTemplate, level);
/*     */     
/* 262 */     summon.setKnownlist(new KnownList((VisibleObject)summon));
/* 263 */     summon.setEffectController(new EffectController((Creature)summon));
/* 264 */     summon.setMaster(creator);
/*     */     
/* 266 */     bringIntoWorld((VisibleObject)summon, spawn, instanceId);
/* 267 */     return summon;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private SpawnTemplate createSpawnTemplate(int worldId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk) {
/* 285 */     SpawnTemplate spawnTemplate = new SpawnTemplate(x, y, z, heading, walkerid, randomwalk, 0);
/*     */     
/* 287 */     SpawnGroup spawnGroup = new SpawnGroup(worldId, objectId, 295, 1);
/* 288 */     spawnTemplate.setSpawnGroup(spawnGroup);
/* 289 */     spawnGroup.getObjects().add(spawnTemplate);
/*     */     
/* 291 */     return spawnTemplate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTemplate addNewSpawn(int worldId, int instanceId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk, boolean noRespawn) {
/* 312 */     return addNewSpawn(worldId, instanceId, objectId, x, y, z, heading, walkerid, randomwalk, noRespawn, false);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SpawnTemplate addNewSpawn(int worldId, int instanceId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk, boolean noRespawn, boolean isNewSpawn) {
/* 334 */     SpawnTemplate spawnTemplate = createSpawnTemplate(worldId, objectId, x, y, z, heading, walkerid, randomwalk);
/*     */     
/* 336 */     if (spawnTemplate == null) {
/*     */       
/* 338 */       log.warn("Object couldn't be spawned");
/* 339 */       return null;
/*     */     } 
/*     */     
/* 342 */     if (!noRespawn)
/*     */     {
/* 344 */       DataManager.SPAWNS_DATA.addNewSpawnGroup(spawnTemplate.getSpawnGroup(), worldId, objectId, isNewSpawn);
/*     */     }
/*     */     
/* 347 */     spawnTemplate.setNoRespawn(noRespawn, instanceId);
/*     */     
/* 349 */     return spawnTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   private void bringIntoWorld(VisibleObject visibleObject, SpawnTemplate spawn, int instanceIndex) {
/* 354 */     World world = World.getInstance();
/* 355 */     world.storeObject((AionObject)visibleObject);
/* 356 */     world.setPosition(visibleObject, spawn.getWorldId(), instanceIndex, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getHeading());
/*     */     
/* 358 */     world.spawn(visibleObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnAll() {
/* 366 */     this.npcCounter = 0;
/* 367 */     this.gatherableCounter = 0;
/*     */     
/* 369 */     for (WorldMapTemplate worldMapTemplate : DataManager.WORLD_MAPS_DATA) {
/*     */       
/* 371 */       if (worldMapTemplate.isInstance())
/*     */         continue; 
/* 373 */       int maxTwin = worldMapTemplate.getTwinCount();
/* 374 */       int mapId = worldMapTemplate.getMapId().intValue();
/* 375 */       int numberToSpawn = (maxTwin > 0) ? maxTwin : 1;
/*     */       
/* 377 */       for (int i = 1; i <= numberToSpawn; i++)
/*     */       {
/* 379 */         spawnInstance(mapId, i);
/*     */       }
/*     */     } 
/*     */     
/* 383 */     log.info("Loaded " + this.npcCounter + " npc spawns");
/* 384 */     log.info("Loaded " + this.gatherableCounter + " gatherable spawns");
/*     */     
/* 386 */     RiftSpawnManager.startRiftPool();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnInstance(int worldId, int instanceIndex) {
/* 396 */     List<SpawnGroup> worldSpawns = DataManager.SPAWNS_DATA.getSpawnsForWorld(worldId);
/*     */     
/* 398 */     if (worldSpawns == null || worldSpawns.size() == 0) {
/*     */       return;
/*     */     }
/* 401 */     int instanceSpawnCounter = 0;
/* 402 */     for (SpawnGroup spawnGroup : worldSpawns) {
/*     */       
/* 404 */       spawnGroup.resetLastSpawnCounter(instanceIndex);
/* 405 */       if (spawnGroup.getHandler() == null) {
/*     */         
/* 407 */         int pool = spawnGroup.getPool();
/* 408 */         for (int i = 0; i < pool; i++) {
/*     */           
/* 410 */           spawnObject(spawnGroup.getNextAvailableTemplate(instanceIndex), instanceIndex);
/*     */           
/* 412 */           instanceSpawnCounter++;
/*     */         } 
/*     */         
/*     */         continue;
/*     */       } 
/* 417 */       switch (spawnGroup.getHandler()) {
/*     */         
/*     */         case RIFT:
/* 420 */           RiftSpawnManager.addRiftSpawnGroup(spawnGroup);
/*     */         
/*     */         case STATIC:
/* 423 */           StaticObjectSpawnManager.spawnGroup(spawnGroup, instanceIndex);
/*     */       } 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 429 */     log.info("Spawned " + worldId + " [" + instanceIndex + "] : " + instanceSpawnCounter);
/*     */   }
/*     */ 
/*     */   
/*     */   private static class SingletonHolder
/*     */   {
/* 435 */     protected static final SpawnEngine instance = new SpawnEngine();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\spawnengine\SpawnEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */