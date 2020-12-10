package quest.ishalgen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _2114TheInsectProblem extends QuestHandler {
  private static final int questId = 2114;

  public _2114TheInsectProblem() {
    super(Integer.valueOf(2114));
  }

  public void register() {
    this.qe.setNpcQuestData(203533).addOnQuestStart(2114);
    this.qe.setNpcQuestData(203533).addOnTalkEvent(2114);
    this.qe.setNpcQuestData(210734).addOnKillEvent(2114);
    this.qe.setNpcQuestData(210380).addOnKillEvent(2114);
    this.qe.setNpcQuestData(210381).addOnKillEvent(2114);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2114);
    if (targetId == 203533) {
      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        switch (env.getDialogId().intValue()) {

          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
          case 10000:
            if (QuestService.startQuest(env, QuestStatus.START)) {

              qs = player.getQuestStateList().getQuestState(2114);
              qs.setQuestVar(1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
          case 10001:
            if (QuestService.startQuest(env, QuestStatus.START)) {

              qs = player.getQuestStateList().getQuestState(2114);
              qs.setQuestVar(11);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
            break;
        }
      } else if (qs.getStatus() == QuestStatus.REWARD) {

        int var = qs.getQuestVarById(0);
        switch (env.getDialogId().intValue()) {

          case -1:
            if (var == 10)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
            if (var == 20)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 6);
          case 17:
            if (QuestService.questFinish(env, var / 10 - 1)) {

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

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2114);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START)
      return false;
    switch (targetId) {

      case 210734:
        if (var >= 1 && var < 10) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 10) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }
      case 210380:
      case 210381:
        if (var >= 11 && var < 20) {

          qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 20) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
          return true;
        }
        break;
    }
    return false;
  }
}
