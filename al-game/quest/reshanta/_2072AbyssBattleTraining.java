package quest.reshanta;

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

public class _2072AbyssBattleTraining extends QuestHandler {
  private static final int questId = 2072;
  private static final int[] npc_ids = new int[] { 278126, 278127, 278128, 278129, 278130, 278131, 278136, 278054 };

  public _2072AbyssBattleTraining() {
    super(Integer.valueOf(2072));
  }

  public void register() {
    this.qe.addQuestLvlUp(2072);
    for (int npc_id : npc_ids) {
      this.qe.setNpcQuestData(npc_id).addOnTalkEvent(2072);
    }
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2072);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }
    QuestState qs2 = player.getQuestStateList().getQuestState(2701);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
      return false;
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2072);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 278054) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 10002);
        if (env.getDialogId().intValue() == 1009)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        return defaultQuestEndDialog(env);
      }
      return false;
    }
    if (qs.getStatus() != QuestStatus.START) {
      return false;
    }
    if (targetId == 278126) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 0)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
        case 1013:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 282));
          break;
        case 10000:
          if (var == 0) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278127) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 1)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
        case 1353:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 283));
          break;
        case 10001:
          if (var == 1) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278128) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 2)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
        case 1694:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 284));
          break;
        case 10002:
          if (var == 2) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278129) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 3)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
        case 2035:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 285));
          break;
        case 10003:
          if (var == 3) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278130) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 4)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
        case 2376:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 286));
          break;
        case 10004:
          if (var == 4) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278131) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 5)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        case 2717:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 287));
          break;
        case 10005:
          if (var == 5) {

            qs.setQuestVarById(0, var + 1);
            updateQuestStatus(player, qs);
            PacketSendUtility.sendPacket(player,
                (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
            return true;
          }
          return false;
      }

    } else if (targetId == 278136) {

      switch (env.getDialogId().intValue()) {

        case 25:
          if (var == 6)
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 3057);
        case 3058:
          PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 288));
          break;
        case 10255:
          if (var == 6) {

            qs.setStatus(QuestStatus.REWARD);
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
}
