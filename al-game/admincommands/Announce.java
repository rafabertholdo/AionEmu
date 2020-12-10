package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import com.aionemu.gameserver.world.World;


























public class Announce
  extends AdminCommand
{
  public Announce() {
    super("announce");
  }


  
  public int getSplitSize() {
    return 2;
  }



  
  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_ANNOUNCE) {
      
      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command.");
      
      return;
    } 
    if (params == null || params.length != 2) {
      
      PacketSendUtility.sendMessage(admin, "Syntax: //announce <anonymous|name> <message>");

      
      return;
    } 
    
    if ("anonymous".startsWith(params[0].toLowerCase())) {
      
      message = "Announce: ";
    }
    else if ("name".startsWith(params[0].toLowerCase())) {
      
      message = admin.getName() + ": ";
    }
    else {
      
      PacketSendUtility.sendMessage(admin, "Syntax: //announce <anonymous|name> <message>");
      return;
    } 
    String message = message + params[1];
    
    for (Player player : World.getInstance().getAllPlayers())
    {
      PacketSendUtility.sendSysMessage(player, message);
    }
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\Announce.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
