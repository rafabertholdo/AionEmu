package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.quest.QuestItems;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _3967AndusDyeBox extends QuestHandler {
  private static final int questId = 3967;

  public _3967AndusDyeBox() {
    super(Integer.valueOf(3967));
  }

  public void register() {
    this.qe.setNpcQuestData(798391).addOnQuestStart(3967);
    this.qe.setNpcQuestData(798309).addOnTalkEvent(3967);
    this.qe.setNpcQuestData(798391).addOnTalkEvent(3967);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;

    QuestState qs2 = player.getQuestStateList().getQuestState(3966);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
      return false;
    }
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3967);

    if (targetId == 798391) {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == -1) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (targetId == 798309) {

      if (qs.getStatus() == QuestStatus.START && var == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206122, 1)))) {

            qs.setQuestVar(++var);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
          }
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798391 && qs.getStatus() == QuestStatus.REWARD) {

      if (env.getDialogId().intValue() == -1)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
      if (env.getDialogId().intValue() == 1009) {

        ItemService.removeItemFromInventoryByItemId(player, 182206122);
        return defaultQuestEndDialog(env);
      }

      return defaultQuestEndDialog(env);
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3967AndusDyeBox.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */