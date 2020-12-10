package com.aionemu.commons.network;

































public class DisconnectionTask
  implements Runnable
{
  private AConnection connection;
  
  public DisconnectionTask(AConnection connection) {
    this.connection = connection;
  }





  
  public void run() {
    this.connection.onDisconnect();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\DisconnectionTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
