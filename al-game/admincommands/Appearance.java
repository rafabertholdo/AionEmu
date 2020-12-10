package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.PlayerAppearance;
import com.aionemu.gameserver.services.TeleportService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Appearance extends AdminCommand {
    public Appearance() {
        super("appearance");
    }

    public void executeCommand(Player admin, String[] params) {
        Player player;
        if (admin.getAccessLevel() < AdminConfig.COMMAND_APPEARANCE) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

            return;
        }
        String syntax = "Syntax: //appearance <size | voice | hair | face | deco | head_size | tattoo | reset (to reset the appearance)> <value>";

        if (params == null || params.length < 1) {

            PacketSendUtility.sendMessage(admin, syntax);

            return;
        }
        VisibleObject target = admin.getTarget();

        if (target == null) {
            player = admin;
        } else {
            player = (Player) target;
        }
        if (params[0].equals("reset")) {

            PlayerAppearance savedPlayerAppearance = player.getSavedPlayerAppearance();

            if (savedPlayerAppearance == null) {

                PacketSendUtility.sendMessage(admin, "The target has already the normal appearance.");

                return;
            }

            player.setPlayerAppearance(savedPlayerAppearance);

            player.setSavedPlayerAppearance(null);

            PacketSendUtility.sendMessage(player, "An admin has resetted your appearance.");

            TeleportService.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(),
                    player.getY(), player.getZ(), player.getHeading(), 0);

            return;
        }

        if (params.length < 2) {

            PacketSendUtility.sendMessage(admin, syntax);

            return;
        }

        PlayerAppearance playerAppearance = player.getPlayerAppearance();

        if (player.getSavedPlayerAppearance() == null) {
            player.setSavedPlayerAppearance((PlayerAppearance) playerAppearance.clone());
        }
        if (params[0].equals("size")) {
            float height;

            try {
                height = Float.parseFloat(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (height < 0.0F || height > 50.0F) {

                PacketSendUtility.sendMessage(admin, "Size: Min value : 0 - Max value : 50");

                return;
            }

            playerAppearance.setHeight(height);
        } else if (params[0].equals("voice")) {
            int voice;

            try {
                voice = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (voice < 0 || voice > 3) {

                PacketSendUtility.sendMessage(admin, "Voice: Min value : 0 - Max value : 3");

                return;
            }

            playerAppearance.setVoice(voice);
        } else if (params[0].equals("hair")) {
            int hair;

            try {
                hair = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (hair < 1 || hair > 43) {

                PacketSendUtility.sendMessage(admin, "Hair: Min value : 1 - Max value : 43");

                return;
            }

            playerAppearance.setHair(hair);
        } else if (params[0].equals("face")) {
            int face;

            try {
                face = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (face < 1 || face > 24) {

                PacketSendUtility.sendMessage(admin, "Face: Min value : 1 - Max value : 24");

                return;
            }

            playerAppearance.setFace(face);
        } else if (params[0].equals("deco")) {
            int deco;

            try {
                deco = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (deco < 1 || deco > 18) {

                PacketSendUtility.sendMessage(admin, "Deco: Min value : 1 - Max value : 18");

                return;
            }

            playerAppearance.setDeco(deco);
        } else if (params[0].equals("head_size")) {
            int head;

            try {
                head = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (head < 0 || head > 100) {

                PacketSendUtility.sendMessage(admin, "Head Size: Min value : 0 - Max value : 100");

                return;
            }

            playerAppearance.setHeadSize(head + 200);
        } else if (params[0].equals("tattoo")) {
            int tattoo;

            try {
                tattoo = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The value must be a number !");
                PacketSendUtility.sendMessage(admin, syntax);

                return;
            }
            if (tattoo < 1 || tattoo > 13) {

                PacketSendUtility.sendMessage(admin, "Tattoo: Min value : 1 - Max value : 13");

                return;
            }

            playerAppearance.setTattoo(tattoo);
        } else {

            PacketSendUtility.sendMessage(admin, syntax);

            return;
        }

        player.setPlayerAppearance(playerAppearance);

        PacketSendUtility.sendMessage(player, "An admin has changed your appearance.");

        TeleportService.teleportTo(player, player.getWorldId(), player.getInstanceId(), player.getX(), player.getY(),
                player.getZ(), player.getHeading(), 0);
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Appearance.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */