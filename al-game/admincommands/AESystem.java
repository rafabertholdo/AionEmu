package admincommands;

import com.aionemu.commons.utils.AEInfos;
import com.aionemu.gameserver.ShutdownHook;
import com.aionemu.gameserver.configs.administration.AdminConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.utils.VersionningService;
import com.aionemu.gameserver.utils.chathandlers.AdminCommand;
import java.util.List;





























public class AESystem
  extends AdminCommand
{
  public AESystem() {
    super("sys");
  }


  
  public void executeCommand(Player admin, String[] params) {
    if (admin.getAccessLevel() < AdminConfig.COMMAND_SYSTEM) {
      
      PacketSendUtility.sendMessage(admin, "You dont have enough rights to execute this command!");
      
      return;
    } 
    if (params == null || params.length < 1) {
      
      PacketSendUtility.sendMessage(admin, "Usage: //sys info | //sys memory | //sys gc | //sys restart <countdown time> <announce delay> | //sys shutdown <countdown time> <announce delay>");
      
      return;
    } 
    if (params[0].equals("info")) {

      
      PacketSendUtility.sendMessage(admin, "System Informations at: " + AEInfos.getRealTime().toString());

      
      for (String line : VersionningService.getFullVersionInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
      
      for (String line : AEInfos.getOSInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
      
      for (String line : AEInfos.getCPUInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
      
      for (String line : AEInfos.getJREInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
      
      for (String line : AEInfos.getJVMInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
    }
    else if (params[0].equals("memory")) {

      
      for (String line : AEInfos.getMemoryInfo()) {
        PacketSendUtility.sendMessage(admin, line);
      }
    }
    else if (params[0].equals("gc")) {
      
      long time = System.currentTimeMillis();
      PacketSendUtility.sendMessage(admin, "RAM Used (Before): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
      
      System.gc();
      PacketSendUtility.sendMessage(admin, "RAM Used (After): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
      
      System.runFinalization();
      PacketSendUtility.sendMessage(admin, "RAM Used (Final): " + ((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L));
      
      PacketSendUtility.sendMessage(admin, "Garbage Collection and Finalization finished in: " + (System.currentTimeMillis() - time) + " milliseconds...");
    
    }
    else if (params[0].equals("shutdown")) {
      
      try
      {
        int val = Integer.parseInt(params[1]);
        int announceInterval = Integer.parseInt(params[2]);
        ShutdownHook.getInstance().doShutdown(val, announceInterval, ShutdownHook.ShutdownMode.SHUTDOWN);
        PacketSendUtility.sendMessage(admin, "Server will shutdown in " + val + " seconds.");
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
        PacketSendUtility.sendMessage(admin, "Numbers only!");
      }
      catch (NumberFormatException e)
      {
        PacketSendUtility.sendMessage(admin, "Numbers only!");
      }
    
    } else if (params[0].equals("restart")) {
      
      try
      {
        int val = Integer.parseInt(params[1]);
        int announceInterval = Integer.parseInt(params[2]);
        ShutdownHook.getInstance().doShutdown(val, announceInterval, ShutdownHook.ShutdownMode.RESTART);
        PacketSendUtility.sendMessage(admin, "Server will restart in " + val + " seconds.");
      }
      catch (ArrayIndexOutOfBoundsException e)
      {
        PacketSendUtility.sendMessage(admin, "Numbers only!");
      }
      catch (NumberFormatException e)
      {
        PacketSendUtility.sendMessage(admin, "Numbers only!");
      }
    
    } else if (params[0].equals("threadpool")) {
      
      List<String> stats = ThreadPoolManager.getInstance().getStats();
      for (String stat : stats)
      {
        PacketSendUtility.sendMessage(admin, stat.replaceAll("\t", ""));
      }
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\admincommands\AESystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
