package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Invul extends AdminCommand {
    public Invul() {
        super("invul");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_INVUL) {

            PacketSendUtility.sendMessage(admin, "You do not have sufficient rights to execute this command");

            return;
        }
        if (admin.isInvul()) {

            admin.setInvul(false);
            PacketSendUtility.sendMessage(admin, "You are now mortal.");
        } else {

            admin.setInvul(true);
            PacketSendUtility.sendMessage(admin, "You are now immortal.");
        }
    }
}
