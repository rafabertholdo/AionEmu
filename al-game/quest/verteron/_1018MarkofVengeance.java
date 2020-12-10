package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;

public class _1018MarkofVengeance extends QuestHandler {
  private static final int questId = 1018;

  public _1018MarkofVengeance() {
    super(Integer.valueOf(1018));
  }

  public void register() {
    this.qe.addQuestLvlUp(1018);
    this.qe.setNpcQuestData(203098).addOnTalkEvent(1018);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1018);
    boolean lvlCheck = QuestService.checkLevelRequirement(1018, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1018);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 203098 && qs.getStatus() == QuestStatus.START) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        case 33:
          if (QuestService.collectItemCheck(env, true)) {

            qs.setQuestVarById(0, var + 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1097);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203098)
        return defaultQuestEndDialog(env);
    }
    return false;
  }
}
