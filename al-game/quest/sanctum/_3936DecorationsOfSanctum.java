package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;

public class _3936DecorationsOfSanctum extends QuestHandler {
  private static final int questId = 3936;

  public _3936DecorationsOfSanctum() {
    super(Integer.valueOf(3936));
  }

  public void register() {
    this.qe.setNpcQuestData(203710).addOnQuestStart(3936);
    this.qe.setNpcQuestData(203710).addOnTalkEvent(3936);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3936);

    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 203710) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START)

    {
      switch (targetId)

      {
        case 203710:
          switch (env.getDialogId().intValue()) {

            case 25:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

            case 10000:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);

            case 33:
              if (player.getInventory().getItemCountByItemId(182206091) >= 10L
                  && player.getInventory().getItemCountByItemId(182206092) >= 10L
                  && player.getInventory().getItemCountByItemId(182206093) >= 10L
                  && player.getInventory().getItemCountByItemId(182206094) >= 10L) {

                ItemService.removeItemFromInventoryByItemId(player, 182206091);
                ItemService.removeItemFromInventoryByItemId(player, 182206092);
                ItemService.removeItemFromInventoryByItemId(player, 182206093);
                ItemService.removeItemFromInventoryByItemId(player, 182206094);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
              }

              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
          }

          return false;
      }
      return defaultQuestStartDialog(env);
    }
    if (qs.getStatus() == QuestStatus.REWARD)
      if (targetId == 203710)
        return defaultQuestEndDialog(env);
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3936DecorationsOfSanctum.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */
