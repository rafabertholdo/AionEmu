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

public class _1901KrallicPotion extends QuestHandler {
  private static final int questId = 1901;

  public _1901KrallicPotion() {
    super(Integer.valueOf(1901));
  }

  public void register() {
    this.qe.setNpcQuestData(203830).addOnQuestStart(1901);
    this.qe.setNpcQuestData(203830).addOnTalkEvent(1901);
    this.qe.setNpcQuestData(798026).addOnTalkEvent(1901);
    this.qe.setNpcQuestData(798025).addOnTalkEvent(1901);
    this.qe.setNpcQuestData(203131).addOnTalkEvent(1901);
    this.qe.setNpcQuestData(798003).addOnTalkEvent(1901);
    this.qe.setNpcQuestData(203864).addOnTalkEvent(1901);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1901);
    if (targetId == 203830) {

      if (env.getDialogId().intValue() == 25) {
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
      }
      return defaultQuestStartDialog(env);
    }

    if (targetId == 203864) {

      if (env.getDialogId().intValue() == 25)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
      if (env.getDialogId().intValue() == 1009) {

        qs.setQuestVar(7);
        updateQuestStatus(player, qs);
        qs.setStatus(QuestStatus.REWARD);
        return defaultQuestEndDialog(env);
      }
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
        return defaultQuestEndDialog(env);
      }

    } else if (qs != null) {

      if (qs.getStatus() == QuestStatus.START) {

        int var = qs.getQuestVarById(0);
        switch (targetId) {

          case 798026:
            switch (env.getDialogId().intValue()) {

              case 25:
                if (var == 0)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
                if (var == 5)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
              case 1438:
                if (ItemService.decreaseKinah(player, 10000L)) {

                  qs.setQuestVarById(0, var + 1);
                  updateQuestStatus(player, qs);
                  PacketSendUtility.sendPacket(player,
                      (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                  return true;
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1523);
              case 10000:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
              case 10001:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
              case 10006:
                qs.setQuestVarById(0, var + 1);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
            }
            return defaultQuestStartDialog(env);

          case 798025:
            switch (env.getDialogId().intValue()) {

              case 25:
                if (var == 1)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
                if (var == 4)
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
              case 10002:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
              case 10005:
                ItemService.removeItemFromInventoryByItemId(player, 182206000);
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
            }
          case 203131:
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              case 10003:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
            }
          case 798003:
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              case 10004:
                if (player.getInventory().getItemCountByItemId(182206000) == 0L
                    && !ItemService.addItems(player, Collections.singletonList(new QuestItems(182206000, 1)))) {
                  return true;
                }
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

                return true;
            }
            break;
        }
      }
    }
    return false;
  }
}
