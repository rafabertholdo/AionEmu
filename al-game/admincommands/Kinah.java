package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Kinah extends AdminCommand {
    public Kinah() {
        super("kinah");
    }

    public void executeCommand(Player admin, String[] params) {
        long l;
        Player receiver;
        if (admin.getAccessLevel() < AdminConfig.COMMAND_KINAH) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

            return;
        }
        if (params == null || params.length < 1 || params.length > 2) {

            PacketSendUtility.sendMessage(admin, "syntax //kinah [player] <quantity>");

            return;
        }

        if (params.length == 1) {

            receiver = admin;

            try {
                l = Integer.parseInt(params[0]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "Kinah value must be an integer.");

                return;
            }
        } else {
            receiver = World.getInstance().findPlayer(Util.convertName(params[0]));

            if (receiver == null) {

                PacketSendUtility.sendMessage(admin, "Could not find a player by that name.");

                return;
            }

            try {
                l = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "Kinah value must be an integer.");

                return;
            }
        }
        ItemService.increaseKinah(receiver, l);
        PacketSendUtility.sendMessage(admin, "Kinah given successfully.");
        PacketSendUtility.sendMessage(receiver, "An admin gives you some kinah.");
    }
}
