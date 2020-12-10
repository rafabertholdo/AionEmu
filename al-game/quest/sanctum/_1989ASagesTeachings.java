package quest.sanctum;

import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1989ASagesTeachings extends QuestHandler {
  private static final int questId = 1989;

  public _1989ASagesTeachings() {
    super(Integer.valueOf(1989));
  }

  public void register() {
    this.qe.setNpcQuestData(203704).addOnQuestStart(1989);
    this.qe.setNpcQuestData(203705).addOnQuestStart(1989);
    this.qe.setNpcQuestData(203706).addOnQuestStart(1989);
    this.qe.setNpcQuestData(203707).addOnQuestStart(1989);
    this.qe.setNpcQuestData(203771).addOnQuestStart(1989);
    this.qe.setNpcQuestData(203771).addOnTalkEvent(1989);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1989);

    if (qs == null || qs.getStatus() == QuestStatus.NONE) {
      if (targetId == 203771) {

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

      PlayerClass playerClass = player.getCommonData().getPlayerClass();
      switch (targetId) {

        case 203704:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (playerClass == PlayerClass.GLADIATOR || playerClass == PlayerClass.TEMPLAR) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1438);
            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }
        case 203705:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (playerClass == PlayerClass.ASSASSIN || playerClass == PlayerClass.RANGER) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              }
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1779);
            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }
        case 203706:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (playerClass == PlayerClass.SORCERER || playerClass == PlayerClass.SPIRIT_MASTER) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              }
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }
        case 203707:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (playerClass == PlayerClass.CLERIC || playerClass == PlayerClass.CHANTER) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
              }
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2461);
            case 10000:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }
        case 203771:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
              if (var == 2)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
              if (var == 3) {

                if (player.getCommonData().getDp() < 4000) {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3484);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3398);
              }
              if (var == 4) {

                if (player.getCommonData().getDp() < 4000) {
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3825);
                }
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3739);
              }
            case 1009:
              if (var == 3) {

                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 105));
                player.getCommonData().setDp(0);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
              }
              if (var == 4) {

                PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 105));
                player.getCommonData().setDp(0);
                qs.setStatus(QuestStatus.REWARD);
                updateQuestStatus(player, qs);
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
              }

              return defaultQuestEndDialog(env);
            case 10001:
              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
            case 10003:
              qs.setQuestVarById(0, 3);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            case 10004:
              qs.setQuestVarById(0, 4);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203771)
        return defaultQuestEndDialog(env);
    }
    return false;
  }
}
