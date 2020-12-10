package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.configs.main.CacheConfig;
import com.aionemu.gameserver.configs.main.CustomConfig;
import com.aionemu.gameserver.configs.main.GSConfig;
import com.aionemu.gameserver.configs.main.GroupConfig;
import com.aionemu.gameserver.configs.main.LegionConfig;
import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.configs.main.RateConfig;
import com.aionemu.gameserver.configs.main.ShutdownConfig;
import com.aionemu.gameserver.configs.network.IPConfig;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.lang.reflect.Field;

public class Configure extends AdminCommand {
  public Configure() {
    super("configure");
  }

  public void executeCommand(Player admin, String[] params) {
    Class<NetworkConfig> clazz;
    if (admin.getAccessLevel() < AdminConfig.COMMAND_CONFIGURE) {

      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");

      return;
    }
    String command = "";
    if (params.length == 3) {

      command = params[0];
      if (!"show".equalsIgnoreCase(command)) {

        PacketSendUtility.sendMessage(admin, "syntax //configure <set|show> <configname> <property> [<newvalue>]");
        return;
      }
    } else if (params.length == 4) {

      command = params[0];
      if (!"set".equalsIgnoreCase(command)) {

        PacketSendUtility.sendMessage(admin, "syntax //configure <set|show> <configname> <property> [<newvalue>]");

        return;
      }
    } else {
      PacketSendUtility.sendMessage(admin, "syntax //configure <set|show> <configname> <property> [<newvalue>]");

      return;
    }
    Class<AdminConfig> classToMofify = null;
    String className = params[1];

    if ("admin".equalsIgnoreCase(className)) {

      classToMofify = AdminConfig.class;
    } else if ("cache".equalsIgnoreCase(className)) {

      Class<CacheConfig> clazz1 = CacheConfig.class;
    } else if ("custom".equalsIgnoreCase(className)) {

      Class<CustomConfig> clazz1 = CustomConfig.class;
    } else if ("group".equalsIgnoreCase(className)) {

      Class<GroupConfig> clazz1 = GroupConfig.class;
    } else if ("gs".equalsIgnoreCase(className)) {

      Class<GSConfig> clazz1 = GSConfig.class;
    } else if ("legion".equalsIgnoreCase(className)) {

      Class<LegionConfig> clazz1 = LegionConfig.class;
    } else if ("rate".equalsIgnoreCase(className)) {

      Class<RateConfig> clazz1 = RateConfig.class;
    } else if ("shutdown".equalsIgnoreCase(className)) {

      Class<ShutdownConfig> clazz1 = ShutdownConfig.class;
    } else if ("tm".equalsIgnoreCase(className)) {

      Class<OptionsConfig> clazz1 = OptionsConfig.class;
    } else if ("ip".equalsIgnoreCase(className)) {

      Class<IPConfig> clazz1 = IPConfig.class;
    } else if ("network".equalsIgnoreCase(className)) {

      clazz = NetworkConfig.class;
    }

    if (command.equalsIgnoreCase("show")) {

      String fieldName = params[2];

      try {
        Field someField = clazz.getDeclaredField(fieldName.toUpperCase());
        PacketSendUtility.sendMessage(admin, "Current value is " + someField.get(null));
      } catch (Exception e) {
        PacketSendUtility.sendMessage(admin, "Something really bad happend :)");

        return;
      }
    } else if (command.equalsIgnoreCase("set")) {

      String fieldName = params[2];
      String newValue = params[3];
      if (clazz != null) {

        try {

          Field someField = clazz.getDeclaredField(fieldName.toUpperCase());
          Class<?> classType = someField.getType();
          if (classType == String.class) {
            someField.set(null, newValue);
          } else if (classType == int.class || classType == Integer.class) {
            someField.set(null, Integer.valueOf(Integer.parseInt(newValue)));
          } else if (classType == Boolean.class || classType == boolean.class) {
            someField.set(null, Boolean.valueOf(newValue));
          }

        } catch (Exception e) {
          PacketSendUtility.sendMessage(admin, "Something really bad happend :)");
          return;
        }
      }
      PacketSendUtility.sendMessage(admin, "Property changed");
    }
  }
}
