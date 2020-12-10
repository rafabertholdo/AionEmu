package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CUSTOM_PACKET;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class SendFakeServerPacket extends AdminCommand {
  public SendFakeServerPacket() {
    super("fsc");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SENDFAKESERVERPACKET) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length < 3) {

      PacketSendUtility.sendMessage(admin, "Incorrent number of params in //fsc command");

      return;
    }
    int id = Integer.decode(params[0]).intValue();
    String format = "";

    if (params.length > 1) {
      format = params[1];
    }
    SM_CUSTOM_PACKET packet = new SM_CUSTOM_PACKET(id);

    int i = 0;
    for (char c : format.toCharArray()) {

      packet.addElement(SM_CUSTOM_PACKET.PacketElementType.getByCode(c), params[i + 2]);
      i++;
    }
    PacketSendUtility.sendPacket(admin, (AionServerPacket) packet);
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\SendFakeServerPacket.class Java compiler version: 6 (50.0)
 * JD-Core Version: 1.1.3
 */