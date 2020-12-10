package com.aionemu.gameserver.utils.gametime;

public class GameTimeUpdater implements Runnable {
  private GameTime time;

  public GameTimeUpdater(GameTime time) {
    this.time = time;
  }

  public void run() {
    this.time.increase();
  }
}
