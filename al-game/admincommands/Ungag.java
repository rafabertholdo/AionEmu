package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.concurrent.Future;

public class Ungag extends AdminCommand {
  public Ungag() {
    super("ungag");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_GAG) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "Syntax: //ungag <player>");

      return;
    }
    String name = Util.convertName(params[0]);
    Player player = World.getInstance().findPlayer(name);
    if (player == null) {

      PacketSendUtility.sendMessage(admin, "Player " + name + " was not found!");
      PacketSendUtility.sendMessage(admin, "Syntax: //ungag <player>");

      return;
    }
    player.setGagged(false);
    Future<?> task = player.getController().getTask(TaskId.GAG);
    if (task != null)
      player.getController().cancelTask(TaskId.GAG);
    PacketSendUtility.sendMessage(player, "You have been ungagged");

    PacketSendUtility.sendMessage(admin, "Player " + name + " ungagged");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Ungag.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
