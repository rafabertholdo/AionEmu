package com.aionemu.commons.network;





















































public class ServerCfg
{
  public final String hostName;
  public final int port;
  public final String connectionName;
  public final ConnectionFactory factory;
  
  public ServerCfg(String hostName, int port, String connectionName, ConnectionFactory factory) {
    this.hostName = hostName;
    this.port = port;
    this.connectionName = connectionName;
    this.factory = factory;
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\ServerCfg.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
