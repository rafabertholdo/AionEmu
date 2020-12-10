package com.aionemu.gameserver.taskmanager;

import com.aionemu.commons.taskmanager.AbstractLockManager;
import com.aionemu.commons.utils.Rnd;
import com.aionemu.gameserver.GameServer;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.apache.log4j.Logger;

public abstract class AbstractPeriodicTaskManager extends AbstractLockManager
    implements Runnable, GameServer.StartupHook {
  protected static final Logger log = Logger.getLogger(AbstractPeriodicTaskManager.class);

  private final int period;

  public AbstractPeriodicTaskManager(int period) {
    this.period = period;

    GameServer.addStartupHook(this);

    log.info(getClass().getSimpleName() + ": Initialized.");
  }

  public final void onStartup() {
    ThreadPoolManager.getInstance().scheduleAtFixedRate(this, (1000 + Rnd.get(this.period)),
        Rnd.get(this.period - 5, this.period + 5));
  }

  public abstract void run();
}
