package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class BanIP extends AdminCommand {
    public BanIP() {
        super("banip");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_BAN) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

            return;
        }
        if (params == null || params.length < 1) {

            PacketSendUtility.sendMessage(admin, "Syntax: //banip <mask> [time in minutes]");

            return;
        }
        String mask = params[0];

        int time = 0;
        if (params.length > 1) {

            try {

                time = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "Syntax: //banip <mask> [time in minutes]");

                return;
            }
        }
        LoginServer.getInstance().sendBanPacket((byte) 2, 0, mask, time, admin.getObjectId());
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\BanIP.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
