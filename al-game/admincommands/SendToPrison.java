package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.PunishmentService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.NoSuchElementException;

public class SendToPrison extends AdminCommand {
  public SendToPrison() {
    super("sprison");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_PRISON) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

      return;
    }
    if (params.length == 0 || params.length > 2) {

      PacketSendUtility.sendMessage(admin, "syntax //sprison <player> <delay>");

      return;
    }

    try {
      Player playerToPrison = World.getInstance().findPlayer(Util.convertName(params[0]));
      int delay = Integer.parseInt(params[1]);

      if (playerToPrison != null) {
        PunishmentService.setIsInPrison(playerToPrison, true, delay);
        PacketSendUtility.sendMessage(admin,
            "Player " + playerToPrison.getName() + " sent to prison for " + delay + ".");
      }

    } catch (NoSuchElementException nsee) {

      PacketSendUtility.sendMessage(admin, "Usage: //sprison <player> <delay>");
    } catch (Exception e) {

      PacketSendUtility.sendMessage(admin, "Usage: //sprison <player> <delay>");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\SendToPrison.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
