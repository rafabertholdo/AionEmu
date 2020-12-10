package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.SkillLearnService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class GiveMissingSkills extends AdminCommand {
    public GiveMissingSkills() {
        super("givemissingskills");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_GIVEMISSINGSKILLS) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        SkillLearnService.addMissingSkills(admin);
    }
}
