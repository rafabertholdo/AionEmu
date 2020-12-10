package admincommands;

import com.aionemu.gameserver.cache.HTMLCache;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.HTMLService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class HTML extends AdminCommand {
    public HTML() {
        super("html");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_HTML) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

            return;
        }
        if (params == null || params.length < 1) {

            PacketSendUtility.sendMessage(admin, "Usage: //html reload || //html test <filename.html>");

            return;
        }
        if (params[0].equals("reload")) {

            HTMLCache.getInstance().reload(true);
            PacketSendUtility.sendMessage(admin, HTMLCache.getInstance().toString());
        } else if (params[0].equals("test")) {

            HTMLService.showHTML(admin, HTMLCache.getInstance().getHTML(params[1]));
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\HTML.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */