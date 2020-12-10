package com.aionemu.gameserver.controllers;

import com.aionemu.gameserver.controllers.attack.AttackResult;
import com.aionemu.gameserver.controllers.attack.AttackUtil;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.alliance.PlayerAllianceEvent;
import com.aionemu.gameserver.model.gameobjects.AionObject;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Kisk;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.Summon;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
import com.aionemu.gameserver.model.gameobjects.state.CreatureState;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.model.gameobjects.stats.PlayerGameStats;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.model.group.GroupEvent;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.stats.PlayerStatsTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DELETE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_GATHERABLE_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_KISK_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_LEVEL_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NEARBY_QUESTS;
import com.aionemu.gameserver.network.aion.serverpackets.SM_NPC_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PRIVATE_STORE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_CANCEL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SKILL_LIST;
import com.aionemu.gameserver.network.aion.serverpackets.SM_STATS_INFO;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_PANEL;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SUMMON_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SYSTEM_MESSAGE;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.restrictions.RestrictionsManager;
import com.aionemu.gameserver.services.AllianceService;
import com.aionemu.gameserver.services.ClassChangeService;
import com.aionemu.gameserver.services.DuelService;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.services.PvpService;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.services.SkillLearnService;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.skillengine.SkillEngine;
import com.aionemu.gameserver.skillengine.model.HealType;
import com.aionemu.gameserver.skillengine.model.Skill;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.taskmanager.tasks.PacketBroadcaster;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import com.aionemu.gameserver.world.zone.ZoneInstance;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;

public class PlayerController extends CreatureController<Player> {
  private boolean isInShutdownProgress;
  private volatile byte zoneUpdateMask;
  private long lastAttackMilis = 0L;

  public void see(VisibleObject object) {
    super.see(object);
    if (object instanceof Player) {

      PacketSendUtility.sendPacket(getOwner(),
          (AionServerPacket) new SM_PLAYER_INFO((Player) object, getOwner().isEnemyPlayer((Player) object)));
      getOwner().getEffectController().sendEffectIconsTo((Player) object);
    } else if (object instanceof Kisk) {

      Kisk kisk = (Kisk) object;
      PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_NPC_INFO(getOwner(), kisk));
      if (getOwner().getCommonData().getRace() == kisk.getOwnerRace()) {
        PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_KISK_UPDATE(kisk));
      }
    } else if (object instanceof Npc) {

      boolean update = false;
      Npc npc = (Npc) object;

      PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_NPC_INFO(npc, getOwner()));

      for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(npc.getNpcId()).getOnQuestStart()
          .iterator(); i$.hasNext();) {
        int questId = ((Integer) i$.next()).intValue();

        if (QuestService
            .checkStartCondition(new QuestEnv(object, getOwner(), Integer.valueOf(questId), Integer.valueOf(0)))) {
          if (!getOwner().getNearbyQuests().contains(Integer.valueOf(questId))) {

            update = true;
            getOwner().getNearbyQuests().add(Integer.valueOf(questId));
          }
        }
      }

      if (update) {
        updateNearbyQuestList();
      }
    } else if (object instanceof Summon) {

      Summon npc = (Summon) object;
      PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_NPC_INFO(npc));
    } else if (object instanceof com.aionemu.gameserver.model.gameobjects.Gatherable
        || object instanceof com.aionemu.gameserver.model.gameobjects.StaticObject) {

      PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_GATHERABLE_INFO(object));
    }
  }

  public void notSee(VisibleObject object, boolean isOutOfRange) {
    super.notSee(object, isOutOfRange);
    if (object instanceof Npc) {

      boolean update = false;
      for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(((Npc) object).getNpcId()).getOnQuestStart()
          .iterator(); i$.hasNext();) {
        int questId = ((Integer) i$.next()).intValue();

        if (QuestService
            .checkStartCondition(new QuestEnv(object, getOwner(), Integer.valueOf(questId), Integer.valueOf(0)))) {
          if (getOwner().getNearbyQuests().contains(Integer.valueOf(questId))) {

            update = true;
            getOwner().getNearbyQuests().remove(getOwner().getNearbyQuests().indexOf(Integer.valueOf(questId)));
          }
        }
      }

      if (update) {
        updateNearbyQuestList();
      }
    }
    PacketSendUtility.sendPacket(getOwner(),
        (AionServerPacket) new SM_DELETE((AionObject) object, isOutOfRange ? 0 : 15));
  }

  public void updateNearbyQuests() {
    getOwner().getNearbyQuests().clear();
    for (VisibleObject obj : getOwner().getKnownList().getKnownObjects().values()) {

      if (obj instanceof Npc) {
        for (Iterator<Integer> i$ = QuestEngine.getInstance().getNpcQuestData(((Npc) obj).getNpcId()).getOnQuestStart()
            .iterator(); i$.hasNext();) {
          int questId = ((Integer) i$.next()).intValue();

          if (QuestService
              .checkStartCondition(new QuestEnv(obj, getOwner(), Integer.valueOf(questId), Integer.valueOf(0)))) {
            if (!getOwner().getNearbyQuests().contains(Integer.valueOf(questId))) {
              getOwner().getNearbyQuests().add(Integer.valueOf(questId));
            }
          }
        }

      }
    }
    updateNearbyQuestList();
  }

  public void onEnterZone(ZoneInstance zoneInstance) {
    QuestEngine.getInstance().onEnterZone(new QuestEnv(null, getOwner(), Integer.valueOf(0), Integer.valueOf(0)),
        zoneInstance.getTemplate().getName());
  }

  public void onLeaveZone(ZoneInstance zoneInstance) {
  }

  public void resetZone() {
    getOwner().setZoneInstance(null);
  }

  public void onDie(Creature lastAttacker) {
    Player player = getOwner();

    Creature master = null;
    if (lastAttacker != null) {
      master = lastAttacker.getMaster();
    }
    if (master instanceof Player) {
      if (isDueling((Player) master)) {

        DuelService.getInstance().onDie(player);

        return;
      }
    }
    doReward();

    boolean hasSelfRezEffect = player.getReviveController().checkForSelfRezEffect(player);

    super.onDie(lastAttacker);

    if (master instanceof Npc || master == player) {
      if (player.getLevel() > 4) {
        player.getCommonData().calculateExpLoss();
      }
    }

    Summon summon = player.getSummon();
    if (summon != null) {
      summon.getController().release(SummonController.UnsummonType.UNSPECIFIED);
    }
    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_EMOTION((Creature) player, EmotionType.DIE, 0,
        (lastAttacker == null) ? 0 : lastAttacker.getObjectId()), true);

    int kiskTimeRemaining = (player.getKisk() != null) ? player.getKisk().getRemainingLifetime() : 0;
    boolean hasSelfRezItem = player.getReviveController().checkForSelfRezItem(player);
    PacketSendUtility.sendPacket(player,
        (AionServerPacket) new SM_DIE(hasSelfRezEffect, hasSelfRezItem, kiskTimeRemaining));

    PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.DIE);
    QuestEngine.getInstance().onDie(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
  }

  public void doReward() {
    Player victim = getOwner();
    PvpService.getInstance().doReward(victim);
  }

  public void onRespawn() {
    super.onRespawn();
    startProtectionActiveTask();
  }

  public void attackTarget(Creature target) {
    Player player = getOwner();

    if (target == null || !player.canAttack()) {
      return;
    }
    PlayerGameStats gameStats = player.getGameStats();

    if (Math.abs(player.getZ() - target.getZ()) > 6.0F) {
      return;
    }
    if (!RestrictionsManager.canAttack(player, (VisibleObject) target)) {
      return;
    }
    int attackSpeed = gameStats.getCurrentStat(StatEnum.ATTACK_SPEED);
    long milis = System.currentTimeMillis();
    if (milis - this.lastAttackMilis < attackSpeed) {
      return;
    }

    this.lastAttackMilis = milis;

    super.attackTarget(target);

    List<AttackResult> attackResult = AttackUtil.calculateAttackResult((Creature) player, target);

    int damage = 0;
    for (AttackResult result : attackResult) {
      damage += result.getDamage();
    }

    long time = System.currentTimeMillis();
    int attackType = 0;
    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_ATTACK((Creature) player, target,
        gameStats.getAttackCounter(), (int) time, attackType, attackResult), true);

    target.getController().onAttack((Creature) player, damage);

    gameStats.increaseAttackCounter();
  }

  public void onAttack(Creature creature, int skillId, SM_ATTACK_STATUS.TYPE type, int damage) {
    if (getOwner().getLifeStats().isAlreadyDead()) {
      return;
    }

    if (damage > getOwner().getLifeStats().getCurrentHp()) {
      damage = getOwner().getLifeStats().getCurrentHp() + 1;
    }
    super.onAttack(creature, skillId, type, damage);

    if (getOwner().isInvul()) {
      damage = 0;
    }
    getOwner().getLifeStats().reduceHp(damage, creature);
    PacketSendUtility.broadcastPacket(getOwner(),
        (AionServerPacket) new SM_ATTACK_STATUS((Creature) getOwner(), type, skillId, damage), true);
  }

  public void useSkill(int skillId, int targetType, float x, float y, float z) {
    Player player = getOwner();

    Skill skill = SkillEngine.getInstance().getSkillFor(player, skillId, player.getTarget());

    if (skill != null) {

      skill.setTargetType(targetType, x, y, z);
      if (!RestrictionsManager.canUseSkill(player, skill)) {
        return;
      }
      skill.useSkill();
    }
  }

  public void onMove() {
    super.onMove();
    addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_UPDATE);
  }

  public void onStopMove() {
    super.onStopMove();
  }

  public void onStartMove() {
    cancelCurrentSkill();
    super.onStartMove();
  }

  public void cancelCurrentSkill() {
    Player player = getOwner();
    Skill castingSkill = player.getCastingSkill();
    if (castingSkill != null) {

      int skillId = castingSkill.getSkillTemplate().getSkillId();
      player.removeSkillCoolDown(skillId);
      player.setCasting(null);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SKILL_CANCEL((Creature) player, skillId));
      PacketSendUtility.sendPacket(player, (AionServerPacket) SM_SYSTEM_MESSAGE.STR_SKILL_CANCELED());
    }
  }

  public void updatePassiveStats() {
    Player player = getOwner();
    for (SkillListEntry skillEntry : player.getSkillList().getAllSkills()) {

      Skill skill = SkillEngine.getInstance().getSkillFor(player, skillEntry.getSkillId(), player.getTarget());
      if (skill != null && skill.isPassive()) {
        skill.useSkill();
      }
    }
  }

  public Player getOwner() {
    return (Player) super.getOwner();
  }

  public void onRestore(HealType healType, int value) {
    super.onRestore(healType, value);
    switch (healType) {

      case DP:
        getOwner().getCommonData().addDp(value);
        break;
    }
  }

  public boolean isDueling(Player player) {
    return DuelService.getInstance().isDueling(player.getObjectId(), getOwner().getObjectId());
  }

  public void updateNearbyQuestList() {
    getOwner().addPacketBroadcastMask(PacketBroadcaster.BroadcastMode.UPDATE_NEARBY_QUEST_LIST);
  }

  public void updateNearbyQuestListImpl() {
    PacketSendUtility.sendPacket(getOwner(), (AionServerPacket) new SM_NEARBY_QUESTS(getOwner().getNearbyQuests()));
  }

  public boolean isInShutdownProgress() {
    return this.isInShutdownProgress;
  }

  public void setInShutdownProgress(boolean isInShutdownProgress) {
    this.isInShutdownProgress = isInShutdownProgress;
  }

  public void onDialogSelect(int dialogId, Player player, int questId) {
    switch (dialogId) {

      case 2:
        PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PRIVATE_STORE(getOwner().getStore()));
        break;
    }
  }

  public void upgradePlayer(int level) {
    Player player = getOwner();

    PlayerStatsTemplate statsTemplate = DataManager.PLAYER_STATS_DATA.getTemplate(player);
    player.setPlayerStatsTemplate(statsTemplate);

    player.getGameStats().doLevelUpgrade();
    player.getLifeStats().synchronizeWithMaxStats();
    player.getLifeStats().updateCurrentStats();

    PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_LEVEL_UPDATE(player.getObjectId(), 0, level),
        true);

    ClassChangeService.showClassChangeDialog(player);

    QuestEngine.getInstance().onLvlUp(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
    updateNearbyQuests();

    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_STATS_INFO(player));

    if (level == 10 && player.getSkillList().getSkillEntry(30001) != null) {

      int skillLevel = player.getSkillList().getSkillLevel(30001);
      player.getSkillList().removeSkill(player, 30001);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_SKILL_LIST(player));
      player.getSkillList().addSkill(player, 30002, skillLevel, true);
    }

    SkillLearnService.addNewSkills(player, false);

    if (player.isInGroup())
      player.getPlayerGroup().updateGroupUIToEvent(player, GroupEvent.UPDATE);
    if (player.isInAlliance())
      AllianceService.getInstance().updateAllianceUIToEvent(player, PlayerAllianceEvent.UPDATE);
    if (player.isLegionMember()) {
      LegionService.getInstance().updateMemberInfo(player);
    }
  }

  public void startProtectionActiveTask() {
    getOwner().setVisualState(CreatureVisualState.BLINKING);
    PacketSendUtility.broadcastPacket(getOwner(), (AionServerPacket) new SM_PLAYER_STATE(getOwner()), true);
    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        PlayerController.this.stopProtectionActiveTask();
      }
    }, 60000L);
    addTask(TaskId.PROTECTION_ACTIVE, task);
  }

  public void stopProtectionActiveTask() {
    cancelTask(TaskId.PROTECTION_ACTIVE);
    Player player = getOwner();
    if (player != null && player.isSpawned()) {

      player.unsetVisualState(CreatureVisualState.BLINKING);
      PacketSendUtility.broadcastPacket(player, (AionServerPacket) new SM_PLAYER_STATE(player), true);
    }
  }

  public void onFlyTeleportEnd() {
    Player player = getOwner();
    player.unsetState(CreatureState.FLIGHT_TELEPORT);
    player.setFlightTeleportId(0);
    player.setFlightDistance(0);
    player.setState(CreatureState.ACTIVE);
    addZoneUpdateMask(ZoneService.ZoneUpdateMode.ZONE_REFRESH);
  }

  public final void addZoneUpdateMask(ZoneService.ZoneUpdateMode mode) {
    this.zoneUpdateMask = (byte) (this.zoneUpdateMask | mode.mask());
    ZoneService.getInstance().add(getOwner());
  }

  public final void removeZoneUpdateMask(ZoneService.ZoneUpdateMode mode) {
    this.zoneUpdateMask = (byte) (this.zoneUpdateMask & (mode.mask() ^ 0xFFFFFFFF));
  }

  public final byte getZoneUpdateMask() {
    return this.zoneUpdateMask;
  }

  public void updateZoneImpl() {
    ZoneService.getInstance().checkZone(getOwner());
  }

  public void refreshZoneImpl() {
    ZoneService.getInstance().findZoneInCurrentMap(getOwner());
  }

  public void checkWaterLevel() {
    Player player = getOwner();
    World world = World.getInstance();
    float z = player.getZ();

    if (player.getLifeStats().isAlreadyDead()) {
      return;
    }
    if (z < world.getWorldMap(player.getWorldId()).getDeathLevel()) {

      die();

      return;
    }
    ZoneInstance currentZone = player.getZoneInstance();
    if (currentZone != null && currentZone.isBreath()) {
      return;
    }

    float playerheight = player.getPlayerAppearance().getHeight() * 1.6F;
    if (z < world.getWorldMap(player.getWorldId()).getWaterLevel() - playerheight) {
      ZoneService.getInstance().startDrowning(player);
    } else {
      ZoneService.getInstance().stopDrowning(player);
    }
  }

  public void createSummon(int npcId, int skillLvl) {
    Player master = getOwner();
    Summon summon = SpawnEngine.getInstance().spawnSummon(master, npcId, skillLvl);
    master.setSummon(summon);
    PacketSendUtility.sendPacket(master, (AionServerPacket) new SM_SUMMON_PANEL(summon));
    PacketSendUtility.broadcastPacket((VisibleObject) summon,
        (AionServerPacket) new SM_EMOTION((Creature) summon, EmotionType.START_EMOTE2));
    PacketSendUtility.broadcastPacket((VisibleObject) summon, (AionServerPacket) new SM_SUMMON_UPDATE(summon));
  }

  public boolean addItems(int itemId, int count) {
    return ItemService.addItems(getOwner(), Collections.singletonList(new QuestItems(itemId, count)));
  }
}
