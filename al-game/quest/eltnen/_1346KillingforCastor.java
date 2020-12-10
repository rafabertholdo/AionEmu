package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

public class _1346KillingforCastor extends QuestHandler {
  private static final int questId = 1346;
  private static final int[] mob_ids = new int[] { 210898, 210878, 210872, 210844 };

  public _1346KillingforCastor() {
    super(Integer.valueOf(1346));
  }

  public void register() {
    this.qe.setNpcQuestData(203966).addOnQuestStart(1346);
    this.qe.setNpcQuestData(203966).addOnTalkEvent(1346);
    this.qe.setNpcQuestData(203965).addOnTalkEvent(1346);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1346);
    }
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1346);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    switch (targetId) {

      case 210844:
      case 210872:
        if (qs.getQuestVarById(0) < 15) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
      case 210878:
      case 210898:
        if (qs.getQuestVarById(1) < 20) {

          qs.setQuestVarById(1, qs.getQuestVarById(1) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1346);
    if (player.getCommonData().getLevel() < 27)
      return false;
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {

      if (targetId == 203966) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203965) {
        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(1) == 20 && qs.getQuestVarById(0) == 15) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        }

        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203965) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        if (env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
