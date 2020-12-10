package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class MoveToPlayer extends AdminCommand {
  public MoveToPlayer() {
    super("movetoplayer");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_MOVETOPLAYER) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "syntax //movetoplayer characterName");

      return;
    }
    Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
    if (player == null) {

      PacketSendUtility.sendMessage(admin, "The specified player is not online.");

      return;
    }
    if (player == admin) {

      PacketSendUtility.sendMessage(admin, "Cannot use this command on yourself.");

      return;
    }
    TeleportService.teleportTo(admin, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),
        player.getZ(), player.getHeading(), 0);
    PacketSendUtility.sendMessage(admin, "Teleported to player " + player.getName() + ".");
  }
}
