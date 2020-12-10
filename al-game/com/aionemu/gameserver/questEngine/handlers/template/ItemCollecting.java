package com.aionemu.gameserver.questEngine.handlers.template;

import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.QuestTemplate;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class ItemCollecting extends QuestHandler {
  private final int questId;
  private final int startNpcId;
  private final int actionItemId;
  private final int endNpcId;

  public ItemCollecting(int questId, int startNpcId, int actionItemId, int endNpcId) {
    super(Integer.valueOf(questId));
    this.questId = questId;
    this.startNpcId = startNpcId;
    this.actionItemId = actionItemId;
    if (endNpcId != 0) {
      this.endNpcId = endNpcId;
    } else {
      this.endNpcId = startNpcId;
    }
  }

  public void register() {
    this.qe.setNpcQuestData(this.startNpcId).addOnQuestStart(this.questId);
    this.qe.setNpcQuestData(this.startNpcId).addOnTalkEvent(this.questId);
    if (this.actionItemId != 0)
      this.qe.setNpcQuestData(this.actionItemId).addOnTalkEvent(this.questId);
    if (this.endNpcId != this.startNpcId) {
      this.qe.setNpcQuestData(this.endNpcId).addOnTalkEvent(this.questId);
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

      if (targetId == this.startNpcId) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs != null && qs.getStatus() == QuestStatus.START) {

      if (targetId == this.endNpcId) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 33) {
          if (QuestService.collectItemCheck(env, true)) {

            qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        }

      } else if (targetId == this.actionItemId && targetId != 0) {
        return true;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD && targetId == this.endNpcId) {

      return defaultQuestEndDialog(env);
    }
    return false;
  }
}
