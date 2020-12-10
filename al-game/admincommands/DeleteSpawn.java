package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class DeleteSpawn extends AdminCommand {
    public DeleteSpawn() {
        super("delete");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_DELETESPAWN) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        VisibleObject cre = admin.getTarget();
        if (!(cre instanceof Npc)) {

            PacketSendUtility.sendMessage(admin, "Wrong target");
            return;
        }
        Npc npc = (Npc) cre;
        DataManager.SPAWNS_DATA.removeSpawn(npc.getSpawn());
        npc.getController().delete();
        PacketSendUtility.sendMessage(admin, "Spawn removed");
    }
}
