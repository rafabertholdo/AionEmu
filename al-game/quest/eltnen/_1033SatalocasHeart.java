package quest.eltnen;

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

public class _1033SatalocasHeart extends QuestHandler {
  private static final int questId = 1033;
  private static final int[] mob_ids = new int[] { 210799 };

  public _1033SatalocasHeart() {
    super(Integer.valueOf(1033));
  }

  public void register() {
    this.qe.setNpcQuestData(203900).addOnTalkEvent(1033);
    this.qe.setNpcQuestData(203996).addOnTalkEvent(1033);
    for (int mob_id : mob_ids)
      this.qe.setNpcQuestData(mob_id).addOnKillEvent(1033);
    this.qe.addQuestLvlUp(1033);
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1033);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    switch (targetId) {

      case 210799:
        if (var >= 10 && var < 11) {

          qs.setQuestVarById(0, var + 1);
          updateQuestStatus(player, qs);
          return true;
        }

        if (var == 11) {

          qs.setQuestVar(11);
          updateQuestStatus(player, qs);
          return true;
        }
        break;
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1033);
    boolean lvlCheck = QuestService.checkLevelRequirement(1033, player.getCommonData().getLevel());
    if (qs == null || !lvlCheck || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    QuestState qs = player.getQuestStateList().getQuestState(1033);
    if (qs == null)
      return false;
    if (targetId == 203900) {

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        if (env.getDialogId().intValue() == 10000) {

          qs.setQuestVar(1);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          return true;
        }

        return defaultQuestStartDialog(env);
      }

      if (qs.getStatus() == QuestStatus.REWARD) {
        return defaultQuestEndDialog(env);

      }
    } else if (targetId == 203996) {

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 1) {

        if (env.getDialogId().intValue() == 25)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        if (env.getDialogId().intValue() == 10002) {

          qs.setQuestVar(10);
          updateQuestStatus(player, qs);
          PacketSendUtility.sendPacket(player,
              (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 42));
          return true;
        }

        return defaultQuestStartDialog(env);
      }

      if (qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 11) {

        if (env.getDialogId().intValue() == 25) {

          qs.setQuestVar(qs.getQuestVarById(0) + 1);
          qs.setStatus(QuestStatus.REWARD);
          updateQuestStatus(player, qs);
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2205);
        }

        return defaultQuestStartDialog(env);
      }
    }

    return false;
  }
}
