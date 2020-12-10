package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.legion.Legion;
import com.aionemu.gameserver.services.LegionService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class LegionCommand extends AdminCommand {
  public LegionCommand() {
    super("legion");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_LEGION) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length < 2) {

      PacketSendUtility.sendMessage(admin,
          "syntax //legion <disband|setlevel|setpoints|setname> <legion name> <value>");

      return;
    }
    LegionService legionService = LegionService.getInstance();
    Legion legion = legionService.getLegion(params[1].toLowerCase());
    if (legion == null) {

      PacketSendUtility.sendMessage(admin, "The " + params[1].toLowerCase() + " legion does not exist.");

      return;
    }
    if (params[0].toLowerCase().equals("disband")) {

      legionService.disbandLegion(legion);
      PacketSendUtility.sendMessage(admin, "The following legion has been disbanded: " + legion.getLegionName());
    } else if (params[0].toLowerCase().equals("setlevel")) {

      int newLevel = Integer.parseInt(params[2]);

      if (newLevel < 1 || newLevel > 5) {

        PacketSendUtility.sendMessage(admin, "Please use a valid legion level. (1 - 5)");
        return;
      }
      if (legion.getLegionLevel() == newLevel) {

        PacketSendUtility.sendMessage(admin, "Level of legion already is " + newLevel);
        return;
      }
      legionService.changeLevel(legion, newLevel, true);
      PacketSendUtility.sendMessage(admin,
          "The " + legion.getLegionName() + " legion has been leveled up to " + newLevel);

    } else if (params[0].toLowerCase().equals("setpoints")) {

      int newPoints = Integer.parseInt(params[2]);

      if (newPoints <= 0 || newPoints > 2000000000) {

        PacketSendUtility.sendMessage(admin, "Please use valid points amount. (0 - 2.000.000.000)");
        return;
      }
      if (legion.getContributionPoints() == newPoints) {

        PacketSendUtility.sendMessage(admin, "Contribution Points of legion already is " + newPoints);
        return;
      }
      legionService.setContributionPoints(legion, newPoints, true);
      PacketSendUtility.sendMessage(admin,
          "The " + legion.getLegionName() + " legion points have been changed to " + newPoints);

    } else if (params[0].toLowerCase().equals("setname")) {

      String newLegionName = params[2];

      if (!legionService.isValidName(newLegionName)) {

        PacketSendUtility.sendMessage(admin, "Please use a valid legion name!");
        return;
      }
      if (legion.getLegionName().toLowerCase() == newLegionName.toLowerCase()) {

        PacketSendUtility.sendMessage(admin, "Name of legion already is " + newLegionName);
        return;
      }
      legionService.setLegionName(legion, newLegionName, true);
      PacketSendUtility.sendMessage(admin,
          "The " + legion.getLegionName() + " legion's name has been changed to " + newLegionName);
    }
  }
}
