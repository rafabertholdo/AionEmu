package com.aionemu.gameserver.model.gameobjects;

import com.aionemu.gameserver.ai.AI;
import com.aionemu.gameserver.controllers.CreatureController;
import com.aionemu.gameserver.controllers.MoveController;
import com.aionemu.gameserver.controllers.ObserveController;
import com.aionemu.gameserver.controllers.VisibleObjectController;
import com.aionemu.gameserver.controllers.attack.AggroList;
import com.aionemu.gameserver.controllers.effect.EffectController;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureSeeState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.CreatureLifeStats;
import com.aionemu.gameserver.model.templates.VisibleObjectTemplate;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.world.WorldPosition;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.log4j.Logger;

public abstract class Creature extends VisibleObject {
  private static final Logger log = Logger.getLogger(Creature.class);

  protected AI<? extends Creature> ai;

  private CreatureLifeStats<? extends Creature> lifeStats;

  private CreatureGameStats<? extends Creature> gameStats;

  private EffectController effectController;

  private MoveController moveController;

  private int state = CreatureState.ACTIVE.getId();
  private int visualState = CreatureVisualState.VISIBLE.getId();
  private int seeState = CreatureSeeState.NORMAL.getId();

  private Skill castingSkill;

  private Map<Integer, Long> skillCoolDowns;

  private int transformedModelId;

  private ObserveController observeController;

  private AggroList aggroList;

  private volatile byte packetBroadcastMask;

  public Creature(int objId, CreatureController<? extends Creature> controller, SpawnTemplate spawnTemplate,
      VisibleObjectTemplate objectTemplate, WorldPosition position) {
    super(objId, (VisibleObjectController) controller, spawnTemplate, objectTemplate, position);
    initializeAi();
    this.moveController = new MoveController(this);
    this.observeController = new ObserveController();

    this.aggroList = new AggroList(this);
  }

  public CreatureController getController() {
    return (CreatureController) super.getController();
  }

  public CreatureLifeStats<? extends Creature> getLifeStats() {
    return this.lifeStats;
  }

  public void setLifeStats(CreatureLifeStats<? extends Creature> lifeStats) {
    this.lifeStats = lifeStats;
  }

  public CreatureGameStats<? extends Creature> getGameStats() {
    return this.gameStats;
  }

  public void setGameStats(CreatureGameStats<? extends Creature> gameStats) {
    this.gameStats = gameStats;
  }

  public EffectController getEffectController() {
    return this.effectController;
  }

  public void setEffectController(EffectController effectController) {
    this.effectController = effectController;
  }

  public AI<? extends Creature> getAi() {
    return (this.ai != null) ? this.ai : (AI<? extends Creature>) AI.dummyAi();
  }

  public void setAi(AI<? extends Creature> ai) {
    this.ai = ai;
  }

  public boolean isCasting() {
    return (this.castingSkill != null);
  }

  public void setCasting(Skill castingSkill) {
    this.castingSkill = castingSkill;
  }

  public int getCastingSkillId() {
    return (this.castingSkill != null) ? this.castingSkill.getSkillTemplate().getSkillId() : 0;
  }

  public Skill getCastingSkill() {
    return this.castingSkill;
  }

  public boolean canPerformMove() {
    return (!getEffectController().isAbnormalState(EffectId.CANT_MOVE_STATE) && isSpawned());
  }

  public boolean canAttack() {
    return (!getEffectController().isAbnormalState(EffectId.CANT_ATTACK_STATE) && !isCasting()
        && !isInState(CreatureState.RESTING) && !isInState(CreatureState.PRIVATE_SHOP));
  }

  public int getState() {
    return this.state;
  }

  public void setState(CreatureState state) {
    this.state |= state.getId();
  }

  public void setState(int state) {
    this.state = state;
  }

  public void unsetState(CreatureState state) {
    this.state &= state.getId() ^ 0xFFFFFFFF;
  }

  public boolean isInState(CreatureState state) {
    int isState = this.state & state.getId();

    if (isState == state.getId()) {
      return true;
    }
    return false;
  }

  public int getVisualState() {
    return this.visualState;
  }

  public void setVisualState(CreatureVisualState visualState) {
    this.visualState |= visualState.getId();
  }

  public void unsetVisualState(CreatureVisualState visualState) {
    this.visualState &= visualState.getId() ^ 0xFFFFFFFF;
  }

  public boolean isInVisualState(CreatureVisualState visualState) {
    int isVisualState = this.visualState & visualState.getId();

    if (isVisualState == visualState.getId()) {
      return true;
    }
    return false;
  }

  public int getSeeState() {
    return this.seeState;
  }

  public void setSeeState(CreatureSeeState seeState) {
    this.seeState |= seeState.getId();
  }

  public void unsetSeeState(CreatureSeeState seeState) {
    this.seeState &= seeState.getId() ^ 0xFFFFFFFF;
  }

  public boolean isInSeeState(CreatureSeeState seeState) {
    int isSeeState = this.seeState & seeState.getId();

    if (isSeeState == seeState.getId()) {
      return true;
    }
    return false;
  }

  public int getTransformedModelId() {
    return this.transformedModelId;
  }

  public void setTransformedModelId(int transformedModelId) {
    this.transformedModelId = transformedModelId;
  }

  public MoveController getMoveController() {
    return this.moveController;
  }

  public AggroList getAggroList() {
    return this.aggroList;
  }

  public final void addPacketBroadcastMask(PacketBroadcaster.BroadcastMode mode) {
    this.packetBroadcastMask = (byte) (this.packetBroadcastMask | mode.mask());

    PacketBroadcaster.getInstance().add(this);

    if (log.isDebugEnabled()) {
      log.debug("PacketBroadcaster: Packet " + mode.name() + " added to player " + ((Player) this).getName());
    }
  }

  public final void removePacketBroadcastMask(PacketBroadcaster.BroadcastMode mode) {
    this.packetBroadcastMask = (byte) (this.packetBroadcastMask & (mode.mask() ^ 0xFFFFFFFF));

    if (log.isDebugEnabled()) {
      log.debug("PacketBroadcaster: Packet " + mode.name() + " removed from player " + ((Player) this).getName());
    }
  }

  public final byte getPacketBroadcastMask() {
    return this.packetBroadcastMask;
  }

  public ObserveController getObserveController() {
    return this.observeController;
  }

  public boolean isEnemy(VisibleObject visibleObject) {
    if (visibleObject instanceof Npc)
      return isEnemyNpc((Npc) visibleObject);
    if (visibleObject instanceof Player)
      return isEnemyPlayer((Player) visibleObject);
    if (visibleObject instanceof Summon) {
      return isEnemySummon((Summon) visibleObject);
    }
    return false;
  }

  protected boolean isEnemySummon(Summon summon) {
    return false;
  }

  protected boolean isEnemyPlayer(Player player) {
    return false;
  }

  protected boolean isEnemyNpc(Npc npc) {
    return false;
  }

  public String getTribe() {
    return "";
  }

  public boolean isAggressiveTo(Creature creature) {
    return false;
  }

  public boolean isAggroFrom(Npc npc) {
    return false;
  }

  public boolean isHostileFrom(Npc npc) {
    return false;
  }

  public boolean isSupportFrom(Npc npc) {
    return false;
  }

  public boolean isAggroFrom(Player player) {
    return false;
  }

  public boolean isAggroFrom(Summon summon) {
    return isAggroFrom(summon.getMaster());
  }

  public boolean canSee(VisibleObject visibleObject) {
    if (visibleObject instanceof Npc)
      return canSeeNpc((Npc) visibleObject);
    if (visibleObject instanceof Player) {
      return canSeePlayer((Player) visibleObject);
    }
    return true;
  }

  protected boolean canSeePlayer(Player visibleObject) {
    return true;
  }

  protected boolean canSeeNpc(Npc visibleObject) {
    return true;
  }

  public NpcObjectType getNpcObjectType() {
    return NpcObjectType.NORMAL;
  }

  public Creature getMaster() {
    return this;
  }

  public Creature getActingCreature() {
    return this;
  }

  public boolean isSkillDisabled(int skillId) {
    if (this.skillCoolDowns == null) {
      return false;
    }
    Long coolDown = this.skillCoolDowns.get(Integer.valueOf(skillId));
    if (coolDown == null) {
      return false;
    }

    if (coolDown.longValue() < System.currentTimeMillis()) {

      this.skillCoolDowns.remove(Integer.valueOf(skillId));
      return false;
    }

    return true;
  }

  public long getSkillCoolDown(int skillId) {
    if (this.skillCoolDowns == null || !this.skillCoolDowns.containsKey(Integer.valueOf(skillId))) {
      return 0L;
    }
    return ((Long) this.skillCoolDowns.get(Integer.valueOf(skillId))).longValue();
  }

  public void setSkillCoolDown(int skillId, long time) {
    if (this.skillCoolDowns == null) {
      this.skillCoolDowns = (Map<Integer, Long>) (new FastMap()).shared();
    }
    this.skillCoolDowns.put(Integer.valueOf(skillId), Long.valueOf(time));
  }

  public Map<Integer, Long> getSkillCoolDowns() {
    return this.skillCoolDowns;
  }

  public void removeSkillCoolDown(int skillId) {
    if (this.skillCoolDowns == null)
      return;
    this.skillCoolDowns.remove(Integer.valueOf(skillId));
  }

  public abstract byte getLevel();

  public abstract void initializeAi();
}
