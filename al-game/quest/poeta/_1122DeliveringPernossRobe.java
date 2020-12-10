package quest.poeta;

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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _1122DeliveringPernossRobe extends QuestHandler {
  private static final int questId = 1122;

  public _1122DeliveringPernossRobe() {
    super(Integer.valueOf(1122));
  }

  public void register() {
    this.qe.setNpcQuestData(203060).addOnQuestStart(1122);
    this.qe.setNpcQuestData(203060).addOnTalkEvent(1122);
    this.qe.setNpcQuestData(790001).addOnTalkEvent(1122);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1122);
    if (targetId == 203060) {

      if (qs == null) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 1002) {

          if (ItemService.addItems(player, Collections.singletonList(new QuestItems(182200216, 1)))) {
            return defaultQuestStartDialog(env);
          }
          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 790001) {

      if (qs != null && qs.getStatus() == QuestStatus.START) {
        long itemCount;

        switch (env.getDialogId().intValue()) {

          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 10000:
            itemCount = player.getInventory().getItemCountByItemId(182200218);
            if (itemCount > 0L) {

              qs.setQuestVar(1);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              ItemService.removeItemFromInventoryByItemId(player, 182200218);
              ItemService.removeItemFromInventoryByItemId(player, 182200216);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1523);
            }

            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);

          case 10001:
            itemCount = player.getInventory().getItemCountByItemId(182200219);
            if (itemCount > 0L) {

              qs.setQuestVar(2);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              ItemService.removeItemFromInventoryByItemId(player, 182200219);
              ItemService.removeItemFromInventoryByItemId(player, 182200216);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
            }

            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);
          case 10002:
            itemCount = player.getInventory().getItemCountByItemId(182200220);
            if (itemCount > 0L) {

              qs.setQuestVar(3);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              ItemService.removeItemFromInventoryByItemId(player, 182200220);
              ItemService.removeItemFromInventoryByItemId(player, 182200216);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
            }

            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1608);
        }
        return defaultQuestStartDialog(env);
      }

      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == -1 || env.getDialogId().intValue() == 1009) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4 + qs.getQuestVars().getQuestVars());
        }
        if (env.getDialogId().intValue() == 17) {

          QuestService.questFinish(env, qs.getQuestVars().getQuestVars() - 1);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }
      }
    }
    return false;
  }
}
