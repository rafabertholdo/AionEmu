/*     */ package com.aionemu.gameserver.model.gameobjects;
/*     */ 
/*     */ import com.aionemu.gameserver.ai.AI;
/*     */ import com.aionemu.gameserver.controllers.CreatureController;
/*     */ import com.aionemu.gameserver.controllers.MoveController;
/*     */ import com.aionemu.gameserver.controllers.ObserveController;
/*     */ import com.aionemu.gameserver.controllers.VisibleObjectController;
/*     */ import com.aionemu.gameserver.controllers.attack.AggroList;
/*     */ import com.aionemu.gameserver.controllers.effect.EffectController;
/*     */ import com.aionemu.gameserver.model.gameobjects.player.Player;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureSeeState;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
/*     */ import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
/*     */ import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
/*     */ import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
/*     */ import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
/*     */ import com.aionemu.gameserver.skillengine.effect.EffectId;
/*     */ import com.aionemu.gameserver.skillengine.model.Skill;
/*     */ import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
/*     */ import com.aionemu.gameserver.world.WorldPosition;
/*     */ import java.util.Map;
/*     */ import javolution.util.FastMap;
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
/*     */ public abstract class Creature
/*     */   extends VisibleObject
/*     */ {
/*  54 */   private static final Logger log = Logger.getLogger(Creature.class);
/*     */ 
/*     */   
/*     */   protected AI<? extends Creature> ai;
/*     */   
/*     */   private CreatureLifeStats<? extends Creature> lifeStats;
/*     */   
/*     */   private CreatureGameStats<? extends Creature> gameStats;
/*     */   
/*     */   private EffectController effectController;
/*     */   
/*     */   private MoveController moveController;
/*     */   
/*  67 */   private int state = CreatureState.ACTIVE.getId();
/*  68 */   private int visualState = CreatureVisualState.VISIBLE.getId();
/*  69 */   private int seeState = CreatureSeeState.NORMAL.getId();
/*     */ 
/*     */   
/*     */   private Skill castingSkill;
/*     */ 
/*     */   
/*     */   private Map<Integer, Long> skillCoolDowns;
/*     */ 
/*     */   
/*     */   private int transformedModelId;
/*     */ 
/*     */   
/*     */   private ObserveController observeController;
/*     */   
/*     */   private AggroList aggroList;
/*     */   
/*     */   private volatile byte packetBroadcastMask;
/*     */ 
/*     */   
/*     */   public Creature(int objId, CreatureController<? extends Creature> controller, SpawnTemplate spawnTemplate, VisibleObjectTemplate objectTemplate, WorldPosition position) {
/*  89 */     super(objId, (VisibleObjectController)controller, spawnTemplate, objectTemplate, position);
/*  90 */     initializeAi();
/*  91 */     this.moveController = new MoveController(this);
/*  92 */     this.observeController = new ObserveController();
/*     */     
/*  94 */     this.aggroList = new AggroList(this);
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
/*     */   public CreatureController getController() {
/* 106 */     return (CreatureController)super.getController();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreatureLifeStats<? extends Creature> getLifeStats() {
/* 114 */     return this.lifeStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLifeStats(CreatureLifeStats<? extends Creature> lifeStats) {
/* 122 */     this.lifeStats = lifeStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreatureGameStats<? extends Creature> getGameStats() {
/* 130 */     return this.gameStats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameStats(CreatureGameStats<? extends Creature> gameStats) {
/* 138 */     this.gameStats = gameStats;
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
/*     */   public EffectController getEffectController() {
/* 150 */     return this.effectController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEffectController(EffectController effectController) {
/* 158 */     this.effectController = effectController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AI<? extends Creature> getAi() {
/* 166 */     return (this.ai != null) ? this.ai : (AI<? extends Creature>)AI.dummyAi();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAi(AI<? extends Creature> ai) {
/* 174 */     this.ai = ai;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCasting() {
/* 184 */     return (this.castingSkill != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCasting(Skill castingSkill) {
/* 194 */     this.castingSkill = castingSkill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCastingSkillId() {
/* 204 */     return (this.castingSkill != null) ? this.castingSkill.getSkillTemplate().getSkillId() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Skill getCastingSkill() {
/* 214 */     return this.castingSkill;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPerformMove() {
/* 224 */     return (!getEffectController().isAbnormalState(EffectId.CANT_MOVE_STATE) && isSpawned());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttack() {
/* 233 */     return (!getEffectController().isAbnormalState(EffectId.CANT_ATTACK_STATE) && !isCasting() && !isInState(CreatureState.RESTING) && !isInState(CreatureState.PRIVATE_SHOP));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 241 */     return this.state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(CreatureState state) {
/* 249 */     this.state |= state.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(int state) {
/* 257 */     this.state = state;
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetState(CreatureState state) {
/* 262 */     this.state &= state.getId() ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInState(CreatureState state) {
/* 267 */     int isState = this.state & state.getId();
/*     */     
/* 269 */     if (isState == state.getId()) {
/* 270 */       return true;
/*     */     }
/* 272 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVisualState() {
/* 280 */     return this.visualState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisualState(CreatureVisualState visualState) {
/* 288 */     this.visualState |= visualState.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetVisualState(CreatureVisualState visualState) {
/* 293 */     this.visualState &= visualState.getId() ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInVisualState(CreatureVisualState visualState) {
/* 298 */     int isVisualState = this.visualState & visualState.getId();
/*     */     
/* 300 */     if (isVisualState == visualState.getId()) {
/* 301 */       return true;
/*     */     }
/* 303 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSeeState() {
/* 311 */     return this.seeState;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSeeState(CreatureSeeState seeState) {
/* 319 */     this.seeState |= seeState.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unsetSeeState(CreatureSeeState seeState) {
/* 324 */     this.seeState &= seeState.getId() ^ 0xFFFFFFFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInSeeState(CreatureSeeState seeState) {
/* 329 */     int isSeeState = this.seeState & seeState.getId();
/*     */     
/* 331 */     if (isSeeState == seeState.getId()) {
/* 332 */       return true;
/*     */     }
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTransformedModelId() {
/* 342 */     return this.transformedModelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransformedModelId(int transformedModelId) {
/* 350 */     this.transformedModelId = transformedModelId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MoveController getMoveController() {
/* 358 */     return this.moveController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AggroList getAggroList() {
/* 366 */     return this.aggroList;
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
/*     */   public final void addPacketBroadcastMask(PacketBroadcaster.BroadcastMode mode) {
/* 379 */     this.packetBroadcastMask = (byte)(this.packetBroadcastMask | mode.mask());
/*     */     
/* 381 */     PacketBroadcaster.getInstance().add(this);
/*     */ 
/*     */     
/* 384 */     if (log.isDebugEnabled()) {
/* 385 */       log.debug("PacketBroadcaster: Packet " + mode.name() + " added to player " + ((Player)this).getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void removePacketBroadcastMask(PacketBroadcaster.BroadcastMode mode) {
/* 393 */     this.packetBroadcastMask = (byte)(this.packetBroadcastMask & (mode.mask() ^ 0xFFFFFFFF));
/*     */ 
/*     */     
/* 396 */     if (log.isDebugEnabled()) {
/* 397 */       log.debug("PacketBroadcaster: Packet " + mode.name() + " removed from player " + ((Player)this).getName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final byte getPacketBroadcastMask() {
/* 405 */     return this.packetBroadcastMask;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ObserveController getObserveController() {
/* 413 */     return this.observeController;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnemy(VisibleObject visibleObject) {
/* 423 */     if (visibleObject instanceof Npc)
/* 424 */       return isEnemyNpc((Npc)visibleObject); 
/* 425 */     if (visibleObject instanceof Player)
/* 426 */       return isEnemyPlayer((Player)visibleObject); 
/* 427 */     if (visibleObject instanceof Summon) {
/* 428 */       return isEnemySummon((Summon)visibleObject);
/*     */     }
/* 430 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemySummon(Summon summon) {
/* 439 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyPlayer(Player player) {
/* 448 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isEnemyNpc(Npc npc) {
/* 457 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTribe() {
/* 462 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggressiveTo(Creature creature) {
/* 472 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggroFrom(Npc npc) {
/* 482 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHostileFrom(Npc npc) {
/* 492 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSupportFrom(Npc npc) {
/* 498 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggroFrom(Player player) {
/* 507 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAggroFrom(Summon summon) {
/* 517 */     return isAggroFrom(summon.getMaster());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSee(VisibleObject visibleObject) {
/* 526 */     if (visibleObject instanceof Npc)
/* 527 */       return canSeeNpc((Npc)visibleObject); 
/* 528 */     if (visibleObject instanceof Player) {
/* 529 */       return canSeePlayer((Player)visibleObject);
/*     */     }
/* 531 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSeePlayer(Player visibleObject) {
/* 540 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSeeNpc(Npc visibleObject) {
/* 549 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NpcObjectType getNpcObjectType() {
/* 557 */     return NpcObjectType.NORMAL;
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
/*     */   public Creature getMaster() {
/* 571 */     return this;
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
/*     */   public Creature getActingCreature() {
/* 584 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSkillDisabled(int skillId) {
/* 594 */     if (this.skillCoolDowns == null) {
/* 595 */       return false;
/*     */     }
/* 597 */     Long coolDown = this.skillCoolDowns.get(Integer.valueOf(skillId));
/* 598 */     if (coolDown == null) {
/* 599 */       return false;
/*     */     }
/*     */     
/* 602 */     if (coolDown.longValue() < System.currentTimeMillis()) {
/*     */       
/* 604 */       this.skillCoolDowns.remove(Integer.valueOf(skillId));
/* 605 */       return false;
/*     */     } 
/*     */     
/* 608 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSkillCoolDown(int skillId) {
/* 618 */     if (this.skillCoolDowns == null || !this.skillCoolDowns.containsKey(Integer.valueOf(skillId))) {
/* 619 */       return 0L;
/*     */     }
/* 621 */     return ((Long)this.skillCoolDowns.get(Integer.valueOf(skillId))).longValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSkillCoolDown(int skillId, long time) {
/* 631 */     if (this.skillCoolDowns == null) {
/* 632 */       this.skillCoolDowns = (Map<Integer, Long>)(new FastMap()).shared();
/*     */     }
/* 634 */     this.skillCoolDowns.put(Integer.valueOf(skillId), Long.valueOf(time));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, Long> getSkillCoolDowns() {
/* 642 */     return this.skillCoolDowns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeSkillCoolDown(int skillId) {
/* 651 */     if (this.skillCoolDowns == null)
/*     */       return; 
/* 653 */     this.skillCoolDowns.remove(Integer.valueOf(skillId));
/*     */   }
/*     */   
/*     */   public abstract byte getLevel();
/*     */   
/*     */   public abstract void initializeAi();
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\gameobjects\Creature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */