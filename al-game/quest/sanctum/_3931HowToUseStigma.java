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
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Collections;

public class _3931HowToUseStigma extends QuestHandler {
  private static final int questId = 3931;
  private static final int[] npc_ids = new int[] { 798321, 279005, 203711 };

  public _3931HowToUseStigma() {
    super(Integer.valueOf(3931));
  }

  public void register() {
    this.qe.setNpcQuestData(203711).addOnQuestStart(3931);
    this.qe.setQuestItemIds(182206080).add(3931);
    this.qe.setQuestItemIds(182206081).add(3931);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(3931);
    }
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(3931);

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs == null || qs.getStatus() == QuestStatus.NONE) {

      if (targetId == 203711) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        return defaultQuestStartDialog(env);
      }

      return false;
    }

    int var = qs.getQuestVarById(0);

    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203711 && player.getInventory().getItemCountByItemId(182206081) == 1L) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        return defaultQuestEndDialog(env);
      }
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 798321) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 33:
            if (var == 1) {

              if (QuestService.collectItemCheck(env, true)) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                ItemService.addItems(player, Collections.singletonList(new QuestItems(182206080, 1)));
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
              }

              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
            }
          case 10000:
            if (var == 0)
              qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
        }
        return false;
      }
      if (targetId == 279005 && player.getInventory().getItemCountByItemId(182206080) == 1L) {
        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          case 10255:
            if (var == 2)
              ItemService.removeItemFromInventoryByItemId(player, 182206080);
            ItemService.addItems(player, Collections.singletonList(new QuestItems(182206081, 1)));
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            qs.setStatus(QuestStatus.REWARD);
            updateQuestStatus(player, qs);
            return true;
        }

      }
      return false;
    }
    return false;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\quest\sanctum\_3931HowToUseStigma.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */