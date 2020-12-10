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

public class _3966SaluteANewUniform extends QuestHandler {
  private static final int questId = 3966;

  public _3966SaluteANewUniform() {
    super(Integer.valueOf(3966));
  }

  public void register() {
    this.qe.setNpcQuestData(798391).addOnQuestStart(3966);
    this.qe.setNpcQuestData(203994).addOnTalkEvent(3966);
    this.qe.setNpcQuestData(204030).addOnTalkEvent(3966);
    this.qe.setNpcQuestData(204568).addOnTalkEvent(3966);
    this.qe.setNpcQuestData(798391).addOnTalkEvent(3966);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(3966);
    if (targetId == 798391) {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

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

    if (targetId == 203994) {

      if (qs.getStatus() == QuestStatus.START && var == 0) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVar(++var);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 204030) {

      if (qs.getStatus() == QuestStatus.START && var == 1) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 10001) {

          qs.setQuestVar(++var);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 204568) {

      if (qs.getStatus() == QuestStatus.START && var == 2) {
        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        if (env.getDialogId().intValue() == 10002) {

          qs.setQuestVar(++var);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

    } else if (targetId == 798391) {

      if (env.getDialogId().intValue() == -1 && qs.getStatus() == QuestStatus.REWARD)
        return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
      return defaultQuestEndDialog(env);
    }
    return false;
  }
}
