package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;

public class _1376AMountaineOfTrouble extends QuestHandler {
  private static final int questId = 1376;

  public _1376AMountaineOfTrouble() {
    super(Integer.valueOf(1376));
  }

  public void register() {
    this.qe.setNpcQuestData(203947).addOnQuestStart(1376);
    this.qe.setNpcQuestData(203947).addOnTalkEvent(1376);
    this.qe.setNpcQuestData(203964).addOnTalkEvent(1376);
    this.qe.setNpcQuestData(210976).addOnKillEvent(1376);
    this.qe.setNpcQuestData(210986).addOnKillEvent(1376);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;

    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1376);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {

      if (targetId == 203947) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203964) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1376);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    switch (targetId) {

      case 210976:
        if (var >= 0 && var < 6) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 6) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }
      case 210986:
        if (var >= 0 && var < 6) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 6) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }
}
