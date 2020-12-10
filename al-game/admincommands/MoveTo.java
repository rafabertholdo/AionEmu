package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.WorldMapType;

public class MoveTo extends AdminCommand {
  public MoveTo() {
    super("moveto");
  }

  public void executeCommand(Player admin, String[] params) {
    int worldId;
    float x;
    float y;
    float z;
    if (admin.getAccessLevel() < AdminConfig.COMMAND_MOVETO) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 4) {

      PacketSendUtility.sendMessage(admin, "syntax //moveto worldId X Y Z");

      return;
    }

    try {
      worldId = Integer.parseInt(params[0]);
      x = Float.parseFloat(params[1]);
      y = Float.parseFloat(params[2]);
      z = Float.parseFloat(params[3]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "All the parameters should be numbers");

      return;
    }
    if (WorldMapType.getWorld(worldId) == null) {

      PacketSendUtility.sendMessage(admin, "Illegal WorldId %d " + worldId);
    } else {

      TeleportService.teleportTo(admin, worldId, x, y, z, 0);
      PacketSendUtility.sendMessage(admin, "Teleported to " + x + " " + y + " " + z + " [" + worldId + "]");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\MoveTo.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
