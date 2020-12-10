package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Unstuck extends AdminCommand {
  public Unstuck() {
    super("unstuck");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_UNSTUCK) {
      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
      return;
    }
    if (admin.getLifeStats().isAlreadyDead()) {

      PacketSendUtility.sendMessage(admin, "You dont have execute this command. You die");
      return;
    }
    TeleportService.moveToBindLocation(admin, true, CustomConfig.UNSTUCK_DELAY);
  }
}
