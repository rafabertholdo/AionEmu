package quest.morheim;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class _2332MeatyTreats extends QuestHandler {
  private static final int questId = 2332;

  public _2332MeatyTreats() {
    super(Integer.valueOf(2332));
  }

  public void register() {
    this.qe.setNpcQuestData(798084).addOnQuestStart(2332);
    this.qe.setNpcQuestData(798084).addOnTalkEvent(2332);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2332);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {

      if (targetId == 798084) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs != null && qs.getStatus() == QuestStatus.START) {

      if (targetId == 798084) {

        if (env.getDialogId().intValue() == 25) {

          if (QuestService.collectItemCheck(env, true)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        }
        if (env.getDialogId().intValue() >= 10000 && env.getDialogId().intValue() <= 10002) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + env.getDialogId().intValue() - 10000);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), env.getDialogId().intValue() - 9995);
        }
      }
    } else {

      if (env.getDialogId().intValue() == 17 && qs.getStatus() == QuestStatus.REWARD && targetId == 798084) {

        QuestService.questFinish(env, qs.getQuestVarById(0));
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1008);
      }
      if (qs.getStatus() == QuestStatus.COMPLETE && targetId == 798084) {
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1008);
      }
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\morheim\_2332MeatyTreats.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */
