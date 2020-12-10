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

public class _3968PalentinesRequest extends QuestHandler {
  private static final int questId = 3968;

  public _3968PalentinesRequest() {
    super(Integer.valueOf(3968));
  }

  public void register() {
    this.qe.setNpcQuestData(798390).addOnQuestStart(3968);
    this.qe.setNpcQuestData(798176).addOnTalkEvent(3968);
    this.qe.setNpcQuestData(204528).addOnTalkEvent(3968);
    this.qe.setNpcQuestData(203927).addOnTalkEvent(3968);
    this.qe.setNpcQuestData(798390).addOnTalkEvent(3968);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3968);
    if (targetId == 798390) {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (targetId == 798176) {

      if (qs.getStatus() == QuestStatus.START && var == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206123, 1)))) {

            qs.setQuestVar(++var);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          }

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 204528) {

      if (qs.getStatus() == QuestStatus.START && var == 1) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 10001) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206124, 1)))) {

            qs.setQuestVar(++var);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          }

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 203927) {

      if (qs.getStatus() == QuestStatus.START && var == 2) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        if (env.getDialogId().intValue() == 10002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182206125, 1)))) {

            qs.setQuestVar(++var);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          }

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798390) {

      if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
      if (env.getDialogId().intValue() == 1009) {

        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        ItemService.removeItemFromInventoryByItemId(player, 182206123);
        ItemService.removeItemFromInventoryByItemId(player, 182206124);
        ItemService.removeItemFromInventoryByItemId(player, 182206125);
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
 * !\quest\sanctum\_3968PalentinesRequest.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */