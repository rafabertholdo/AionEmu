package com.aionemu.gameserver.spawnengine;

import com.aionemu.gameserver.controllers.ActionitemController;
import com.aionemu.gameserver.controllers.BindpointController;
import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.GatherableController;
import com.aionemu.gameserver.controllers.KiskController;
import com.aionemu.gameserver.controllers.MonsterController;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.controllers.PortalController;
import com.aionemu.gameserver.controllers.PostboxController;
import com.aionemu.gameserver.controllers.ServantController;
import com.aionemu.gameserver.controllers.SummonController;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.NpcData;
import com.aionemu.gameserver.model.NpcType;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Gatherable;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Monster;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Servant;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.Trap;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.GatherableTemplate;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.WorldMapTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnGroup;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.stats.SummonStatsTemplate;
import com.aionemu.gameserver.utils.idfactory.IDFactory;
import com.aionemu.gameserver.world.KnownList;
import com.aionemu.gameserver.world.StaticObjectKnownList;
import com.aionemu.gameserver.world.World;
import java.util.List;
import org.apache.log4j.Logger;



























public class SpawnEngine
{
  private static Logger log = Logger.getLogger(SpawnEngine.class);

  
  private int npcCounter = 0;
  
  private int gatherableCounter = 0;

  
  public static final SpawnEngine getInstance() {
    return SingletonHolder.instance;
  }

  
  private SpawnEngine() {
    spawnAll();
  }






  
  public VisibleObject spawnObject(SpawnTemplate spawn, int instanceIndex) {
    NpcTemplate npcTemplate;
    VisibleObjectTemplate template = null;
    int objectId = spawn.getSpawnGroup().getNpcid();
    NpcData npcData = DataManager.NPC_DATA;
    if (objectId > 400000 && objectId < 499999) {
      
      GatherableTemplate gatherableTemplate = DataManager.GATHERABLE_DATA.getGatherableTemplate(objectId);
      if (gatherableTemplate == null)
        return null; 
      this.gatherableCounter++;
    
    }
    else {
      
      npcTemplate = npcData.getNpcTemplate(objectId);
      if (npcTemplate == null)
        return null; 
      this.npcCounter++;
    } 
    IDFactory iDFactory = IDFactory.getInstance();
    if (npcTemplate instanceof NpcTemplate) {
      Monster monster; Npc npc1; BindpointController bindPointController;
      NpcType npcType = npcTemplate.getNpcType();
      Npc npc = null;
      
      switch (npcType) {
        
        case RIFT:
        case STATIC:
          monster = new Monster(iDFactory.nextId(), new MonsterController(), spawn, (VisibleObjectTemplate)npcTemplate);
          
          monster.setKnownlist(new KnownList((VisibleObject)monster));
          break;
        case null:
          npc1 = new Npc(iDFactory.nextId(), (NpcController)new PostboxController(), spawn, (VisibleObjectTemplate)npcTemplate);
          
          npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
          break;
        case null:
          bindPointController = new BindpointController();
          bindPointController.setBindPointTemplate(DataManager.BIND_POINT_DATA.getBindPointTemplate(objectId));
          npc1 = new Npc(iDFactory.nextId(), (NpcController)bindPointController, spawn, (VisibleObjectTemplate)npcTemplate);
          npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
          break;
        case null:
          npc1 = new Npc(iDFactory.nextId(), (NpcController)new ActionitemController(), spawn, (VisibleObjectTemplate)npcTemplate);
          
          npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
          break;
        case null:
          npc1 = new Npc(iDFactory.nextId(), (NpcController)new PortalController(), spawn, (VisibleObjectTemplate)npcTemplate);
          
          npc1.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)npc1));
          break;
        default:
          npc1 = new Npc(iDFactory.nextId(), new NpcController(), spawn, (VisibleObjectTemplate)npcTemplate);
          
          npc1.setKnownlist(new KnownList((VisibleObject)npc1));
          break;
      } 
      
      npc1.setNpcSkillList(DataManager.NPC_SKILL_DATA.getNpcSkillList(npcTemplate.getTemplateId()));
      npc1.setEffectController(new EffectController((Creature)npc1));
      npc1.getController().onRespawn();
      bringIntoWorld((VisibleObject)npc1, spawn, instanceIndex);
      if (npc1.hasWalkRoutes())
        npc1.getAi().schedule(); 
      return (VisibleObject)npc1;
    } 
    if (npcTemplate instanceof GatherableTemplate) {
      
      Gatherable gatherable = new Gatherable(spawn, (VisibleObjectTemplate)npcTemplate, iDFactory.nextId(), new GatherableController());
      gatherable.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)gatherable));
      bringIntoWorld((VisibleObject)gatherable, spawn, instanceIndex);
      return (VisibleObject)gatherable;
    } 
    return null;
  }








  
  public Trap spawnTrap(SpawnTemplate spawn, int instanceIndex, Creature creator, int skillId) {
    int objectId = spawn.getSpawnGroup().getNpcid();
    NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(objectId);
    Trap trap = new Trap(IDFactory.getInstance().nextId(), new NpcController(), spawn, (VisibleObjectTemplate)npcTemplate);
    
    trap.setKnownlist(new KnownList((VisibleObject)trap));
    trap.setEffectController(new EffectController((Creature)trap));
    trap.setCreator(creator);
    trap.setSkillId(skillId);
    trap.getController().onRespawn();
    bringIntoWorld((VisibleObject)trap, spawn, instanceIndex);
    return trap;
  }







  
  public Kisk spawnKisk(SpawnTemplate spawn, int instanceIndex, Player creator) {
    int npcId = spawn.getSpawnGroup().getNpcid();
    NpcTemplate template = DataManager.NPC_DATA.getNpcTemplate(npcId);
    Kisk kisk = new Kisk(IDFactory.getInstance().nextId(), (NpcController)new KiskController(), spawn, template, creator);
    
    kisk.setKnownlist((KnownList)new StaticObjectKnownList((VisibleObject)kisk));
    kisk.setEffectController(new EffectController((Creature)kisk));
    kisk.getController().onRespawn();
    bringIntoWorld((VisibleObject)kisk, spawn, instanceIndex);
    return kisk;
  }









  
  public Servant spawnServant(SpawnTemplate spawn, int instanceIndex, Creature creator, int skillId, int hpRatio) {
    int objectId = spawn.getSpawnGroup().getNpcid();
    NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(objectId);
    Servant servant = new Servant(IDFactory.getInstance().nextId(), (NpcController)new ServantController(), spawn, (VisibleObjectTemplate)npcTemplate);
    
    servant.setKnownlist(new KnownList((VisibleObject)servant));
    servant.setEffectController(new EffectController((Creature)servant));
    servant.setCreator(creator);
    servant.setSkillId(skillId);
    servant.setTarget(creator.getTarget());
    servant.setHpRatio(hpRatio);
    servant.getController().onRespawn();
    bringIntoWorld((VisibleObject)servant, spawn, instanceIndex);
    return servant;
  }







  
  public Summon spawnSummon(Player creator, int npcId, int skillLvl) {
    float x = creator.getX();
    float y = creator.getY();
    float z = creator.getZ();
    byte heading = creator.getHeading();
    int worldId = creator.getWorldId();
    int instanceId = creator.getInstanceId();
    
    SpawnTemplate spawn = createSpawnTemplate(worldId, npcId, x, y, z, heading, 0, 0);
    NpcTemplate npcTemplate = DataManager.NPC_DATA.getNpcTemplate(npcId);
    
    byte level = (byte)(npcTemplate.getLevel() + skillLvl - 1);
    SummonStatsTemplate statsTemplate = DataManager.SUMMON_STATS_DATA.getSummonTemplate(npcId, level);
    Summon summon = new Summon(IDFactory.getInstance().nextId(), (CreatureController)new SummonController(), spawn, (VisibleObjectTemplate)npcTemplate, statsTemplate, level);
    
    summon.setKnownlist(new KnownList((VisibleObject)summon));
    summon.setEffectController(new EffectController((Creature)summon));
    summon.setMaster(creator);
    
    bringIntoWorld((VisibleObject)summon, spawn, instanceId);
    return summon;
  }














  
  private SpawnTemplate createSpawnTemplate(int worldId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk) {
    SpawnTemplate spawnTemplate = new SpawnTemplate(x, y, z, heading, walkerid, randomwalk, 0);
    
    SpawnGroup spawnGroup = new SpawnGroup(worldId, objectId, 295, 1);
    spawnTemplate.setSpawnGroup(spawnGroup);
    spawnGroup.getObjects().add(spawnTemplate);
    
    return spawnTemplate;
  }

















  
  public SpawnTemplate addNewSpawn(int worldId, int instanceId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk, boolean noRespawn) {
    return addNewSpawn(worldId, instanceId, objectId, x, y, z, heading, walkerid, randomwalk, noRespawn, false);
  }


















  
  public SpawnTemplate addNewSpawn(int worldId, int instanceId, int objectId, float x, float y, float z, byte heading, int walkerid, int randomwalk, boolean noRespawn, boolean isNewSpawn) {
    SpawnTemplate spawnTemplate = createSpawnTemplate(worldId, objectId, x, y, z, heading, walkerid, randomwalk);
    
    if (spawnTemplate == null) {
      
      log.warn("Object couldn't be spawned");
      return null;
    } 
    
    if (!noRespawn)
    {
      DataManager.SPAWNS_DATA.addNewSpawnGroup(spawnTemplate.getSpawnGroup(), worldId, objectId, isNewSpawn);
    }
    
    spawnTemplate.setNoRespawn(noRespawn, instanceId);
    
    return spawnTemplate;
  }

  
  private void bringIntoWorld(VisibleObject visibleObject, SpawnTemplate spawn, int instanceIndex) {
    World world = World.getInstance();
    world.storeObject((AionObject)visibleObject);
    world.setPosition(visibleObject, spawn.getWorldId(), instanceIndex, spawn.getX(), spawn.getY(), spawn.getZ(), spawn.getHeading());
    
    world.spawn(visibleObject);
  }




  
  public void spawnAll() {
    this.npcCounter = 0;
    this.gatherableCounter = 0;
    
    for (WorldMapTemplate worldMapTemplate : DataManager.WORLD_MAPS_DATA) {
      
      if (worldMapTemplate.isInstance())
        continue; 
      int maxTwin = worldMapTemplate.getTwinCount();
      int mapId = worldMapTemplate.getMapId().intValue();
      int numberToSpawn = (maxTwin > 0) ? maxTwin : 1;
      
      for (int i = 1; i <= numberToSpawn; i++)
      {
        spawnInstance(mapId, i);
      }
    } 
    
    log.info("Loaded " + this.npcCounter + " npc spawns");
    log.info("Loaded " + this.gatherableCounter + " gatherable spawns");
    
    RiftSpawnManager.startRiftPool();
  }






  
  public void spawnInstance(int worldId, int instanceIndex) {
    List<SpawnGroup> worldSpawns = DataManager.SPAWNS_DATA.getSpawnsForWorld(worldId);
    
    if (worldSpawns == null || worldSpawns.size() == 0) {
      return;
    }
    int instanceSpawnCounter = 0;
    for (SpawnGroup spawnGroup : worldSpawns) {
      
      spawnGroup.resetLastSpawnCounter(instanceIndex);
      if (spawnGroup.getHandler() == null) {
        
        int pool = spawnGroup.getPool();
        for (int i = 0; i < pool; i++) {
          
          spawnObject(spawnGroup.getNextAvailableTemplate(instanceIndex), instanceIndex);
          
          instanceSpawnCounter++;
        } 
        
        continue;
      } 
      switch (spawnGroup.getHandler()) {
        
        case RIFT:
          RiftSpawnManager.addRiftSpawnGroup(spawnGroup);
        
        case STATIC:
          StaticObjectSpawnManager.spawnGroup(spawnGroup, instanceIndex);
      } 


    
    } 
    log.info("Spawned " + worldId + " [" + instanceIndex + "] : " + instanceSpawnCounter);
  }

  
  private static class SingletonHolder
  {
    protected static final SpawnEngine instance = new SpawnEngine();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\spawnengine\SpawnEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
