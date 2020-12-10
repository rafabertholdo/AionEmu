package quest.altgard;

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

public class _2020KeepingtheBlackClawTribeinCheck extends QuestHandler {
  private static final int questId = 2020;

  public _2020KeepingtheBlackClawTribeinCheck() {
    super(Integer.valueOf(2020));
  }

  public void register() {
    this.qe.addQuestLvlUp(2020);
    this.qe.setNpcQuestData(203665).addOnTalkEvent(2020);
    this.qe.setNpcQuestData(203668).addOnTalkEvent(2020);
    this.qe.setNpcQuestData(210562).addOnKillEvent(2020);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2020);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 203665:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 0)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              break;
            case 10000:
              if (var == 0) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
              break;
          }
          break;
        case 203668:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (var == 1)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              if (var == 5)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
              if (var == 6)
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              break;
            case 10001:
            case 10002:
              if (var == 1 || var == 5) {

                qs.setQuestVarById(0, var + 1);
                updateQuestStatus(player, qs);
                PacketSendUtility.sendPacket(player,
                    (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                return true;
              }
            case 33:
              if (var == 6) {

                if (QuestService.collectItemCheck(env, true)) {

                  qs.setStatus(QuestStatus.REWARD);
                  updateQuestStatus(player, qs);
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
                }

                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2120);
              }
              break;
          }
          break;
      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203668)
        return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2020);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 210562 && var >= 2 && var < 5) {

      qs.setQuestVarById(0, var + 1);
      updateQuestStatus(player, qs);
      return true;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2020);
    boolean lvlCheck = QuestService.checkLevelRequirement(2020, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
