package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import javolution.util.FastMap;

public class Siege extends AdminCommand {
  public Siege() {
    super("siege");
  }

  public void executeCommand(Player admin, String[] params) {
    if (!SiegeConfig.SIEGE_ENABLED) {

      PacketSendUtility.sendMessage(admin, "Siege system is currently disabled.");

      return;
    }
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SIEGE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

      return;
    }
    if (params == null || params.length == 0) {

      PacketSendUtility.sendMessage(admin, "No parameters detected.\nPlease use //siege help");

      return;
    }

    try {
      String cmd = params[0].toLowerCase();
      if ("help".startsWith(cmd)) {
        sendHelp(admin, params);
      } else if ("capture".startsWith(cmd)) {
        processCapture(admin, params);
      } else if ("set".startsWith(cmd)) {
        processSet(admin, params);
      } else if ("timer".startsWith(cmd)) {
        processTimer(admin, params);
      } else if ("list".startsWith(cmd)) {
        processList(admin, params);
      } else {
        PacketSendUtility.sendMessage(admin, "Sub Command does not exist.\nPlease use //siege help");
      }

    } catch (Exception e) {

      PacketSendUtility.sendMessage(admin, "Error with your request.\nPlease use //siege help");
    }
  }

  private void processList(Player admin, String[] params) {
    FastMap<Integer, SiegeLocation> locations = SiegeService.getInstance().getSiegeLocations();
    String msg = "[Siege Locations]\n";
    FastMap.Entry<Integer, SiegeLocation> e = locations.head();
    for (FastMap.Entry<Integer, SiegeLocation> end = locations.tail(); (e = e.getNext()) != end;) {

      SiegeLocation sLoc = (SiegeLocation) e.getValue();
      msg = msg + " - "
          + ((sLoc instanceof com.aionemu.gameserver.model.siege.Artifact) ? "Artifact"
              : ((sLoc instanceof com.aionemu.gameserver.model.siege.Fortress) ? "Fortress" : "Other"))
          + " " + sLoc.getLocationId() + " (" + sLoc.getRace().toString() + ")"
          + ((sLoc.getLegionId() == 0) ? "" : (" " + sLoc.getLegionId())) + "\n";

      if (msg.length() > 500) {

        PacketSendUtility.sendMessage(admin, msg);
        msg = "";
      }
    }
    PacketSendUtility.sendMessage(admin, msg);
  }

  private void processTimer(Player admin, String[] params) {
  }

  private void processSet(Player admin, String[] params) {
    int locationId, currentState;
    if (params.length < 3 || params.length > 4) {

      PacketSendUtility.sendMessage(admin, "Incorrect parameter count.\nPlease use //siege help set");

      return;
    }

    try {
      locationId = Integer.parseInt(params[1]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Location ID must be an integer.");

      return;
    }

    try {
      currentState = Integer.parseInt(params[2]);
    } catch (NumberFormatException e) {

      String cmd = params[2].toLowerCase();
      if ("invulnerable".startsWith(cmd)) {

        currentState = 0;
      } else if ("vulnerable".startsWith(cmd)) {

        currentState = 2;
      } else {

        PacketSendUtility.sendMessage(admin, "Current State must be an integer.");

        return;
      }
    }

    int nextState = -1;
    if (params.length == 4) {

      try {

        nextState = Integer.parseInt(params[3]);
      } catch (NumberFormatException e) {

        String cmd = params[3].toLowerCase();
        if ("invulnerable".startsWith(cmd)) {

          nextState = 0;
        } else if ("vulnerable".startsWith(cmd)) {

          nextState = 1;
        } else {

          PacketSendUtility.sendMessage(admin, "Next State must be an integer.");

          return;
        }
      }
    }
    if (currentState != 0 && currentState != 2) {

      PacketSendUtility.sendMessage(admin, "Incorrect current state value.\nPlease use //siege help set");

      return;
    }

    if (params.length == 4 && nextState != 0 && nextState != 1) {

      PacketSendUtility.sendMessage(admin, "Incorrect next state value.\nPlease use //siege help set");

      return;
    }

    SiegeLocation sLoc = SiegeService.getInstance().getSiegeLocation(locationId);

    if (sLoc == null) {

      PacketSendUtility.sendMessage(admin, "Location does not exist: " + locationId);

      return;
    }
    PacketSendUtility.sendMessage(admin, "[Admin Set State]\n - Location ID: " + locationId + "\n - New Current State: "
        + ((currentState == 2) ? "Vulnerable" : "Invulnerable")
        + ((params.length == 4) ? ("\n - New Next State: " + ((nextState == 1) ? "Vulnerable" : "Invulnerable")) : "")
        + "\n");

    if (sLoc.isVulnerable() != ((currentState == 2))) {
      sLoc.setVulnerable((currentState == 2));
    }

    if (params.length == 4 && sLoc.getNextState() != nextState) {
      sLoc.setNextState(Integer.valueOf(nextState));
    }

    SiegeService.getInstance().broadcastUpdate(sLoc);
  }

  private void processCapture(Player admin, String[] params) {
    int locationId;
    SiegeRace race;
    if (params.length < 3 || params.length > 4) {

      PacketSendUtility.sendMessage(admin, "Incorrect parameter count.\nPlease use //siege help capture");

      return;
    }

    try {
      locationId = Integer.parseInt(params[1]);
    } catch (NumberFormatException e) {

      PacketSendUtility.sendMessage(admin, "Location ID must be an integer.");

      return;
    }

    String raceName = params[2].toLowerCase();
    if ("elyos".startsWith(raceName)) {

      race = SiegeRace.ELYOS;
    } else if ("asmos".startsWith(raceName)) {

      race = SiegeRace.ASMODIANS;
    } else if ("balaur".startsWith(raceName)) {

      race = SiegeRace.BALAUR;
    } else {

      PacketSendUtility.sendMessage(admin, "Race must be: Elyos, Asmos, or Balaur.\nPlease use //siege help capture");

      return;
    }

    int legionId = 0;
    if (params.length == 4) {

      try {

        legionId = Integer.parseInt(params[3]);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "Legion ID must be an integer.");

        return;
      }
    }
    SiegeLocation sLoc = SiegeService.getInstance().getSiegeLocation(locationId);

    if (sLoc == null) {

      PacketSendUtility.sendMessage(admin, "Location does not exist: " + locationId);

      return;
    }
    PacketSendUtility.sendMessage(admin, "[Admin Capture]\n - Location ID: " + locationId + "\n - Race: "
        + race.toString() + "\n - Legion ID: " + legionId + "\n");

    SiegeService.getInstance().capture(locationId, race, legionId);
  }

  private void sendHelp(Player admin, String[] params) {
    if (params.length == 2) {

      String cmd = params[1].toLowerCase();
      if ("capture".startsWith(cmd)) {

        sendHelpCapture(admin);
        return;
      }
      if ("set".startsWith(cmd)) {

        sendHelpSet(admin);
        return;
      }
      if ("timer".startsWith(cmd)) {

        sendHelpTimer(admin, params);
        return;
      }
      if ("list".startsWith(cmd)) {

        sendHelpList(admin, params);
        return;
      }
    }
    sendHelpGeneral(admin);
  }

  private void sendHelpList(Player admin, String[] params) {
    PacketSendUtility.sendMessage(admin,
        "[Help: Siege List Command]\n  The siege list command outputs each siege location.  Format is: - (fortress|artifact) <location id> (faction owner) [legion id]\n");
  }

  private void sendHelpGeneral(Player admin) {
    PacketSendUtility.sendMessage(admin,
        "[Help: Siege Command]\n  Use //siege help <capture|set|list> for more details on the command.\n  Notice: This command uses smart matching. You may abbreviate most commands.\n  For example: (//siege cap 1011 ely) will match to (//siege capture 1011 elyos)\n");
  }

  private void sendHelpTimer(Player admin, String[] params) {
    PacketSendUtility.sendMessage(admin, "Timer not implemented.");
  }

  private void sendHelpSet(Player admin) {
    PacketSendUtility.sendMessage(admin,
        "Syntax: //siege set <location id> <current state> [next state]\nCurrent State Values: 0 - Invulnerable, 2 - Vulnerable\nNext State Values: 0 - Invulnerable, 1 - Vulnerable");
  }

  private void sendHelpCapture(Player admin) {
    PacketSendUtility.sendMessage(admin,
        "Syntax: //siege capture <location id> <race> [legion id]\nRace may be: Elyos, Asmos, Balaur. (Not case sensitive.)");
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Siege.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
