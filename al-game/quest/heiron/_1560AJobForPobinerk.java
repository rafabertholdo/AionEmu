package quest.heiron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1560AJobForPobinerk extends QuestHandler {
  private static final int questId = 1560;

  public _1560AJobForPobinerk() {
    super(Integer.valueOf(1560));
  }

  public void register() {
    this.qe.setNpcQuestData(798103).addOnQuestStart(1560);
    this.qe.setNpcQuestData(798103).addOnTalkEvent(1560);
    this.qe.setNpcQuestData(798026).addOnTalkEvent(1560);
    this.qe.setNpcQuestData(798357).addOnTalkEvent(1560);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1560);
    if (targetId == 798103) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.START) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          qs.setQuestVar(3);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        }

        return defaultQuestEndDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    } else if (targetId == 798026) {

      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798357) {

      if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }
    }

    return false;
  }
}
