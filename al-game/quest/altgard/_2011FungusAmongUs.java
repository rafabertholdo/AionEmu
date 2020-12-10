package quest.altgard;

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

public class _2011FungusAmongUs extends QuestHandler {
  private static final int questId = 2011;

  public _2011FungusAmongUs() {
    super(Integer.valueOf(2011));
  }

  public void register() {
    int[] talkNpcs = { 203558, 203572, 203558 };
    this.qe.setNpcQuestData(700092).addOnKillEvent(2011);
    this.qe.addQuestLvlUp(2011);
    for (int id : talkNpcs) {
      this.qe.setNpcQuestData(id).addOnTalkEvent(2011);
    }
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2011);
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
    if (targetId == 700092) {

      if (var > 0 && var < 6) {

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
    QuestState qs = player.getQuestStateList().getQuestState(2011);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203558) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 0)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
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
      } else if (targetId == 203572) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
          case 1352:
            PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_PLAY_MOVIE(0, 60));
            break;
          case 10001:
            if (var == 1) {

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

      if (targetId == 203558) {
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(2011);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    QuestState qs2 = player.getQuestStateList().getQuestState(2200);
    if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
      return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }
}
