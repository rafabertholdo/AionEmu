package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class MoveToNpc extends AdminCommand {
  public MoveToNpc() {
    super("movetonpc");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_MOVETONPC) {

      PacketSendUtility.sendMessage(admin, "You don't have enough privileges to use that command!");
      return;
    }
    int npcId = 0;

    try {
      npcId = Integer.valueOf(params[0]).intValue();
      TeleportService.teleportToNpc(admin, npcId);
    } catch (ArrayIndexOutOfBoundsException e) {

      PacketSendUtility.sendMessage(admin, "syntax //movetonpc <npc_id>");
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Numbers only!");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\MoveToNpc.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
