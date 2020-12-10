package quest.altgard;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2209TheScribbler extends QuestHandler {
  private static final int questId = 2209;

  public _2209TheScribbler() {
    super(Integer.valueOf(2209));
  }

  public void register() {
    this.qe.setNpcQuestData(203555).addOnQuestStart(2209);
    this.qe.setNpcQuestData(203555).addOnTalkEvent(2209);
    this.qe.setNpcQuestData(203562).addOnTalkEvent(2209);
    this.qe.setNpcQuestData(203592).addOnTalkEvent(2209);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2209);
    if (qs == null) {

      if (targetId == 203555) {
        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 203562:
          if (qs.getQuestVarById(0) == 0) {

            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
            if (env.getDialogId().intValue() == 10000) {

              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
          }
          break;

        case 203572:
          if (qs.getQuestVarById(0) == 1) {

            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
            if (env.getDialogId().intValue() == 10001) {

              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
          }
          break;

        case 203592:
          if (qs.getQuestVarById(0) == 2) {

            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
            if (env.getDialogId().intValue() == 10002) {

              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
          }
          break;

        case 203555:
          if (qs.getQuestVarById(0) == 3) {

            if (env.getDialogId().intValue() == 25)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
            if (env.getDialogId().intValue() == 1009) {

              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return defaultQuestEndDialog(env);
            }

            return defaultQuestEndDialog(env);
          }
          break;
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203555)
        return defaultQuestEndDialog(env);
    }
    return false;
  }
}
