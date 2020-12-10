package admincommands;

import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.Npc;
import com.aionemu.gameserver.model.gameobjects.VisibleObject;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;





















public class AiCommand
  extends AdminCommand
{
  public AiCommand() {
    super("ai");
  }


  
  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_AI) {
      
      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command");
      
      return;
    } 
    if (params == null || params.length < 1) {
      
      PacketSendUtility.sendMessage(admin, "syntax //ai <info|event|state>");
      
      return;
    } 
    VisibleObject target = admin.getTarget();
    
    if (target == null || !(target instanceof Npc)) {
      
      PacketSendUtility.sendMessage(admin, "Select target first (Npc only)");
      
      return;
    } 
    Npc npc = (Npc)target;
    
    if (params[0].equals("info")) {
      
      PacketSendUtility.sendMessage(admin, "Ai state: " + npc.getAi().getAiState());
      PacketSendUtility.sendMessage(admin, "Ai desires size: " + npc.getAi().desireQueueSize());
      PacketSendUtility.sendMessage(admin, "Ai task scheduled: " + npc.getAi().isScheduled());
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AiCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
