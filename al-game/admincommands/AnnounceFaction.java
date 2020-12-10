package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.Race;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class AnnounceFaction extends AdminCommand {
    public AnnounceFaction() {
        super("announcefaction");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_ANNOUNCE_FACTION) {

            PacketSendUtility.sendMessage(admin, "You don't have enough rights to execute this command.");

            return;
        }
        if (params.length < 2) {

            PacketSendUtility.sendMessage(admin, "Syntax: //announcefaction <ely | asmo> <message>");
        } else {

            String message = null;

            if (params[0].equals("ely")) {
                message = "Elyos : ";
            } else {
                message = "Asmodians : ";
            }

            for (int i = 1; i < params.length - 1; i++) {
                message = message + params[i] + " ";
            }

            message = message + params[params.length - 1];

            for (Player player : World.getInstance().getAllPlayers()) {

                if (player.getCommonData().getRace() == Race.ELYOS && params[0].equals("ely")) {
                    PacketSendUtility.sendSysMessage(player, message);
                    continue;
                }
                if (player.getCommonData().getRace() == Race.ASMODIANS && params[0].equals("asmo"))
                    PacketSendUtility.sendSysMessage(player, message);
            }
        }
    }
}
