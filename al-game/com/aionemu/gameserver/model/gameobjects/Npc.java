/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.ai.npcai.AggressiveAi;
/*     */ import com.aionemu.gameserver.ai.npcai.NpcAi;
/*     */ import com.aionemu.gameserver.configs.main.CustomConfig;
/*     */ import com.aionemu.gameserver.configs.main.NpcMovementConfig;
/*     */ import com.aionemu.gameserver.controllers.CreatureController;
/*     */ import com.aionemu.gameserver.controllers.NpcController;
/*     */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*     */ import com.aionemu.gameserver.dataholders.DataManager;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.NpcGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.NpcLifeStats;
/*     */ import com.aionemu.gameserver.model.templates.NpcTemplate;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.npcskill.NpcSkillList;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.model.templates.stats.NpcRank;
/*     */ import com.aionemu.gameserver.utils.MathUtil;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
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
/*     */ public class Npc
/*     */   extends Creature
/*     */ {
/*     */   private NpcSkillList npcSkillList;
/*     */   
/*     */   public Npc(int objId, NpcController controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate) {
/*  59 */     super(objId, (CreatureController<? extends Creature>)controller, spawnTemplate, objectTemplate, new WorldPosition());
/*  60 */     controller.setOwner(this);
/*     */     
/*  62 */     setGameStats((CreatureGameStats<? extends Creature>)new NpcGameStats(this));
/*  63 */     setLifeStats((CreatureLifeStats<? extends Creature>)new NpcLifeStats(this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcTemplate getObjectTemplate() {
/*  69 */     return (NpcTemplate)this.objectTemplate;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  74 */     return getObjectTemplate().getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getNpcId() {
/*  79 */     return getObjectTemplate().getTemplateId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getLevel() {
/*  85 */     return getObjectTemplate().getLevel();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcLifeStats getLifeStats() {
/*  94 */     return (NpcLifeStats)super.getLifeStats();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcGameStats getGameStats() {
/* 103 */     return (NpcGameStats)super.getGameStats();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcController getController() {
/* 109 */     return (NpcController)super.getController();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasWalkRoutes() {
/* 114 */     return (getSpawn().getWalkerId() > 0 || (getSpawn().hasRandomWalk() && NpcMovementConfig.ACTIVE_NPC_MOVEMENT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressive() {
/* 123 */     String currentTribe = getObjectTemplate().getTribe();
/* 124 */     return (DataManager.TRIBE_RELATIONS_DATA.hasAggressiveRelations(currentTribe) || isGuard() || isHostile());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHostile() {
/* 129 */     String currentTribe = getObjectTemplate().getTribe();
/* 130 */     return DataManager.TRIBE_RELATIONS_DATA.hasHostileRelations(currentTribe);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressiveTo(Creature creature) {
/* 136 */     return (creature.isAggroFrom(this) || creature.isHostileFrom(this));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggroFrom(Npc npc) {
/* 142 */     return DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(npc.getTribe(), getTribe());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHostileFrom(Npc npc) {
/* 148 */     return DataManager.TRIBE_RELATIONS_DATA.isHostileRelation(npc.getTribe(), getTribe());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportFrom(Npc npc) {
/* 154 */     return DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(npc.getTribe(), getTribe());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isGuard() {
/* 163 */     String currentTribe = getObjectTemplate().getTribe();
/* 164 */     return (DataManager.TRIBE_RELATIONS_DATA.isGuardDark(currentTribe) || DataManager.TRIBE_RELATIONS_DATA.isGuardLight(currentTribe));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getTribe() {
/* 171 */     return getObjectTemplate().getTribe();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAggroRange() {
/* 176 */     return getObjectTemplate().getAggroRange();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeAi() {
/* 182 */     if (isAggressive() && !CustomConfig.DISABLE_MOB_AGGRO) {
/* 183 */       this.ai = (AI<? extends Creature>)new AggressiveAi();
/*     */     } else {
/* 185 */       this.ai = (AI<? extends Creature>)new NpcAi();
/* 186 */     }  this.ai.setOwner(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAtSpawnLocation() {
/* 196 */     return (MathUtil.getDistance(getSpawn().getX(), getSpawn().getY(), getSpawn().getZ(), getX(), getY(), getZ()) < 3.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcSkillList getNpcSkillList() {
/* 205 */     return this.npcSkillList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNpcSkillList(NpcSkillList npcSkillList) {
/* 213 */     this.npcSkillList = npcSkillList;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc visibleObject) {
/* 219 */     return (DataManager.TRIBE_RELATIONS_DATA.isAggressiveRelation(getTribe(), visibleObject.getTribe()) || DataManager.TRIBE_RELATIONS_DATA.isHostileRelation(getTribe(), visibleObject.getTribe()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player visibleObject) {
/* 225 */     return (!DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(getTribe(), visibleObject.getTribe()) && !DataManager.TRIBE_RELATIONS_DATA.isFriendlyRelation(getTribe(), visibleObject.getTribe()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemySummon(Summon visibleObject) {
/* 231 */     Player player = visibleObject.getMaster();
/* 232 */     if (player != null)
/* 233 */       return (!DataManager.TRIBE_RELATIONS_DATA.isSupportRelation(getTribe(), player.getTribe()) && !DataManager.TRIBE_RELATIONS_DATA.isFriendlyRelation(getTribe(), player.getTribe())); 
/* 234 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSeeNpc(Npc npc) {
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSeePlayer(Player player) {
/* 246 */     if (!player.isInState(CreatureState.ACTIVE)) {
/* 247 */       return false;
/*     */     }
/* 249 */     if (player.getVisualState() == 1 && getObjectTemplate().getRank() == NpcRank.NORMAL) {
/* 250 */       return false;
/*     */     }
/* 252 */     if (player.getVisualState() == 2 && (getObjectTemplate().getRank() == NpcRank.ELITE || getObjectTemplate().getRank() == NpcRank.NORMAL)) {
/* 253 */       return false;
/*     */     }
/* 255 */     if (player.getVisualState() >= 3) {
/* 256 */       return false;
/*     */     }
/* 258 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Npc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */