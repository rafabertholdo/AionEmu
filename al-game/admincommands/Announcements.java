package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.Announcement;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.AnnouncementService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.Set;

public class Announcements extends AdminCommand {
    private AnnouncementService announceService;

    public Announcements() {
        super("announcements");
        this.announceService = AnnouncementService.getInstance();
    }

    public void executeCommand(Player admin, String[] params) {
        String syntaxCommand = "Syntax: //announcements list - Obtain all announcements in the database.\n";
        syntaxCommand = syntaxCommand
                + "Syntax: //announcements add <faction: ELYOS | ASMODIANS | ALL> <type: NORMAL | ANNOUNCE | ORANGE | YELLOW | SHOUT> <delay in seconds> <message> - Add an announcements in the database.\n";
        syntaxCommand = syntaxCommand
                + "Syntax: //announcements delete <id (see //announcements list to find all id> - Delete an announcements from the database.";

        if (admin.getAccessLevel() < AdminConfig.COMMAND_ANNOUNCEMENTS) {

            PacketSendUtility.sendMessage(admin, "You don't have enough rights to execute this command.");

            return;
        }
        if (params.length < 1 || params == null) {

            PacketSendUtility.sendMessage(admin, syntaxCommand);

            return;
        }
        if (params[0].equals("list")) {

            Set<Announcement> announces = this.announceService.getAnnouncements();
            PacketSendUtility.sendMessage(admin, "ID  |  FACTION  |  CHAT TYPE  |  DELAY  |  MESSAGE");
            PacketSendUtility.sendMessage(admin, "-------------------------------------------------------------------");

            for (Announcement announce : announces) {
                PacketSendUtility.sendMessage(admin, announce.getId() + "  |  " + announce.getFaction() + "  |  "
                        + announce.getType() + "  |  " + announce.getDelay() + "  |  " + announce.getAnnounce());
            }
        } else if (params[0].equals("add")) {
            char c;
            if (params.length < 5) {

                PacketSendUtility.sendMessage(admin, syntaxCommand);

                return;
            }

            try {
                c = Integer.parseInt(params[3]);
            } catch (NumberFormatException e) {

                c = '΄';
            }

            String message = "";

            for (int i = 4; i < params.length - 1; i++) {
                message = message + params[i] + " ";
            }

            message = message + params[params.length - 1];

            Announcement announce = new Announcement(message, params[1], params[2], c);

            this.announceService.addAnnouncement(announce);

            this.announceService.reload();

            PacketSendUtility.sendMessage(admin, "The announcement has been created with successful !");
        } else if (params[0].equals("delete")) {
            int id;
            if (params.length < 2) {

                PacketSendUtility.sendMessage(admin, syntaxCommand);

                return;
            }

            try {
                id = Integer.parseInt(params[1]);
            } catch (NumberFormatException e) {

                PacketSendUtility.sendMessage(admin, "The announcement's ID is wrong !");
                PacketSendUtility.sendMessage(admin, syntaxCommand);

                return;
            }

            this.announceService.delAnnouncement(id);

            this.announceService.reload();

            PacketSendUtility.sendMessage(admin, "The announcement has been deleted with successful !");
        } else {

            PacketSendUtility.sendMessage(admin, syntaxCommand);
            return;
        }
    }
}
