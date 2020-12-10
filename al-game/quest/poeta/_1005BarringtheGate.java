package quest.poeta;

import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAY_MOVIE;
import com.aionemu.gameserver.network.aion.serverpackets.SM_USE_OBJECT;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;

public class _1005BarringtheGate extends QuestHandler {
  private static final int questId = 1005;

  public _1005BarringtheGate() {
    super(Integer.valueOf(1005));
  }

  public void register() {
    int[] talkNpcs = { 203067, 203081, 790001, 203085, 203086, 700080, 700081, 700082, 700083, 203067 };
    this.qe.addQuestLvlUp(1005);
    for (int id : talkNpcs) {
      this.qe.setNpcQuestData(id).addOnTalkEvent(1005);
    }
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1005);
    if (qs == null) {
      return false;
    }
    int var = qs.getQuestVarById(0);
    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs.getStatus() == QuestStatus.START) {

      if (targetId == 203067) {

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
      } else if (targetId == 203081) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 1)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
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
      } else if (targetId == 790001) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 2)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);
          case 10002:
            if (var == 2) {

              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
            break;
        }
      } else if (targetId == 203085) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 3)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
          case 10003:
            if (var == 3) {

              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
            break;
        }
      } else if (targetId == 203086) {

        switch (env.getDialogId().intValue()) {

          case 25:
            if (var == 4)
              return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);
          case 10004:
            if (var == 4) {

              qs.setQuestVarById(0, var + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
              return true;
            }
            break;
        }
      } else if (targetId == 700081) {

        if (var == 5) {
          destroy(6, env);
          return false;
        }

      } else if (targetId == 700082) {

        if (var == 6) {
          destroy(7, env);
          return false;
        }

      } else if (targetId == 700083) {

        if (var == 7) {
          destroy(8, env);
          return false;
        }

      } else if (targetId == 700080) {

        if (var == 8) {
          destroy(-1, env);
          return false;
        }

      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203067) {

        if (env.getDialogId().intValue() == -1)
          return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2716);
        return defaultQuestEndDialog(env);
      }
    }
    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1005);
    if (qs == null || qs.getStatus() != QuestStatus.LOCKED)
      return false;
    int[] quests = { 1001, 1002, 1003, 1004 };
    for (int id : quests) {

      QuestState qs2 = player.getQuestStateList().getQuestState(id);
      if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE)
        return false;
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  private void destroy(final int var, QuestEnv env) {
    final int targetObjectId = env.getVisibleObject().getObjectId();
    
    final Player player = env.getPlayer();
    PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 1));
    PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.NEUTRALMODE2, 0, targetObjectId), true);
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            if (player.getTarget().getObjectId() != targetObjectId)
              return; 
            PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_USE_OBJECT(player.getObjectId(), targetObjectId, 3000, 0));
            PacketSendUtility.broadcastPacket(player, (AionServerPacket)new SM_EMOTION((Creature)player, EmotionType.START_LOOT, 0, targetObjectId), true);
            PacketSendUtility.broadcastPacket(player.getTarget(), (AionServerPacket)new SM_EMOTION((Creature)player.getTarget(), EmotionType.EMOTE, 128, 0));
            QuestState qs = player.getQuestStateList().getQuestState(1005);
            if (var != -1) {
              qs.setQuestVarById(0, var);
            } else {
              
              PacketSendUtility.sendPacket(player, (AionServerPacket)new SM_PLAY_MOVIE(0, 21));
              qs.setStatus(QuestStatus.REWARD);
            } 
            _1005BarringtheGate.this.updateQuestStatus(player, qs);
          }
        }3000L);
  }
}
