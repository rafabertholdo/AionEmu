package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Revoke extends AdminCommand {
  public Revoke() {
    super("revoke");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_REVOKE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length != 2) {

      PacketSendUtility.sendMessage(admin, "syntax //revoke <characterName> <acceslevel | membership>");

      return;
    }
    int type = 0;
    if (params[1].toLowerCase().equals("acceslevel")) {

      type = 1;
    } else if (params[1].toLowerCase().equals("membership")) {

      type = 2;
    } else {

      PacketSendUtility.sendMessage(admin, "syntax //revoke <characterName> <acceslevel | membership>");

      return;
    }
    Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
    if (player == null) {

      PacketSendUtility.sendMessage(admin, "The specified player is not online.");
      return;
    }
    LoginServer.getInstance().sendLsControlPacket(admin.getAcountName(), player.getName(), admin.getName(), 0, type);
  }
}
