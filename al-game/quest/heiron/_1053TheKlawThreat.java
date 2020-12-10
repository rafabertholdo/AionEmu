package quest.heiron;

import com.aionemu.commons.utils.Rnd;
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

public class _1053TheKlawThreat extends QuestHandler {
  private static final int questId = 1053;
  private static final int[] npc_ids = new int[] { 204583, 204502 };

  public _1053TheKlawThreat() {
    super(Integer.valueOf(1053));
  }

  public void register() {
    this.qe.addQuestLvlUp(1053);
    this.qe.setNpcQuestData(700169).addOnKillEvent(1053);
    this.qe.setNpcQuestData(212120).addOnKillEvent(1053);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(1053);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1053);
    boolean lvlCheck = QuestService.checkLevelRequirement(1053, player.getCommonData().getLevel());
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED || !lvlCheck) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(1500);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1053);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 204502) {
        return defaultQuestEndDialog(env);
      }
    } else if (qs.getStatus() != QuestStatus.START) {

      return false;
    }
    if (targetId == 204583) {
      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 33:
          if (var == 1 && QuestService.collectItemCheck(env, true)) {
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10000);
          }
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10001);
        case 1693:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 186));
          return false;
        case 10000:
          if (var == 0) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
        case 10002:
          if (var == 1) {

            qs.setQuestVarById(0, var + 2);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }
    }
    return false;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1053);
    if (qs == null || qs.getStatus() != QuestStatus.START) {
      return false;
    }
    Rnd queen = new Rnd();
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc)
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    Npc npc = (Npc) env.getVisibleObject();

    if (targetId == 700169) {

      int spawn = Rnd.nextInt(5);
      if (spawn == 1) {
        QuestService.addNewSpawn(210040000, 1, 212120, npc.getX(), npc.getY(), npc.getZ(), (byte) 0, true);

        return true;
      }

    } else if (targetId == 212120 && qs.getQuestVarById(0) == 3) {

      qs.setStatus(QuestStatus.REWARD);
      updateQuestStatus(player, qs);
    }
    return false;
  }
}
