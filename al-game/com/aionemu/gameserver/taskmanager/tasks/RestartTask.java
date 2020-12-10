package com.aionemu.gameserver.taskmanager.tasks;

import com.aionemu.gameserver.ShutdownHook;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.templates.tasks.TaskFromDBHandler;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import org.apache.log4j.Logger;

public class RestartTask extends TaskFromDBHandler {
  private static final Logger log = Logger.getLogger(RestartTask.class);

  private int countDown;

  private int announceInterval;

  private int warnCountDown;

  public String getTaskName() {
    return "restart";
  }

  public boolean isValid() {
    if (this.params.length == 3) {
      return true;
    }
    return false;
  }

  public void run() {
    log.info("Task[" + this.id + "] launched : restarting the server !");
    setLastActivation();
    
    this.countDown = Integer.parseInt(this.params[0]);
    this.announceInterval = Integer.parseInt(this.params[1]);
    this.warnCountDown = Integer.parseInt(this.params[2]);
    
    for (Player player : World.getInstance().getAllPlayers()) {
      PacketSendUtility.sendSysMessage(player, "Automatic Task: The server will restart in " + this.warnCountDown + " seconds ! Please find a safe place and disconnect your character.");
    }
    ThreadPoolManager.getInstance().schedule(new Runnable()
        {
          public void run()
          {
            ShutdownHook.getInstance().doShutdown(RestartTask.this.countDown, RestartTask.this.announceInterval, ShutdownHook.ShutdownMode.RESTART);
          }
        }(this.warnCountDown * 1000));
  }
}
