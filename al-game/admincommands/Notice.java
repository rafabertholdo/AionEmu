package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_MESSAGE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Notice extends AdminCommand {
  public Notice() {
    super("notice");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_NOTICE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "syntax //notice <message>");

      return;
    }
    String message = "";

    try {
      for (int i = 0; i < params.length; i++) {
        message = message + " " + params[i];
      }
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "parameters should be text and number");
      return;
    }
    PacketSendUtility.broadcastPacket(admin,
        (AionServerPacket) new SM_MESSAGE(0, null, "Information : " + message, ChatType.SYSTEM_NOTICE), true);
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Notice.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
