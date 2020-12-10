package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.EmotionType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.stats.StatEnum;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_EMOTION;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;

public class Speed extends AdminCommand {
  public Speed() {
    super("speed");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SPEED) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params == null || params.length < 1) {

      PacketSendUtility.sendMessage(admin, "Syntax //speed <percent>");

      return;
    }
    int parameter = 0;

    try {
      parameter = Integer.parseInt(params[0]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Parameter should number");

      return;
    }
    if (parameter < 0 || parameter > 200) {

      PacketSendUtility.sendMessage(admin, "Valid values are in 0-200 range");

      return;
    }
    int speed = 6000;
    int flyspeed = 9000;

    admin.getGameStats().setStat(StatEnum.SPEED, speed + speed * parameter / 100);
    admin.getGameStats().setStat(StatEnum.FLY_SPEED, flyspeed + flyspeed * parameter / 100);
    PacketSendUtility.broadcastPacket(admin,
        (AionServerPacket) new SM_EMOTION((Creature) admin, EmotionType.START_EMOTE2, 0, 0), true);
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Speed.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
