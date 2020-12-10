package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Kill extends AdminCommand {
    public Kill() {
        super("kill");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_KILL) {

            PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

            return;
        }
        VisibleObject target = admin.getTarget();
        if (target == null) {

            PacketSendUtility.sendMessage(admin, "No target selected");
            return;
        }
        if (target instanceof Creature) {

            Creature creature = (Creature) target;
            creature.getController().onAttack((Creature) admin, creature.getLifeStats().getMaxHp() + 1);
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Kill.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */