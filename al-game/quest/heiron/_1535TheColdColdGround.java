package quest.heiron;

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

public class _1535TheColdColdGround extends QuestHandler {
  private static final int questId = 1535;

  public _1535TheColdColdGround() {
    super(Integer.valueOf(1535));
  }

  public void register() {
    this.qe.setNpcQuestData(204580).addOnQuestStart(1535);
    this.qe.setNpcQuestData(204580).addOnTalkEvent(1535);
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId != 204580) {
      return false;
    }
    QuestState qs = player.getQuestStateList().getQuestState(1535);
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {

      if (env.getDialogId().intValue() == 25) {
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
      }
      return defaultQuestStartDialog(env);
    }

    if (qs.getStatus() == QuestStatus.START) {

      boolean abexSkins = (player.getInventory().getItemCountByItemId(182201818) > 4L);
      boolean worgSkins = (player.getInventory().getItemCountByItemId(182201819) > 2L);
      boolean karnifSkins = (player.getInventory().getItemCountByItemId(182201820) > 0L);

      switch (env.getDialogId().intValue()) {

        case -1:
        case 25:
          if (abexSkins || worgSkins || karnifSkins)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        case 10000:
          if (abexSkins) {

            qs.setQuestVarById(0, 1);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
          }
          break;
        case 10001:
          if (worgSkins) {

            qs.setQuestVarById(0, 2);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          }
          break;
        case 10002:
          if (karnifSkins) {

            qs.setQuestVarById(0, 3);
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 7);
          }
          break;
      }
      return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      int var = qs.getQuestVarById(0);
      if (var == 1) {

        ItemService.removeItemFromInventoryByItemId(player, 182201818);
        return defaultQuestEndDialog(env);
      }
      if (var == 2) {

        if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(162000010, 5)))) {

          qs.setStatus(QuestStatus.START);
          updateQuestStatus(player, qs);
        } else {

          ItemService.removeItemFromInventoryByItemId(player, 182201819);
        }
        defaultQuestEndDialog(env);
        return true;
      }
      if (var == 3) {

        if (!ItemService.addItems(player, Collections.singletonList(new QuestItems(162000015, 5)))) {

          qs.setStatus(QuestStatus.START);
          updateQuestStatus(player, qs);
        } else {

          ItemService.removeItemFromInventoryByItemId(player, 182201820);
        }
        defaultQuestEndDialog(env);
        return true;
      }
      PacketSendUtility.sendPacket(player,
          (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
    }
    return false;
  }
}
