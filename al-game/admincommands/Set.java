package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.PlayerClass;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_SET;
import com.aionemu.gameserver.network.aion.serverpackets.SM_TITLE_UPDATE;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.Arrays;

public class Set extends AdminCommand {
  public Set() {
    super("set");
  }

  public void executeCommand(Player admin, String[] params) {
    if (params == null || params.length < 2) {

      PacketSendUtility.sendMessage(admin, "syntax //set <class|exp|ap|level|title>");

      return;
    }
    Player target = null;
    VisibleObject creature = admin.getTarget();

    if (admin.getTarget() instanceof Player) {
      target = (Player) creature;
    }

    if (target == null) {

      PacketSendUtility.sendMessage(admin, "You should select a target first!");

      return;
    }
    if (params[1] == null) {

      PacketSendUtility.sendMessage(admin, "You should enter second params!");
      return;
    }
    String paramValue = params[1];

    if (params[0].equals("class")) {
      byte newClass;
      if (admin.getAccessLevel() < AdminConfig.COMMAND_SETCLASS) {

        PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

        return;
      }

      try {
        newClass = Byte.parseByte(paramValue);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "You should enter valid second params!");

        return;
      }
      PlayerClass oldClass = null;
      Player player = null;

      player = target;
      oldClass = target.getPlayerClass();

      setClass(player, oldClass, newClass);
    } else if (params[0].equals("exp")) {
      long exp;
      if (admin.getAccessLevel() < AdminConfig.COMMAND_SETEXP) {

        PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

        return;
      }

      try {
        exp = Long.parseLong(paramValue);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "You should enter valid second params!");

        return;
      }
      if (target instanceof Player) {
        Player player = target;
        player.getCommonData().setExp(exp);
        PacketSendUtility.sendMessage(admin, "Set your exp to " + paramValue);
      }

    } else if (params[0].equals("ap")) {
      int ap;
      if (admin.getAccessLevel() < AdminConfig.COMMAND_SETAP) {

        PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

        return;
      }

      try {
        ap = Integer.parseInt(paramValue);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "You should enter valid second params!");

        return;
      }
      if (target instanceof Player) {

        Player player = target;
        player.getCommonData().setAp(ap);
        if (player == admin) {
          PacketSendUtility.sendMessage(admin, "Set your Abyss Points to " + ap + ".");
        } else {
          PacketSendUtility.sendMessage(admin, "Set " + player.getName() + " Abyss Points to " + ap + ".");
          PacketSendUtility.sendMessage(player, "Admin set your Abyss Points to " + ap + ".");
        }

      } else {

        PacketSendUtility.sendMessage(admin, "You must select a Player to set their AP.");
      }

    } else if (params[0].equals("level")) {
      int level;
      if (admin.getAccessLevel() < AdminConfig.COMMAND_SETLEVEL) {

        PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

        return;
      }

      try {
        level = Integer.parseInt(paramValue);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "You should enter valid second params!");

        return;
      }
      if (target instanceof Player) {
        Player player = target;
        if (level <= 51)
          player.getCommonData().setLevel(level);
        PacketSendUtility.sendMessage(admin, "Set " + player.getCommonData().getName() + " level to " + level);
      }

    } else if (params[0].equals("title")) {
      int titleId;
      if (admin.getAccessLevel() < AdminConfig.COMMAND_SETTITLE) {

        PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

        return;
      }

      try {
        titleId = Integer.parseInt(paramValue);
      } catch (NumberFormatException e) {

        PacketSendUtility.sendMessage(admin, "You should enter valid second params!");

        return;
      }
      if (target instanceof Player) {

        Player player = target;
        if (titleId <= 106)
          setTitle(player, titleId);
        PacketSendUtility.sendMessage(admin, "Set " + player.getCommonData().getName() + " title to " + titleId);
      }
    }
  }

  private void setTitle(Player player, int value) {
    PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_TITLE_SET(value));
    PacketSendUtility.broadcastPacket((VisibleObject) player, (AionServerPacket) new SM_TITLE_UPDATE(player, value));
    player.getCommonData().setTitleId(value);
  }

  private void setClass(Player player, PlayerClass oldClass, byte value) {
    PlayerClass playerClass = PlayerClass.getPlayerClassById(value);
    int level = player.getLevel();
    if (level < 9) {

      PacketSendUtility.sendMessage(player, "You can only switch class after reach level 9");
      return;
    }
    if (Arrays
        .<Integer>asList(new Integer[] { Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4), Integer.valueOf(5),
            Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(10), Integer.valueOf(11) })
        .contains(Integer.valueOf(oldClass.ordinal()))) {

      PacketSendUtility.sendMessage(player, "You already switched class");
      return;
    }
    int newClassId = playerClass.ordinal();
    switch (oldClass.ordinal()) {

      case 0:
        if (newClassId == 1 || newClassId == 2)
          break;
      case 3:
        if (newClassId == 4 || newClassId == 5)
          break;
      case 6:
        if (newClassId == 7 || newClassId == 8)
          break;
      case 9:
        if (newClassId == 10 || newClassId == 11)
          break;
      default:
        PacketSendUtility.sendMessage(player, "Invalid class switch chosen");
        return;
    }
    player.getCommonData().setPlayerClass(playerClass);
    player.getCommonData().upgradePlayer();
    PacketSendUtility.sendMessage(player, "You have successfuly switched class");
  }
}
