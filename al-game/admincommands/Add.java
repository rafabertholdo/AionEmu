package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ItemService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;

public class Add extends AdminCommand {
    public Add() {
        super("add");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_ADD) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        if (params.length == 0 || params.length > 3) {

            PacketSendUtility.sendMessage(admin, "syntax //add <player> <item ID> <quantity>");

            return;
        }
        int itemId = 0;
        long itemCount = 1L;
        Player receiver = null;

        try {
            itemId = Integer.parseInt(params[0]);

            if (params.length == 2) {
                itemCount = Long.parseLong(params[1]);
            }
            receiver = admin;
        } catch (NumberFormatException e) {

            receiver = World.getInstance().findPlayer(Util.convertName(params[0]));

            if (receiver == null) {

                PacketSendUtility.sendMessage(admin, "Could not find a player by that name.");

                return;
            }

            try {
                itemId = Integer.parseInt(params[1]);

                if (params.length == 3) {
                    itemCount = Long.parseLong(params[2]);
                }
            } catch (NumberFormatException ex) {

                PacketSendUtility.sendMessage(admin, "You must give number to itemid.");

                return;
            } catch (Exception ex2) {

                PacketSendUtility.sendMessage(admin, "Occurs an error.");

                return;
            }
        }
        if (ItemService.addItem(receiver, itemId, itemCount)) {

            PacketSendUtility.sendMessage(admin, "Item added successfully");
            PacketSendUtility.sendMessage(receiver, "Admin gives you an item");
        } else {

            PacketSendUtility.sendMessage(admin, "Item couldn't be added");
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Add.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */