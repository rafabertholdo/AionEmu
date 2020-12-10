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

public class _1111InsomniaMedicine extends QuestHandler {
  private static final int questId = 1111;

  public _1111InsomniaMedicine() {
    super(Integer.valueOf(1111));
  }

  public void register() {
    this.qe.setNpcQuestData(203075).addOnQuestStart(1111);
    this.qe.setNpcQuestData(203075).addOnTalkEvent(1111);
    this.qe.setNpcQuestData(203061).addOnTalkEvent(1111);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1111);
    if (targetId == 203075) {

      if (qs == null) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {

        if (env.getDialogId().intValue() == -1) {

          if (qs.getQuestVarById(0) == 2) {

            ItemService.removeItemFromInventoryByItemId(player, 182200222);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          }
          if (qs.getQuestVarById(0) == 3) {

            ItemService.removeItemFromInventoryByItemId(player, 182200221);
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
          }
          return false;
        }
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), qs.getQuestVarById(0) + 3);
        if (env.getDialogId().intValue() == 17) {
          QuestService.questFinish(env, qs.getQuestVarById(0) - 2);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

      }
    } else if (targetId == 203061) {

      if (env.getDialogId().intValue() == 25) {

        if (qs.getQuestVarById(0) == 0)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (qs.getQuestVarById(0) == 1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
        return false;
      }
      if (env.getDialogId().intValue() == 33) {

        if (QuestService.collectItemCheck(env, true)) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1353);
        }

        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
      }
      if (env.getDialogId().intValue() == 10000) {

        qs.setQuestVarById(0, 2);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        ItemService.addItems(player, Collections.singletonList(new QuestItems(182200222, 1)));
        PacketSendUtility.sendPacket(player,
            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
        return true;
      }
      if (env.getDialogId().intValue() == 10001) {

        qs.setQuestVarById(0, 3);
        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        ItemService.addItems(player, Collections.singletonList(new QuestItems(182200221, 1)));
        PacketSendUtility.sendPacket(player,
            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
        return true;
      }
    }
    return false;
  }
}
