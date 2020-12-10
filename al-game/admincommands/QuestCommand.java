package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUEST_ACCEPTED;
import com.aionemu.gameserver.questEngine.model.QuestEnv;
import com.aionemu.gameserver.questEngine.model.QuestState;
import com.aionemu.gameserver.questEngine.model.QuestStatus;
import com.aionemu.gameserver.services.QuestService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class QuestCommand extends AdminCommand {
  public QuestCommand() {
    super("quest");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_QUESTCOMMAND) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "syntax //quest <start|set>");
      return;
    }
    Player target = null;
    VisibleObject creature = admin.getTarget();
    if (admin.getTarget() instanceof Player) {
      target = (Player) creature;
    }

    if (target == null) {

      PacketSendUtility.sendMessage(admin, "Incorrect target!");

      return;
    }
    if (params[0].equals("start")) {
      int id;
      if (params.length != 2) {

        PacketSendUtility.sendMessage(admin, "syntax //quest start <questId>");

        return;
      }

      try {
        id = Integer.valueOf(params[1]).intValue();
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "syntax //quest start <questId>");

        return;
      }
      QuestEnv env = new QuestEnv(null, target, Integer.valueOf(id), Integer.valueOf(0));

      if (QuestService.startQuest(env, QuestStatus.START)) {
        PacketSendUtility.sendMessage(admin, "Quest started.");
      } else {
        PacketSendUtility.sendMessage(admin, "Quest not started.");
      }

    } else if (params[0].equals("set")) {
      int questId, var;

      QuestStatus questStatus;

      try {
        questId = Integer.valueOf(params[1]).intValue();
        questStatus = QuestStatus.valueOf(params[2]);
        var = Integer.valueOf(params[3]).intValue();
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "syntax //quest set <questId status var>");
        return;
      }
      QuestState qs = target.getQuestStateList().getQuestState(questId);
      if (qs == null) {

        PacketSendUtility.sendMessage(admin, "syntax //quest set <questId status var>");
        return;
      }
      qs.setStatus(questStatus);
      qs.setQuestVar(var);
      PacketSendUtility.sendPacket(target,
          (AionServerPacket) new SM_QUEST_ACCEPTED(questId, qs.getStatus(), qs.getQuestVars().getQuestVars()));
    } else {

      PacketSendUtility.sendMessage(admin, "syntax //quest <start|set>");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\QuestCommand.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
