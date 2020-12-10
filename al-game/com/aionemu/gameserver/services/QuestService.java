package com.aionemu.gameserver.services;

import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.dataholders.QuestsData;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.drop.DropItem;
import com.aionemu.gameserver.model.drop.DropTemplate;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerCommonData;
import com.aionemu.gameserver.model.gameobjects.player.SkillListEntry;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.model.templates.quest.CollectItem;
import com.aionemu.gameserver.model.templates.quest.CollectItems;
import com.aionemu.gameserver.model.templates.quest.QuestDrop;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.model.templates.quest.QuestWorkItems;
import com.aionemu.gameserver.model.templates.quest.Rewards;
import com.aionemu.gameserver.model.templates.spawn.SpawnTemplate;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUBE_UPDATE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.QuestEngine;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.spawnengine.SpawnEngine;
import com.aionemu.gameserver.utils.MathUtil;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;

public final class QuestService {
  static QuestsData questsData = DataManager.QUEST_DATA;

  public static boolean questFinish(QuestEnv env) {
    return questFinish(env, 0);
  }

  public static boolean questFinish(QuestEnv env, int reward) {
    Player player = env.getPlayer();
    int id = env.getQuestId().intValue();
    QuestState qs = player.getQuestStateList().getQuestState(id);
    if (qs == null || qs.getStatus() != QuestStatus.REWARD)
      return false;
    QuestTemplate template = questsData.getQuestById(id);
    Rewards rewards = template.getRewards().get(reward);
    List<QuestItems> questItems = new ArrayList<QuestItems>();
    questItems.addAll(rewards.getRewardItem());

    int dialogId = env.getDialogId().intValue();
    if (dialogId != 17 && dialogId != 0) {
      if (template.isUseClassReward()) {

        QuestItems classRewardItem = null;
        PlayerClass playerClass = player.getCommonData().getPlayerClass();
        switch (playerClass) {

          case ASSASSIN:
            classRewardItem = template.getAssassinSelectableReward().get(dialogId - 8);
            break;
          case CHANTER:
            classRewardItem = template.getChanterSelectableReward().get(dialogId - 8);
            break;
          case CLERIC:
            classRewardItem = template.getPriestSelectableReward().get(dialogId - 8);
            break;
          case GLADIATOR:
            classRewardItem = template.getFighterSelectableReward().get(dialogId - 8);
            break;
          case RANGER:
            classRewardItem = template.getRangerSelectableReward().get(dialogId - 8);
            break;
          case SORCERER:
            classRewardItem = template.getWizardSelectableReward().get(dialogId - 8);
            break;
          case SPIRIT_MASTER:
            classRewardItem = template.getElementalistSelectableReward().get(dialogId - 8);
            break;
          case TEMPLAR:
            classRewardItem = template.getKnightSelectableReward().get(dialogId - 8);
            break;
        }
        if (classRewardItem != null) {
          questItems.add(classRewardItem);
        }
      } else {

        QuestItems selectebleRewardItem = rewards.getSelectableRewardItem().get(dialogId - 8);
        if (selectebleRewardItem != null)
          questItems.add(selectebleRewardItem);
      }
    }
    if (ItemService.addItems(player, questItems)) {

      if (rewards.getGold() != null) {
        ItemService.increaseKinah(player, (player.getRates().getQuestKinahRate() * rewards.getGold().intValue()));
      }
      if (rewards.getExp() != null) {

        int rewardExp = player.getRates().getQuestXpRate() * rewards.getExp().intValue();
        player.getCommonData().addExp(rewardExp);
      }

      if (rewards.getTitle() != null) {
        player.getTitleList().addTitle(rewards.getTitle().intValue());
      }

      if (rewards.getRewardAbyssPoint() != null) {
        player.getCommonData().addAp(rewards.getRewardAbyssPoint().intValue());
      }

      if (rewards.getExtendInventory() != null) {
        if (rewards.getExtendInventory().intValue() == 1) {
          CubeExpandService.expand(player);
        } else if (rewards.getExtendInventory().intValue() == 2) {
          WarehouseService.expand(player);
        }
      }
      if (rewards.getExtendStigma() != null) {

        PlayerCommonData pcd = player.getCommonData();
        pcd.setAdvencedStigmaSlotSize(pcd.getAdvencedStigmaSlotSize() + 1);
        PacketSendUtility.sendPacket(player,
            (AionServerPacket) new SM_CUBE_UPDATE(player, 6, pcd.getAdvencedStigmaSlotSize()));
      }

      QuestWorkItems qwi = questsData.getQuestById(id).getQuestWorkItems();

      if (qwi != null) {

        long count = 0L;
        for (QuestItems qi : qwi.getQuestWorkItem()) {

          if (qi != null) {
            ItemService.decreaseItemCountByItemId(player, qi.getItemId().intValue(), count);
          }
        }
      }

      QuestEngine.getInstance().onQuestFinish(env);
      qs.setStatus(QuestStatus.COMPLETE);
      qs.setCompliteCount(qs.getCompliteCount() + 1);
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_QUEST_ACCEPTED(id, qs.getStatus(), qs.getQuestVars().getQuestVars()));
      player.getController().updateNearbyQuests();
      QuestEngine.getInstance().onLvlUp(env);
      return true;
    }
    return true;
  }

  public static boolean checkStartCondition(QuestEnv env) {
    Player player = env.getPlayer();
    QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
    if (template.getRacePermitted() != null) {
      if (template.getRacePermitted() != player.getCommonData().getRace()) {
        return false;
      }
    }

    if (player.getLevel() < template.getMinlevelPermitted().intValue() - 2) {
      return false;
    }

    if (template.getClassPermitted().size() != 0) {
      if (!template.getClassPermitted().contains(player.getCommonData().getPlayerClass())) {
        return false;
      }
    }
    if (template.getGenderPermitted() != null) {
      if (template.getGenderPermitted() != player.getGender()) {
        return false;
      }
    }
    for (Iterator<Integer> i$ = template.getFinishedQuestConds().iterator(); i$.hasNext();) {
      int questId = ((Integer) i$.next()).intValue();

      QuestState questState = player.getQuestStateList().getQuestState(questId);
      if (questState == null || questState.getStatus() != QuestStatus.COMPLETE) {
        return false;
      }
    }

    if (template.getCombineSkill() != null) {

      SkillListEntry skill = player.getSkillList().getSkillEntry(template.getCombineSkill().intValue());
      if (skill == null)
        return false;
      if (skill.getSkillLevel() < template.getCombineSkillPoint().intValue()
          || skill.getSkillLevel() - 40 > template.getCombineSkillPoint().intValue())
        return false;
      return true;
    }

    QuestState qs = player.getQuestStateList().getQuestState(template.getId());
    if (qs != null && qs.getStatus().value() > 0) {
      if (qs.getStatus() == QuestStatus.COMPLETE && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue()) {
        return true;
      }

      return false;
    }

    return true;
  }

  public static boolean startQuest(QuestEnv env, QuestStatus questStatus) {
    Player player = env.getPlayer();
    int id = env.getQuestId().intValue();
    QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
    if (questStatus != QuestStatus.LOCKED) {

      if (!checkStartCondition(env)) {
        return false;
      }
      if (player.getLevel() < template.getMinlevelPermitted().intValue()) {

        return false;
      }
    }
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_QUEST_ACCEPTED(id, questStatus.value(), 0));
    QuestState qs = player.getQuestStateList().getQuestState(id);
    if (qs == null) {

      qs = new QuestState(template.getId(), questStatus, 0, 0);
      player.getQuestStateList().addQuest(id, qs);

    } else if (template.getMaxRepeatCount().intValue() >= qs.getCompliteCount()) {

      qs.setStatus(questStatus);
      qs.setQuestVar(0);
    }

    player.getController().updateNearbyQuests();
    return true;
  }

  public boolean questComplite(QuestEnv env) {
    Player player = env.getPlayer();
    int id = env.getQuestId().intValue();
    QuestState qs = player.getQuestStateList().getQuestState(id);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
    qs.setStatus(QuestStatus.REWARD);
    PacketSendUtility.sendPacket(player,
        (AionServerPacket) new SM_QUEST_ACCEPTED(id, qs.getStatus(), qs.getQuestVars().getQuestVars()));
    player.getController().updateNearbyQuests();
    return true;
  }

  public static boolean collectItemCheck(QuestEnv env, boolean removeItem) {
    Player player = env.getPlayer();
    int id = env.getQuestId().intValue();
    QuestState qs = player.getQuestStateList().getQuestState(id);
    if (qs == null)
      return false;
    QuestTemplate template = questsData.getQuestById(env.getQuestId().intValue());
    CollectItems collectItems = template.getCollectItems();
    if (collectItems == null)
      return true;
    for (CollectItem collectItem : collectItems.getCollectItem()) {

      long count = player.getInventory().getItemCountByItemId(collectItem.getItemId().intValue());
      if (collectItem.getCount().intValue() > count)
        return false;
    }
    if (removeItem) {
      for (CollectItem collectItem : collectItems.getCollectItem()) {
        ItemService.decreaseItemCountByItemId(player, collectItem.getItemId().intValue(),
            collectItem.getCount().intValue());
      }
    }
    return true;
  }

  public static VisibleObject addNewSpawn(int worldId, int instanceId, int templateId, float x, float y, float z,
      byte heading, boolean noRespawn) {
    SpawnTemplate spawn = SpawnEngine.getInstance().addNewSpawn(worldId, instanceId, templateId, x, y, z, heading, 0, 0,
        noRespawn);
    return SpawnEngine.getInstance().spawnObject(spawn, instanceId);
  }

  public static void getQuestDrop(Set<DropItem> droppedItems, int index, Npc npc, Player player) {
    List<QuestDrop> drops = QuestEngine.getInstance().getQuestDrop(npc.getNpcId());
    if (drops.isEmpty())
      return;
    List<Player> players = new ArrayList<Player>();
    if (player.isInGroup()) {

      for (Player member : player.getPlayerGroup().getMembers()) {
        if (MathUtil.isInRange((VisibleObject) member, (VisibleObject) npc, GroupConfig.GROUP_MAX_DISTANCE)) {
          players.add(member);
        }
      }

    } else {

      players.add(player);
    }
    for (QuestDrop drop : drops) {

      for (Player member : players) {

        if (isDrop(member, drop)) {

          DropItem item = new DropItem(new DropTemplate(drop.getNpcId().intValue(), drop.getItemId().intValue(), 1, 1,
              drop.getChance().intValue()));
          item.setPlayerObjId(member.getObjectId());
          item.setIndex(index++);
          item.setCount(1L);
          droppedItems.add(item);
          if (!drop.isDropEachMember().booleanValue()) {
            break;
          }
        }
      }
    }
  }

  private static boolean isDrop(Player player, QuestDrop drop) {
    if (Rnd.get() * 100.0F > drop.getChance().intValue())
      return false;
    int questId = drop.getQuestId().intValue();
    QuestState qs = player.getQuestStateList().getQuestState(questId);
    if (qs == null || qs.getStatus() != QuestStatus.START)
      return false;
    QuestTemplate template = questsData.getQuestById(questId);
    CollectItems collectItems = template.getCollectItems();
    if (collectItems == null) {
      return true;
    }
    for (CollectItem collectItem : collectItems.getCollectItem()) {

      int collectItemId = collectItem.getItemId().intValue();
      int dropItemId = drop.getItemId().intValue();
      if (collectItemId != dropItemId)
        continue;
      long count = player.getInventory().getItemCountByItemId(collectItemId);
      if (collectItem.getCount().intValue() > count)
        return true;
    }
    return false;
  }

  public static boolean checkLevelRequirement(int questId, int playerLevel) {
    QuestTemplate template = questsData.getQuestById(questId);
    return (playerLevel >= template.getMinlevelPermitted().intValue());
  }

  public static boolean questTimerStart(QuestEnv env, int timeInSeconds) {
    final Player player = env.getPlayer();
    final int id = env.getQuestId().intValue();

    
    Future<?> task = ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          
          public void run()
          {
            QuestEngine.getInstance().onQuestTimerEnd(new QuestEnv(null, player, Integer.valueOf(0), Integer.valueOf(0)));
            QuestEngine.getInstance().deleteQuest(player, id);
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id));
            player.getController().updateNearbyQuests();
          }
        }(timeInSeconds * 1000));
    player.getController().addTask(TaskId.QUEST_TIMER, task);
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_QUEST_ACCEPTED(id, timeInSeconds));
    return true;
  }

  public static boolean questTimerEnd(QuestEnv env) {
    Player player = env.getPlayer();
    int id = env.getQuestId().intValue();

    player.getController().cancelTask(TaskId.QUEST_TIMER);
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_QUEST_ACCEPTED(id, 0));
    return true;
  }
}
