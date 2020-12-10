package quest.verteron;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;

public class _1013HuntingLepharistRevolutionaries extends QuestHandler {
  private static final int questId = 1013;
  private static final int[] mob_ids = new int[] { 210688, 210316 };

  public _1013HuntingLepharistRevolutionaries() {
    super(Integer.valueOf(1013));
  }

  public void register() {
    this.qe.setNpcQuestData(203126).addOnTalkEvent(1013);
    this.qe.addQuestLvlUp(1013);
    for (int mob_id : mob_ids) {
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1013);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1013);
    boolean lvlCheck = QuestService.checkLevelRequirement(1013, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1013);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203126) {
        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
            if (var == 11)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
            if (var >= 12) {

              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
            }
          case 1012:
            PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 25));
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1012);
          case 10000:
          case 10001:
            if (var == 0 || var == 11) {

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

      if (targetId == 203126)
        return defaultQuestEndDialog(env);
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1013);
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

      case 210688:
        if (var >= 1 && var <= 11) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
      case 210316:
        if (var == 12) {

          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }
}
