package com.aionemu.gameserver.utils.gametime;





























public class GameTimeUpdater
  implements Runnable
{
  private GameTime time;
  
  public GameTimeUpdater(GameTime time) {
    this.time = time;
  }





  
  public void run() {
    this.time.increase();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserve\\utils\gametime\GameTimeUpdater.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
