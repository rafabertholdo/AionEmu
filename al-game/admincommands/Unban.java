package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Unban extends AdminCommand {
  public Unban() {
    super("unban");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_BAN) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "Syntax: //unban <player> [account|ip|full]");

      return;
    }

    String name = Util.convertName(params[0]);
    int accountId = ((PlayerDAO) DAOManager.getDAO(PlayerDAO.class)).getAccountIdByName(name);
    if (accountId == 0) {

      PacketSendUtility.sendMessage(admin, "Player " + name + " was not found!");
      PacketSendUtility.sendMessage(admin, "Syntax: //unban <player> [account|ip|full]");

      return;
    }
    byte type = 3;
    if (params.length > 1) {

      String stype = params[1].toLowerCase();
      if ("account".startsWith(stype)) {
        type = 1;
      } else if ("ip".startsWith(stype)) {
        type = 2;
      } else if ("full".startsWith(stype)) {
        type = 3;
      } else {

        PacketSendUtility.sendMessage(admin, "Syntax: //unban <player> [account|ip|full]");

        return;
      }
    }

    LoginServer.getInstance().sendBanPacket(type, accountId, "", -1, admin.getObjectId());
  }
}
