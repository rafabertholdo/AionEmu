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

public class _2012Encroachers extends QuestHandler {
  private static final int questId = 2012;
  private static final int[] mob_ids = new int[] { 210715 };

  public _2012Encroachers() {
    super(Integer.valueOf(2012));
  }

  public void register() {
    this.qe.setNpcQuestData(203559).addOnTalkEvent(2012);
    this.qe.addQuestLvlUp(2012);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(2012);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2012);
    boolean lvlCheck = QuestService.checkLevelRequirement(2012, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2012);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }

    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203559) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            if (var <= 5) {
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
            }
            if (var >= 5) {

              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
            }
          case 10000:
          case 10001:
            if (var == 0 || var == 5) {

              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

              return true;
            }
            break;
        }

      }
    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203559) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2012);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() != QuestStatus.START) {
      return false;
    }
    switch (targetId) {

      case 210715:
        if (var > 0 && var < 4) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        if (var == 4) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }
}
