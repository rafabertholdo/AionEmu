package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.PunishmentService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.NoSuchElementException;

public class RemoveFromPrison extends AdminCommand {
  public RemoveFromPrison() {
    super("rprison");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_PRISON) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

      return;
    }
    if (params.length == 0 || params.length > 2) {

      PacketSendUtility.sendMessage(admin, "syntax //rprison <player>");

      return;
    }

    try {
      Player playerFromPrison = World.getInstance().findPlayer(Util.convertName(params[0]));

      if (playerFromPrison != null) {
        PunishmentService.setIsInPrison(playerFromPrison, false, 0L);
        PacketSendUtility.sendMessage(admin, "Player " + playerFromPrison.getName() + " removed from prison.");
      }

    } catch (NoSuchElementException nsee) {

      PacketSendUtility.sendMessage(admin, "Usage: //rprison <player>");
    } catch (Exception e) {

      PacketSendUtility.sendMessage(admin, "Usage: //rprison <player>");
    }
  }
}
