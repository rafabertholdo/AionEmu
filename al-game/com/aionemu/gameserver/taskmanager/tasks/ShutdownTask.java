package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.ShutdownHook;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class ShutdownTask extends TaskFromDBHandler {
  private static final Logger log = Logger.getLogger(ShutdownTask.class);

  private int countDown;

  private int announceInterval;

  private int warnCountDown;

  public String getTaskName() {
    return "shutdown";
  }

  public boolean isValid() {
    if (this.params.length == 3) {
      return true;
    }
    return false;
  }

  public void run() {
    log.info("Task[" + this.id + "] launched : shuting down the server !");
    setLastActivation();
    
    this.countDown = Integer.parseInt(this.params[0]);
    this.announceInterval = Integer.parseInt(this.params[1]);
    this.warnCountDown = Integer.parseInt(this.params[2]);
    
    for (Player player : World.getInstance().getAllPlayers()) {
      PacketSendUtility.sendSysMessage(player, "Automatic Task: The server will shutdown in " + this.warnCountDown + " seconds ! Please find a peace place and disconnect your character.");
    }
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            ShutdownHook.getInstance().doShutdown(ShutdownTask.this.countDown, ShutdownTask.this.announceInterval, ShutdownHook.ShutdownMode.SHUTDOWN);
          }
        }(this.warnCountDown * 1000));
  }
}
