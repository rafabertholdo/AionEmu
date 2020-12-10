package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.state.CreatureVisualState;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_PLAYER_STATE;
import com.aionemu.gameserver.skillengine.effect.EffectId;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Invis extends AdminCommand {
    public Invis() {
        super("invis");
    }

    public void executeCommand(Player admin, String[] params) {
        if (admin.getAccessLevel() < AdminConfig.COMMAND_INVIS) {

            PacketSendUtility.sendMessage(admin, "You don't have enough rights to execute this command.");

            return;
        }
        if (admin.getVisualState() < 3) {

            admin.getEffectController().setAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());
            admin.setVisualState(CreatureVisualState.HIDE3);
            PacketSendUtility.broadcastPacket(admin, (AionServerPacket) new SM_PLAYER_STATE(admin), true);
            PacketSendUtility.sendMessage(admin, "You are invisible.");
        } else {

            admin.getEffectController().unsetAbnormal(EffectId.INVISIBLE_RELATED.getEffectId());
            admin.unsetVisualState(CreatureVisualState.HIDE3);
            PacketSendUtility.broadcastPacket(admin, (AionServerPacket) new SM_PLAYER_STATE(admin), true);
            PacketSendUtility.sendMessage(admin, "You are visible.");
        }
    }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Invis.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
