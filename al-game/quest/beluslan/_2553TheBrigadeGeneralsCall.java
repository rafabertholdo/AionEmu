package quest.beluslan;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2553TheBrigadeGeneralsCall extends QuestHandler {
  private static final int questId = 2553;

  public _2553TheBrigadeGeneralsCall() {
    super(Integer.valueOf(2553));
  }

  public void register() {
    this.qe.setNpcQuestData(798119).addOnQuestStart(2553);
    this.qe.setNpcQuestData(798119).addOnTalkEvent(2553);
    this.qe.setNpcQuestData(204702).addOnTalkEvent(2553);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2553);
    if (targetId == 798119) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
      if (qs != null && qs.getStatus() == QuestStatus.START) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }

        return defaultQuestEndDialog(env);
      }
      if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    } else if (targetId == 204702) {

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

    } else if (targetId == 798119) {

      if (qs != null) {

        if (env.getDialogId().intValue() == 25 && qs.getStatus() == QuestStatus.START)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        if (env.getDialogId().intValue() == 1009) {

          qs.setQuestVar(3);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return defaultQuestEndDialog(env);
        }

        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
