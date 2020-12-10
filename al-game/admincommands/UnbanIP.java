package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class UnbanIP extends AdminCommand {
  public UnbanIP() {
    super("unbanip");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_BAN) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "Syntax: //unbanip <mask>");

      return;
    }
    LoginServer.getInstance().sendBanPacket((byte) 2, 0, params[0], -1, admin.getObjectId());
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\UnbanIP.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
