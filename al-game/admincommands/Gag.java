package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.TaskId;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.Util;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;
import java.util.concurrent.Future;

public class Gag extends AdminCommand {
    public Gag() {
        super("gag");
    }

  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_GAG) {
      
      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");
      
      return;
    } 
    if (params == null || params.length < 1) {
      
      PacketSendUtility.sendMessage(admin, "Syntax: //gag <player> [time in minutes]");
      
      return;
    } 
    String name = Util.convertName(params[0]);
    final Player player = World.getInstance().findPlayer(name);
    if (player == null) {
      
      PacketSendUtility.sendMessage(admin, "Player " + name + " was not found!");
      PacketSendUtility.sendMessage(admin, "Syntax: //gag <player> [time in minutes]");
      
      return;
    } 
    int time = 0;
    if (params.length > 1) {
      
      try {
        
        time = Integer.parseInt(params[1]);
      }
      catch (NumberFormatException e) {
        
        PacketSendUtility.sendMessage(admin, "Syntax: //gag <player> [time in minutes]");
        
        return;
      } 
    }
    player.setGagged(true);
    if (time != 0) {
      
      Future<?> task = player.getController().getTask(TaskId.GAG);
      if (task != null)
        player.getController().cancelTask(TaskId.GAG); 
      player.getController().addTask(TaskId.GAG, ThreadPoolManager.getInstance().schedule(new Runnable()
            {
              public void run()
              {
                player.setGagged(false);
                PacketSendUtility.sendMessage(player, "You have been ungagged");
              }
            }time * 60000L));
    } 
    PacketSendUtility.sendMessage(player, "You have been gagged" + ((time != 0) ? (" for " + time + " minutes") : ""));

    
    PacketSendUtility.sendMessage(admin, "Player " + name + " gagged" + ((time != 0) ? (" for " + time + " minutes") : ""));
  }
}

/*
 * Location:
 * D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar
 * !\admincommands\Gag.class Java compiler version: 6 (50.0) JD-Core Version:
 * 1.1.3
 */
