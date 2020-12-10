package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _3933ClassPreceptorConsent extends QuestHandler {
  private static final int questId = 3933;

  public _3933ClassPreceptorConsent() {
    super(Integer.valueOf(3933));
  }

  public void register() {
    this.qe.setNpcQuestData(203701).addOnQuestStart(3933);
    this.qe.setNpcQuestData(203704).addOnTalkEvent(3933);
    this.qe.setNpcQuestData(203705).addOnTalkEvent(3933);
    this.qe.setNpcQuestData(203706).addOnTalkEvent(3933);
    this.qe.setNpcQuestData(203707).addOnTalkEvent(3933);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(3933);
    this.qe.setNpcQuestData(203701).addOnTalkEvent(3933);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3933);

    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 203701) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 4762);
        }
        return defaultQuestStartDialog(env);
      }
    }

    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);

    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 203704:
          switch (env.getDialogId().intValue()) {

            case 25:
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);

            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }

        case 203705:
          if (var == 1) {
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);

              case 10001:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }

          }
        case 203706:
          if (var == 2) {
            switch (env.getDialogId().intValue()) {

              case 25:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

              case 10002:
                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
            }

          }
        case 203707:
          if (var == 3) {
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

          }
        case 203752:
          if (var == 4) {
            switch (env.getDialogId().intValue()) {

              case 25:
                if (player.getInventory().getItemCountByItemId(186000080) >= 1L) {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);

              case 10255:
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);

              case 1009:
                ItemService.removeItemFromInventoryByItemId(player, 186000080);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
            }
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203701)
        return defaultQuestEndDialog(env);
    }
    return false;
  }
}
