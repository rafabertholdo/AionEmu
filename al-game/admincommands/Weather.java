package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.WeatherService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.WorldMapType;

public class Weather extends AdminCommand {
  private static final String RESET = "reset";
  private static final String COMMAND = "weather";

  public Weather() {
    super("weather");
  }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_WEATHER) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    if (params.length == 0 || params.length > 2) {

      PacketSendUtility.sendMessage(admin,
          "syntax //weather<regionName(poeta, ishalgen, etc ...)> <value(0->8)> OR //weather reset");

      return;
    }

    String regionName = null;
    int weatherType = -1;

    regionName = new String(params[0]);

    if (params.length == 2) {

      try {

        weatherType = Integer.parseInt(params[1]);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "weather type parameter need to be an integer [0-8].");

        return;
      }
    }
    if (regionName.equals("reset")) {

      WeatherService.getInstance().resetWeather();

      return;
    }

    WorldMapType region = null;
    for (WorldMapType worldMapType : WorldMapType.values()) {

      if (worldMapType.name().toLowerCase().equals(regionName.toLowerCase())) {
        region = worldMapType;
      }
    }

    if (region != null) {

      if (weatherType > -1 && weatherType < 9) {

        WeatherService.getInstance().changeRegionWeather(region.getId(), new Integer(weatherType));
      } else {

        PacketSendUtility.sendMessage(admin, "Weather type must be between 0 and 8");

        return;
      }
    } else {
      PacketSendUtility.sendMessage(admin, "Region " + regionName + " not found");
      return;
    }
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Weather.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
