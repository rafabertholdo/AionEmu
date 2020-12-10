package quest.eltnen;

import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import com.aionemu.gameserver.questEngine.handlers.QuestHandler;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.WorldMapType;

public class _1043BalaurConspiracy extends QuestHandler {
  private static final int questId = 1043;

  public _1043BalaurConspiracy() {
    super(Integer.valueOf(1043));
  }

  public void register() {
    this.qe.addQuestLvlUp(1043);
    this.qe.setNpcQuestData(203901).addOnTalkEvent(1043);
    this.qe.setNpcQuestData(204020).addOnTalkEvent(1043);
    this.qe.setNpcQuestData(204044).addOnTalkEvent(1043);
    this.qe.setNpcQuestData(211629).addOnKillEvent(1043);
  }

  public boolean onDialogEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1043);

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (qs == null) {
      return false;
    }
    if (qs.getStatus() == QuestStatus.START) {

      switch (targetId) {

        case 203901:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (qs.getQuestVarById(0) == 0) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1011);
              }

            case 10000:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

              return true;
          }

        case 204020:
          switch (env.getDialogId().intValue()) {

            case 25:
              if (qs.getQuestVarById(0) == 1) {
                return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1352);
              }

            case 10001:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

              return true;
          }

        case 204044:
          switch (env.getDialogId().intValue()) {

            case 25:
              switch (qs.getQuestVarById(0)) {

                case 2:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 1693);

                case 4:
                  return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2034);
              }

            case 10002:
              qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
              updateQuestStatus(player, qs);
              PacketSendUtility.sendPacket(player,
                  (AionServerPacket) new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));

              return true;

            case 10003:
              qs.setQuestVar(4);
              qs.setStatus(QuestStatus.REWARD);
              updateQuestStatus(player, qs);
              TeleportService.teleportTo(player, WorldMapType.ELTNEN.getId(), 2502.1948F, 782.9152F, 408.97723F, 0);

              return true;
          }

          break;
      }

    } else if (qs.getStatus() == QuestStatus.REWARD) {

      if (targetId == 203901) {

        switch (env.getDialogId().intValue()) {

          case 25:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 2375);

          case 1009:
            return sendQuestDialog(player, env.getVisibleObject().getObjectId(), 5);
        }

        return defaultQuestEndDialog(env);
      }
    }

    return false;
  }

  public boolean onLvlUpEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1043);

    if (qs == null || qs.getStatus() != QuestStatus.LOCKED) {
      return false;
    }

    int[] quests = { 1300, 1031, 1032, 1033, 1034, 1036, 1037, 1035, 1038, 1039, 1040, 1041, 1042 };
    for (int id : quests) {

      QuestState qs2 = player.getQuestStateList().getQuestState(id);
      if (qs2 == null || qs2.getStatus() != QuestStatus.COMPLETE) {
        return false;
      }
    }
    qs.setStatus(QuestStatus.START);
    updateQuestStatus(player, qs);
    return true;
  }

  public boolean onKillEvent(QuestEnv env) {
    Player player = env.getPlayer();
    QuestState qs = player.getQuestStateList().getQuestState(1043);

    if (qs == null || qs.getStatus() != QuestStatus.START || qs.getQuestVarById(0) != 3) {
      return false;
    }

    int targetId = 0;
    if (env.getVisibleObject() instanceof Npc) {
      targetId = ((Npc) env.getVisibleObject()).getNpcId();
    }
    if (targetId == 211629) {

      qs.setQuestVarById(0, qs.getQuestVarById(0) + 1);
      updateQuestStatus(player, qs);
      return true;
    }
    return false;
  }
}
