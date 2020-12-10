package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_ITEM;
import com.aionemu.gameserver.network.aion.serverpackets.SM_UPDATE_PLAYER_APPEARANCE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Dye extends AdminCommand {
    public Dye() {
        super("dye");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_DYE) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");

            return;
        }
        Player target = null;
        VisibleObject creature = admin.getTarget();

        if (admin.getTarget() instanceof Player) {
            target = (Player) creature;
        }

        if (target == null) {

            PacketSendUtility.sendMessage(admin, "You should select a target first!");

            return;
        }
        if (params.length == 0 || params.length > 2) {

            PacketSendUtility.sendMessage(admin, "syntax //dye <dye color|hex color|no>");

            return;
        }
        String color = "";
        if (params.length == 2) {
            if (params[1].equalsIgnoreCase("petal")) {
                color = params[0];
            } else {
                color = params[0] + " " + params[1];
            }
        } else {
            color = params[0];
        }

        int rgb = 0;
        int bgra = 0;

        if (color.equalsIgnoreCase("turquoise")) {
            color = "198d94";
        } else if (color.equalsIgnoreCase("blue")) {
            color = "1f87f5";
        } else if (color.equalsIgnoreCase("brown")) {
            color = "66250e";
        } else if (color.equalsIgnoreCase("purple")) {
            color = "c38df5";
        } else if (color.equalsIgnoreCase("true red")) {
            color = "c22626";
        } else if (color.equalsIgnoreCase("true white") || color.equalsIgnoreCase("white")) {
            color = "ffffff";
        } else if (color.equalsIgnoreCase("black") || color.equalsIgnoreCase("true black")) {
            color = "000000";
        } else if (color.equalsIgnoreCase("hot orange")) {
            color = "e36b00";
        } else if (color.equalsIgnoreCase("rich purple")) {
            color = "440b9a";
        } else if (color.equalsIgnoreCase("hot pink")) {
            color = "d60b7e";
        } else if (color.equalsIgnoreCase("mustard")) {
            color = "fcd251";
        } else if (color.equalsIgnoreCase("green tea")) {
            color = "61bb4f";
        } else if (color.equalsIgnoreCase("olive green")) {
            color = "5f730e";
        } else if (color.equalsIgnoreCase("deep blue")) {
            color = "14398b";
        } else if (color.equalsIgnoreCase("romantic purple")) {
            color = "80185d";
        } else if (color.equalsIgnoreCase("wiki")) {
            color = "85e831";
        } else if (color.equalsIgnoreCase("omblic")) {
            color = "ff5151";
        } else if (color.equalsIgnoreCase("meon")) {
            color = "afaf26";
        } else if (color.equalsIgnoreCase("ormea")) {
            color = "ffaa11";
        } else if (color.equalsIgnoreCase("tange")) {
            color = "bd5fff";
        } else if (color.equalsIgnoreCase("ervio")) {
            color = "3bb7fe";
        } else if (color.equalsIgnoreCase("lunime")) {
            color = "c7af27";
        } else if (color.equalsIgnoreCase("vinna")) {
            color = "052775";
        } else if (color.equalsIgnoreCase("kirka")) {
            color = "ca84ff";
        } else if (color.equalsIgnoreCase("brommel")) {
            color = "c7af27";
        } else if (color.equalsIgnoreCase("pressa")) {
            color = "ff9d29";
        } else if (color.equalsIgnoreCase("merone")) {
            color = "8df598";
        } else if (color.equalsIgnoreCase("kukar")) {
            color = "ffff96";
        } else if (color.equalsIgnoreCase("leopis")) {
            color = "31dfff";
        }

        try {
            rgb = Integer.parseInt(color, 16);
            bgra = 0xFF | (rgb & 0xFF) << 24 | (rgb & 0xFF00) << 8 | (rgb & 0xFF0000) >>> 8;

        } catch (NumberFormatException e) {

            if (!color.equalsIgnoreCase("no")) {
                PacketSendUtility.sendMessage(admin, "[Dye] Can't understand: " + color);

                return;
            }
        }
        for (Item targetItem : target.getEquipment().getEquippedItemsWithoutStigma()) {
            if (color.equals("no")) {

                targetItem.setItemColor(0);
            } else {

                targetItem.setItemColor(bgra);
            }
            PacketSendUtility.sendPacket(target, (AionServerPacket) new SM_UPDATE_ITEM(targetItem));
        }
        PacketSendUtility.broadcastPacket(target,
                (AionServerPacket) new SM_UPDATE_PLAYER_APPEARANCE(target.getObjectId(),
                        target.getEquipment().getEquippedItemsWithoutStigma()),
                true);
        target.getEquipment().setPersistentState(PersistentState.UPDATE_REQUIRED);
        if (target.getObjectId() != admin.getObjectId())
            PacketSendUtility.sendMessage(target, "You have been dyed by " + admin.getName() + "!");
        PacketSendUtility.sendMessage(admin, "Dyed " + target.getName() + " successfully!");
    }
}
