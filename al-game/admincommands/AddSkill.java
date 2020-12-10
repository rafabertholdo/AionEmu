package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class AddSkill extends AdminCommand {
  public AddSkill() {
    super("addskill");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_ADDSKILL) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length != 2) {

      PacketSendUtility.sendMessage(admin, "syntax //addskill <skillId> <skillLevel>");

      return;
    }
    VisibleObject target = admin.getTarget();

    int skillId = 0;
    int skillLevel = 0;

    try {
      skillId = Integer.parseInt(params[0]);
      skillLevel = Integer.parseInt(params[1]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Parameters need to be an integer.");

      return;
    }
    if (target instanceof Player) {

      Player player = (Player) target;
      player.getSkillList().addSkill(player, skillId, skillLevel, true);
      PacketSendUtility.sendMessage(admin, "You have success add skill");
      PacketSendUtility.sendMessage(player, "You have acquire a new skill");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\AddSkill.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */