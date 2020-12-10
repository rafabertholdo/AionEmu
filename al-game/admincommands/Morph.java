package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TRANSFORM;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Morph extends AdminCommand {
  public Morph() {
    super("morph");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_MORPH) {

      PacketSendUtility.sendMessage(admin, "You do not have enough rights to execute this command.");

      return;
    }
    if (params == null || params.length != 1) {

      PacketSendUtility.sendMessage(admin, "syntax //morph <NPC Id | cancel> ");

      return;
    }
    Player target = admin;
    int param = 0;

    if (admin.getTarget() instanceof Player) {
      target = (Player) admin.getTarget();
    }
    if (!"cancel".startsWith(params[0].toLowerCase())) {

      try {

        param = Integer.parseInt(params[0]);

      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "Parameter must be an integer, or cancel.");

        return;
      }
    }
    if ((param != 0 && param < 200000) || param > 298021) {

      PacketSendUtility.sendMessage(admin, "Something wrong with the NPC Id!");

      return;
    }
    target.setTransformedModelId(param);
    PacketSendUtility.broadcastPacketAndReceive((VisibleObject) target,
        (AionServerPacket) new SM_TRANSFORM((Creature) target));

    if (param == 0) {

      if (target.equals(admin)) {
        PacketSendUtility.sendMessage(target, "Morph successfully cancelled.");
      } else {
        PacketSendUtility.sendMessage(target, "Your morph has been cancelled by " + admin.getName() + ".");
        PacketSendUtility.sendMessage(admin, "You have cancelled " + target.getName() + "'s morph.");

      }

    } else if (target.equals(admin)) {

      PacketSendUtility.sendMessage(target, "Successfully morphed to npcId " + param + ".");
    } else {

      PacketSendUtility.sendMessage(target, admin.getName() + " morphs you into an NPC form.");
      PacketSendUtility.sendMessage(admin, "You morph " + target.getName() + " to npcId " + param + ".");
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Morph.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */