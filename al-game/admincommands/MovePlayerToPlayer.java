package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class MovePlayerToPlayer extends AdminCommand {
  public MovePlayerToPlayer() {
    super("moveplayertoplayer");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_MOVEPLAYERTOPLAYER) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 2) {

      PacketSendUtility.sendMessage(admin,
          "syntax //moveplayertoplayer <characterNameToMove> <characterNameDestination>");

      return;
    }
    Player playerToMove = World.getInstance().findPlayer(Util.convertName(params[0]));
    if (playerToMove == null) {

      PacketSendUtility.sendMessage(admin, "The specified player is not online.");

      return;
    }
    Player playerDestination = World.getInstance().findPlayer(Util.convertName(params[1]));
    if (playerDestination == null) {

      PacketSendUtility.sendMessage(admin, "The destination player is not online.");

      return;
    }
    if (playerToMove == playerDestination) {
      PacketSendUtility.sendMessage(admin, "Cannot move the specified player to their own position.");

      return;
    }
    TeleportService.teleportTo(playerToMove, playerDestination.getWorldId(), playerDestination.getInstanceId(),
        playerDestination.getX(), playerDestination.getY(), playerDestination.getZ(), playerDestination.getHeading(),
        0);

    PacketSendUtility.sendMessage(admin, "Teleported player " + playerToMove.getName() + " to the location of player "
        + playerDestination.getName() + ".");
    PacketSendUtility.sendMessage(playerToMove, "You have been teleported by an administrator.");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\MovePlayerToPlayer.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */