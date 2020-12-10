package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;

public class _1351EarningMaranasRespect extends QuestHandler {
  private static final int questId = 1351;

  public _1351EarningMaranasRespect() {
    super(Integer.valueOf(1351));
  }

  public void register() {
    this.qe.setNpcQuestData(203965).addOnQuestStart(1351);
    this.qe.setNpcQuestData(203965).addOnTalkEvent(1351);
    this.qe.setNpcQuestData(203983).addOnTalkEvent(1351);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1351);

    if (targetId == 203965) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 203983) {

      if (qs != null && qs.getStatus() == QuestStatus.START) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 33) {

          long itemCount = player.getInventory().getItemCountByItemId(182201321);
          if (itemCount > 9L) {

            ItemService.removeItemFromInventoryByItemId(player, 182201321);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }

          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        }

        return defaultQuestStartDialog(env);
      }
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
