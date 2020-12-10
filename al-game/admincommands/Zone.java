package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.ZoneService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.zone.ZoneInstance;

public class Zone extends AdminCommand {
  public Zone() {
    super("zone");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_ZONE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length == 0) {

      ZoneInstance zoneInstance = admin.getZoneInstance();
      if (zoneInstance == null) {
        PacketSendUtility.sendMessage(admin, "You are out of any zone");
      } else {
        String zoneName = zoneInstance.getTemplate().getName().name();
        PacketSendUtility.sendMessage(admin, "You are in zone: " + zoneName);
      }

    } else if ("refresh".equalsIgnoreCase(params[0])) {

      ZoneService.getInstance().findZoneInCurrentMap(admin);
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Zone.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */