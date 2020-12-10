package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_PACKET;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

public class SendRawPacket extends AdminCommand {
  private static final File ROOT = new File("data/packets/");

  private static final Logger logger = Logger.getLogger(SendRawPacket.class);

  public SendRawPacket() {
    super("raw");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SENDRAWPACKET) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length != 1) {

      PacketSendUtility.sendMessage(admin, "Usage: //raw [name]");

      return;
    }
    File file = new File(ROOT, params[0] + ".txt");

    if (!file.exists() || !file.canRead()) {

      PacketSendUtility.sendMessage(admin, "Wrong file selected.");

      return;
    }

    try {
      List<String> lines = FileUtils.readLines(file);

      SM_CUSTOM_PACKET packet = null;

      for (String row : lines) {

        String[] tokens = row.substring(0, 48).trim().split(" ");
        int len = tokens.length;

        for (int i = 0; i < len; i++) {

          if (i == 0) {

            packet = new SM_CUSTOM_PACKET(Integer.valueOf(tokens[i], 16).intValue());
          } else if (i > 2) {

            packet.addElement(SM_CUSTOM_PACKET.PacketElementType.C, "0x" + tokens[i]);
          }
        }
      }

      if (packet != null) {
        PacketSendUtility.sendPacket(admin, (AionServerPacket) packet);
      }
    } catch (IOException e) {

      PacketSendUtility.sendMessage(admin, "An error has occurred.");
      logger.warn("IO Error.", e);
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\SendRawPacket.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */