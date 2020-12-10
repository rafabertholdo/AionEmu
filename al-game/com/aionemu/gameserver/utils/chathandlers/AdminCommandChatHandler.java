package com.aionemu.gameserver.utils.chathandlers;

import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.model.ChatType;
import com.aionemu.gameserver.model.gameobjects.Creature;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import java.util.Map;
import javolution.util.FastMap;
import org.apache.log4j.Logger;


























public class AdminCommandChatHandler
  implements ChatHandler
{
  private static final Logger log = Logger.getLogger(AdminCommandChatHandler.class);
  
  private Map<String, AdminCommand> commands = (Map<String, AdminCommand>)new FastMap();






  
  void registerAdminCommand(AdminCommand command) {
    if (command == null) {
      throw new NullPointerException("Command instance cannot be null");
    }
    String commandName = command.getCommandName();
    
    AdminCommand old = this.commands.put(commandName, command);
    if (old != null)
    {
      log.warn("Overriding handler for command " + commandName + " from " + old.getClass().getName() + " to " + command.getClass().getName());
    }
  }



  
  public ChatHandlerResponse handleChatMessage(ChatType chatType, String message, Player sender) {
    if (!message.startsWith("//"))
    {
      return new ChatHandlerResponse(false, message);
    }

    
    String[] commandAndParams = message.split(" ", 2);
    
    String command = commandAndParams[0].substring(2);
    AdminCommand admc = this.commands.get(command);
    
    if (OptionsConfig.LOG_GMAUDIT) {
      
      if (sender.getAccessLevel() == 0) {
        log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "]: The player has tried to use the command without have the rights :");
      }
      if (sender.getTarget() != null && sender.getTarget() instanceof Creature) {
        
        Creature target = (Creature)sender.getTarget();
        
        log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "][Target : " + target.getName() + "]: " + message);
      } else {
        
        log.info("[ADMIN COMMAND] > [Name: " + sender.getName() + "]: " + message);
      } 
    } 
    if (admc == null) {
      
      PacketSendUtility.sendMessage(sender, "<There is no such admin command: " + command + ">");
      return ChatHandlerResponse.BLOCKED_MESSAGE;
    } 
    
    String[] params = new String[0];
    if (commandAndParams.length > 1) {
      params = commandAndParams[1].split(" ", admc.getSplitSize());
    }
    admc.executeCommand(sender, params);
    return ChatHandlerResponse.BLOCKED_MESSAGE;
  }





  
  void clearHandlers() {
    this.commands.clear();
  }





  
  public int getSize() {
    return this.commands.size();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\chathandlers\AdminCommandChatHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
