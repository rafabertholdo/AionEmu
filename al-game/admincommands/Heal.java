package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.serverpackets.SM_ATTACK_STATUS;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Heal extends AdminCommand {
    public Heal() {
        super("heal");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_HEAL) {

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
            creature.getLifeStats().increaseHp(SM_ATTACK_STATUS.TYPE.HP, creature.getLifeStats().getMaxHp() + 1);
            creature.getLifeStats().increaseMp(SM_ATTACK_STATUS.TYPE.MP, creature.getLifeStats().getMaxMp() + 1);

            if (target instanceof Player) {
                ((Player) creature).getCommonData().setDp(creature.getGameStats().getCurrentStat(StatEnum.MAXDP));
            }

            PacketSendUtility.sendMessage(admin, creature.getName() + " has been refreshed !");
        }
    }
}
