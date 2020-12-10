package com.aionemu.gameserver.questEngine.handlers.template;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.handlers.models.MonsterInfo;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import java.util.Iterator;
import javolution.util.FastMap;

public class MonsterHunt extends QuestHandler {
  private final int questId;
  private final int startNpc;
  private final int endNpc;
  private final FastMap<Integer, MonsterInfo> monsterInfo;

  public MonsterHunt(int questId, int startNpc, int endNpc, FastMap<Integer, MonsterInfo> monsterInfo) {
    super(Integer.valueOf(questId));
    this.questId = questId;
    this.startNpc = startNpc;
    if (endNpc != 0) {
      this.endNpc = endNpc;
    } else {
      this.endNpc = startNpc;
    }
    this.monsterInfo = monsterInfo;
  }

  public void register() {
    this.qe.setNpcQuestData(this.startNpc).addOnQuestStart(this.questId);
    this.qe.setNpcQuestData(this.startNpc).addOnTalkEvent(this.questId);
    for (Iterator<Integer> i$ = this.monsterInfo.keySet().iterator(); i$.hasNext();) {
      int monsterId = ((Integer) i$.next()).intValue();
      this.qe.setNpcQuestData(monsterId).addOnKillEvent(this.questId);
    }
    if (this.endNpc != this.startNpc) {
      this.qe.setNpcQuestData(this.endNpc).addOnTalkEvent(this.questId);
    }
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(this.questId);
    QuestTemplate template = DataManager.QUEST_DATA.getQuestById(this.questId);
    if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE
        && qs.getCompliteCount() <= template.getMaxRepeatCount().intValue())) {

      if (targetId == this.startNpc) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.START) {

      for (MonsterInfo mi : this.monsterInfo.values()) {

        if (mi.getMaxKill() < qs.getQuestVarById(mi.getVarId()))
          return false;
      }
      if (targetId == this.endNpc) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 1009) {

          qs.setStatus(QuestStatus.REWARD);
          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }

        return defaultQuestEndDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.endNpc) {

      return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(this.questId);
    if (qs == null) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false;
    MonsterInfo mi = (MonsterInfo) this.monsterInfo.get(Integer.valueOf(targetId));
    if (mi == null)
      return false;
    if (mi.getMaxKill() <= qs.getQuestVarById(mi.getVarId())) {
      return false;
    }
    qs.setQuestVarById(mi.getVarId(), qs.getQuestVarById(mi.getVarId()) + 1);
    updateQuestStatus(player, qs);
    return true;
  }
}
