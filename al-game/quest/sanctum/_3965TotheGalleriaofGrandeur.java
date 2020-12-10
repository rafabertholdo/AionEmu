package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import java.util.Collections;

public class _3965TotheGalleriaofGrandeur extends QuestHandler {
  private static final int questId = 3965;

  public _3965TotheGalleriaofGrandeur() {
    super(Integer.valueOf(3965));
  }

  public void register() {
    this.qe.setNpcQuestData(798311).addOnQuestStart(3965);
    this.qe.setNpcQuestData(798391).addOnTalkEvent(3965);
    this.qe.setNpcQuestData(798390).addOnTalkEvent(3965);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3965);
    if (targetId == 798311) {

      if (env.getDialogId().intValue() == 25)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
      if (env.getDialogId().intValue() == 1002) {

        if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206120, 2))))
          return defaultQuestStartDialog(env);
        return true;
      }

      return defaultQuestStartDialog(env);
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (targetId == 798391) {

      if (qs.getStatus() == QuestStatus.START && var == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          if (player.getInventory().getItemCountByItemId(182206120) > 1L) {

            ItemService.removeItemFromInventoryByItemId(player, 182206120);
            qs.setQuestVar(++var);
            updateQuestStatus(player, qs);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798390) {

      if (qs.getStatus() == QuestStatus.START && var == 1) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          if (ItemService.removeItemFromInventoryByItemId(player, 182206120)) {

            qs.setQuestVar(2);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return defaultQuestEndDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3965TotheGalleriaofGrandeur.class Java compiler version: 6
 * (50.0) JD-Core Version: 1.1.3
 */