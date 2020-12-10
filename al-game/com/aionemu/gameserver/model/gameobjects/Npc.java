package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.ai.npcai.AggressiveAi;
import com.aionemu.gameserver.ai.npcai.NpcAi;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.NpcMovementConfig;
import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.NpcController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.model.gameobjects.stats.NpcGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.NpcLifeStats;
import com.aionemu.gameserver.model.templates.NpcTemplate;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.model.templates.stats.NpcRank;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.world.WorldPosition;




























public class Npc
  extends Creature
{
  private NpcSkillList npcSkillList;
  
  public Npc(int objId, NpcController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
    super(objId, (CreatureController<? extends Creature>)controller, spawnTemplate, objectTemplate, new WorldPosition());
    controller.setOwner(this);
    
    setGameStats((CreatureGameStats<? extends Creature>)new NpcGameStats(this));
    setLifeStats((CreatureLifeStats<? extends Creature>)new NpcLifeStats(this));
  }


  
  public NpcTemplate getObjectTemplate() {
    return (NpcTemplate)this.objectTemplate;
  }

  
  public String getName() {
    return getObjectTemplate().getName();
  }

  
  public int getNpcId() {
    return getObjectTemplate().getTemplateId();
  }


  
  public byte getLevel() {
    return getObjectTemplate().getLevel();
  }





  
  public NpcLifeStats getLifeStats() {
    return (NpcLifeStats)super.getLifeStats();
  }





  
  public NpcGameStats getGameStats() {
    return (NpcGameStats)super.getGameStats();
  }


  
  public NpcController getController() {
    return (NpcController)super.getController();
  }

  
  public boolean hasWalkRoutes() {
    return (getSpawn().getWalkerId() > 0 || (getSpawn().hasRandomWalk() && NpcMovementConfig.ACTIVE_NPC_MOVEMENT));
  }





  
  public boolean isAggressive() {
    String currentTribe = getObjectTemplate().getTribe();
    return (DataManager.TRIBE_RELATIONS_DATA.hasAggressiveRelations(currentTribe) || isGuard() || isHostile());
  }

  
  public boolean isHostile() {
    String currentTribe = getObjectTemplate().getTribe();
    return DataManager.TRIBE_RELATIONS_DATA.hasHostileRelations(currentTribe);
  }


  
  public boolean isAggressiveTo(Creature creature) {
    return (creature.isAggroFrom(this) || creature.isHostileFrom(this));
  }


  
  public boolean isAggroFrom(Npc npc) {
    return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npc.getTribe(), getTribe());
  }


  
  public boolean isHostileFrom(Npc npc) {
    return DataManager.TRIBE_RELATIONS_DATA.isHostileRelation(npc.getTribe(), getTribe());
  }


  
  public boolean isSupportFrom(Npc npc) {
    return DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(npc.getTribe(), getTribe());
  }





  
  public boolean isGuard() {
    String currentTribe = getObjectTemplate().getTribe();
    return (DataManager.TRIBE_RELATIONS_DATA.isGuardDark(currentTribe) || DataManager.TRIBE_RELATIONS_DATA.isGuardLight(currentTribe));
  }



  
  public String getTribe() {
    return getObjectTemplate().getTribe();
  }

  
  public int getAggroRange() {
    return getObjectTemplate().getAggroRange();
  }


  
  public void initializeAi() {
    if (isAggressive() && !CustomConfig.DISABLE_MOB_AGGRO) {
      this.ai = (AI<? extends Creature>)new AggressiveAi();
    } else {
      this.ai = (AI<? extends Creature>)new NpcAi();
    }  this.ai.setOwner(this);
  }






  
  public boolean isAtSpawnLocation() {
    return (MathUtil.getDistance(getSpawn().getX(), getSpawn().getY(), getSpawn().getZ(), getX(), getY(), getZ()) < 3.0D);
  }





  
  public NpcSkillList getNpcSkillList() {
    return this.npcSkillList;
  }




  
  public void setNpcSkillList(NpcSkillList npcSkillList) {
    this.npcSkillList = npcSkillList;
  }


  
  protected boolean isEnemyNpc(Npc visibleObject) {
    return (DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(getTribe(), visibleObject.getTribe()) || DataManager.TRIBE_RELATIONS_DATA.isHostileRelation(getTribe(), visibleObject.getTribe()));
  }


  
  protected boolean isEnemyPlayer(Player visibleObject) {
    return (!DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(getTribe(), visibleObject.getTribe()) && !DataManager.TRIBE_RELATIONS_DATA.isFriendlyRelation(getTribe(), visibleObject.getTribe()));
  }


  
  protected boolean isEnemySummon(Summon visibleObject) {
    Player player = visibleObject.getMaster();
    if (player != null)
      return (!DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(getTribe(), player.getTribe()) && !DataManager.TRIBE_RELATIONS_DATA.isFriendlyRelation(getTribe(), player.getTribe())); 
    return true;
  }


  
  protected boolean canSeeNpc(Npc npc) {
    return true;
  }


  
  protected boolean canSeePlayer(Player player) {
    if (!player.isInState(CreatureState.ACTIVE)) {
      return false;
    }
    if (player.getVisualState() == 1 && getObjectTemplate().getRank() == NpcRank.NORMAL) {
      return false;
    }
    if (player.getVisualState() == 2 && (getObjectTemplate().getRank() == NpcRank.ELITE || getObjectTemplate().getRank() == NpcRank.NORMAL)) {
      return false;
    }
    if (player.getVisualState() >= 3) {
      return false;
    }
    return true;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Npc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
