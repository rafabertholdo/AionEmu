package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RESURRECT;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Resurrect extends AdminCommand {
  public Resurrect() {
    super("rez");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_RESURRECT) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

      return;
    }
    VisibleObject target = admin.getTarget();
    if (target == null) {

      PacketSendUtility.sendMessage(admin, "No target selected.");

      return;
    }
    if (!(target instanceof Player)) {

      PacketSendUtility.sendMessage(admin, "You can only resurrect other players.");

      return;
    }
    Player player = (Player) target;
    if (!player.getLifeStats().isAlreadyDead()) {

      PacketSendUtility.sendMessage(admin, "That player is already alive.");

      return;
    }

    if (params == null || params.length == 0 || "prompt".startsWith(params[0])) {

      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_RESURRECT((Creature) admin));

      return;
    }
    if ("instant".startsWith(params[0])) {

      player.getReviveController().skillRevive();

      return;
    }
    PacketSendUtility.sendMessage(admin, "[Resurrect] Usage: target player and use //rez <instant|prompt>");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Resurrect.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
