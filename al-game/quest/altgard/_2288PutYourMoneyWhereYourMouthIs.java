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

public class _2288PutYourMoneyWhereYourMouthIs extends QuestHandler {
  private static final int questId = 2288;

  public _2288PutYourMoneyWhereYourMouthIs() {
    super(Integer.valueOf(2288));
  }

  public void register() {
    this.qe.setNpcQuestData(203621).addOnQuestStart(2288);
    this.qe.setNpcQuestData(203621).addOnTalkEvent(2288);
    this.qe.setNpcQuestData(210564).addOnKillEvent(2288);
    this.qe.setNpcQuestData(210584).addOnKillEvent(2288);
    this.qe.setNpcQuestData(210581).addOnKillEvent(2288);
    this.qe.setNpcQuestData(201047).addOnKillEvent(2288);
    this.qe.setNpcQuestData(210436).addOnKillEvent(2288);
    this.qe.setNpcQuestData(210437).addOnKillEvent(2288);
    this.qe.setNpcQuestData(210440).addOnKillEvent(2288);
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2288);
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
    if (targetId == 210564 || targetId == 210584 || targetId == 210581 || targetId == 210436 || targetId == 201047
        || targetId == 210437 || targetId == 210440) {

      if (var > 0 && var < 4) {

        qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
        updateQuestStatus(player, qs);
        return true;
      }
      if (var == 6) {

        qs.setStatus(QuestStatus.REWARD);
        updateQuestStatus(player, qs);
        PacketSendUtility.sendPacket(player,
            (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
        return true;
      }
    }
    return false;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(2288);
    if (targetId == 203621) {

      if (qs == null || qs.getStatus() == QuestStatus.NONE) {

        if (env.getDialogId().intValue() == 25) {
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        }
        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.START) {

        if (env.getDialogId().intValue() == 25 && qs.getQuestVarById(0) == 4) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        }
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVarById(0, 1);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 0);
        }

        return defaultQuestStartDialog(env);
      }
      if (qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }
}
