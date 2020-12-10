package quest.sanctum;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _19004PeriklessInsight extends QuestHandler {
  private static final int questId = 19004;

  public _19004PeriklessInsight() {
    super(Integer.valueOf(19004));
  }

  public void register() {
    this.qe.setNpcQuestData(203757).addOnQuestStart(19004);
    this.qe.setNpcQuestData(203757).addOnTalkEvent(19004);
    this.qe.setNpcQuestData(203752).addOnTalkEvent(19004);
    this.qe.setNpcQuestData(203701).addOnTalkEvent(19004);
    this.qe.setNpcQuestData(798500).addOnTalkEvent(19004);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(19004);
    if (targetId == 203757) {
      if (qs == null) {

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

    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203752 && var == 0) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVar(++var);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }
      if (targetId == 203701 && var == 1) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10001) {

          qs.setQuestVar(++var);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }
      if (targetId == 798500 && var == 2) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 10255) {

          qs.setQuestVar(++var);
          updateQuestStatus(player, qs);
          qs.setStatus(QuestStatus.REWARD);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
      if (env.getDialogId().intValue() == 1009) {

        qs.setQuestVar(4);
        updateQuestStatus(player, qs);
        return defaultQuestEndDialog(env);
      }

      return defaultQuestEndDialog(env);
    }
    return false;
  }
}
