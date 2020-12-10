package admincommands;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.dao.PetitionDAO;
import com.aionemu.gameserver.dao.PlayerDAO;
import com.aionemu.gameserver.model.PetitionType;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.services.MailService;
import com.aionemu.gameserver.services.PetitionService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.Collection;

public class Petition extends AdminCommand {
  public Petition() {
    super("petition");
  }

  public int getSplitSize() {
    return 3;
  }

  public void executeCommand(Player admin, String[] params) {
    boolean isOnline;
    if (admin.getAccessLevel() < AdminConfig.COMMAND_PETITION) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");

      return;
    }

    if (params == null || params.length == 0) {

      Collection<com.aionemu.gameserver.model.Petition> petitions = PetitionService.getInstance()
          .getRegisteredPetitions();
      com.aionemu.gameserver.model.Petition[] petitionsArray = petitions.<com.aionemu.gameserver.model.Petition>toArray(
          new com.aionemu.gameserver.model.Petition[0]);
      PacketSendUtility.sendMessage(admin, petitionsArray.length + " unprocessed petitions.");
      if (petitionsArray.length < 5) {

        PacketSendUtility.sendMessage(admin, "== " + petitionsArray.length + " first petitions to reply ==");
        for (int i = 0; i < petitionsArray.length; i++) {
          PacketSendUtility.sendMessage(admin,
              petitionsArray[i].getPetitionId() + " | " + petitionsArray[i].getTitle());
        }
      } else {

        PacketSendUtility.sendMessage(admin, "== 5 first petitions to reply ==");
        for (int i = 0; i < 5; i++) {
          PacketSendUtility.sendMessage(admin,
              petitionsArray[i].getPetitionId() + " | " + petitionsArray[i].getTitle());
        }
      }

      return;
    }
    int petitionId = 0;

    try {
      petitionId = Integer.parseInt(params[0]);
    } catch (NumberFormatException nfe) {

      PacketSendUtility.sendMessage(admin, "Invalid petition id.");

      return;
    }
    com.aionemu.gameserver.model.Petition petition = ((PetitionDAO) DAOManager.getDAO(PetitionDAO.class))
        .getPetitionById(petitionId);

    if (petition == null) {

      PacketSendUtility.sendMessage(admin, "There is no petition with id #" + petitionId);

      return;
    }
    String petitionPlayer = "";

    if (World.getInstance().findPlayer(petition.getPlayerObjId()) != null) {

      petitionPlayer = World.getInstance().findPlayer(petition.getPlayerObjId()).getName();
      isOnline = true;
    } else {

      petitionPlayer = ((PlayerDAO) DAOManager.getDAO(PlayerDAO.class)).getPlayerNameByObjId(petition.getPlayerObjId());
      isOnline = false;
    }

    StringBuilder message = new StringBuilder();

    if (params.length == 1) {

      message.append("== Petition #" + petitionId + " ==\n");
      message.append("Player: " + petitionPlayer + " (");
      if (isOnline) {
        message.append("Online");
      } else {
        message.append("Offline");
      }
      message.append(")\n");
      message.append("Type: " + getHumanizedValue(petition.getPetitionType()) + "\n");
      message.append("Title: " + petition.getTitle() + "\n");
      message.append("Text: " + petition.getContentText() + "\n");
      message.append("= Additional Data =\n");
      message.append(getFormattedAdditionalData(petition.getPetitionType(), petition.getAdditionalData()));

    } else if (params.length == 2 && params[1].equals("delete")) {

      PetitionService.getInstance().deletePetition(petition.getPlayerObjId());
      PacketSendUtility.sendMessage(admin, "Petition #" + petitionId + " deleted.");

    } else if (params.length == 3 && params[1].equals("reply")) {

      String replyMessage = params[2];
      if (replyMessage.equals("")) {

        PacketSendUtility.sendMessage(admin, "You must specify a reply to that petition");

        return;
      }
      MailService.getInstance().sendMail(admin, petitionPlayer, "GM-Re:" + petition.getTitle(), replyMessage, 0, 0, 0,
          false);
      PetitionService.getInstance().setPetitionReplied(petitionId);

      PacketSendUtility.sendMessage(admin,
          "Your reply has been sent to " + petitionPlayer + ". Petition is now closed.");
    }

    PacketSendUtility.sendMessage(admin, message.toString());
  }

  private String getHumanizedValue(PetitionType type) {
    String result = "";
    switch (type) {
      case CHARACTER_STUCK:
        result = "Character Stuck";

        return result;
      case CHARACTER_RESTORATION:
        result = "Character Restoration";
        return result;
      case BUG:
        result = "Bug";
        return result;
      case QUEST:
        result = "Quest";
        return result;
      case UNACCEPTABLE_BEHAVIOR:
        result = "Unacceptable Behavior";
        return result;
      case SUGGESTION:
        result = "Suggestion";
      case INQUIRY:
        result = "Inquiry about the game";
        break;
    }
    result = "Unknown";
    return result;
  }

  private String getFormattedAdditionalData(PetitionType type, String additionalData) {
    String bugData[], bData[], result = "";
    switch (type)

    {
      case CHARACTER_STUCK:
        result = "Character Location: " + additionalData;

        return result;
      case CHARACTER_RESTORATION:
        result = "Category: " + additionalData;
        return result;
      case BUG:
        bugData = additionalData.split("/");
        result = "Time Occured: " + bugData[0] + "\n";
        result = result + "Zone and Coords: " + bugData[1];
        if (bugData.length > 2)
          result = result + "\nHow to Replicate: " + bugData[2];
        return result;
      case QUEST:
        result = "Quest Title: " + additionalData;
        return result;
      case UNACCEPTABLE_BEHAVIOR:
        bData = additionalData.split("/");
        result = "Time Occured: " + bData[0] + "\n";
        result = result + "Character Name: " + bData[1] + "\n";
        result = result + "Category: " + bData[2];
        return result;
      case SUGGESTION:
        result = "Category: " + additionalData;
        return result;
      case INQUIRY:
        result = "Petition Category: " + additionalData;
        return result;
    }
    result = additionalData;
    return result;
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Petition.class Java compiler version: 6 (50.0) JD-Core
 * Version: 1.1.3
 */
