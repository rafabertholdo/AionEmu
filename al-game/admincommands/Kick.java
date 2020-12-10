package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_QUIT_RESPONSE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Kick extends AdminCommand {
    public Kick() {
        super("kick");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_KICK) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        if (params.length < 1) {

            PacketSendUtility.sendMessage(admin, "syntax //kick <character_name>");
            return;
        }
        Player player = World.getInstance().findPlayer(Util.convertName(params[0]));
        if (player == null) {

            PacketSendUtility.sendMessage(admin, "The specified player is not online.");
            return;
        }
        player.getClientConnection().close((AionServerPacket) new SM_QUIT_RESPONSE(), true);
        PacketSendUtility.sendMessage(admin, "Kicked player : " + player.getName());
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Kick.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
