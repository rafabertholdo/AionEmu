package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Promote extends AdminCommand {
  public Promote() {
    super("promote");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_PROMOTE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length != 3) {

      PacketSendUtility.sendMessage(admin, "syntax //promote <characterName> <acceslevel | membership> <mask> ");

      return;
    }
    int mask = 0;

    try {
      mask = Integer.parseInt(params[2]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Only number!");

      return;
    }
    int type = 0;
    if (params[1].toLowerCase().equals("acceslevel")) {

      type = 1;
      if (mask > 3 || mask < 0) {

        PacketSendUtility.sendMessage(admin, "accesslevel can be 0, 1, 2 or 3");

        return;
      }
    } else if (params[1].toLowerCase().equals("membership")) {

      type = 2;
      if (mask > 1 || mask < 0) {

        PacketSendUtility.sendMessage(admin, "membership can be 0 or 1");

        return;
      }
    } else {
      PacketSendUtility.sendMessage(admin, "syntax //promote <characterName> <acceslevel | membership> <mask>");

      return;
    }
    Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
    if (player == null) {

      PacketSendUtility.sendMessage(admin, "The specified player is not online.");
      return;
    }
    LoginServer.getInstance().sendLsControlPacket(admin.getAcountName(), player.getName(), admin.getName(), mask, type);
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Promote.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
