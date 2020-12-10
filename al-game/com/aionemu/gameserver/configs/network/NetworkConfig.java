package com.aionemu.gameserver.configs.network;

import com.aionemu.commons.configuration.Property;
import java.net.InetSocketAddress;

public class NetworkConfig {
  @Property(key = "network.client.port", defaultValue = "7777")
  public static int GAME_PORT;
  
  @Property(key = "network.client.host", defaultValue = "*")
  public static String GAME_BIND_ADDRESS;
  
  @Property(key = "network.client.maxplayers", defaultValue = "100")
  public static int MAX_ONLINE_PLAYERS;
  
  @Property(key = "network.login.address", defaultValue = "localhost:9014")
  public static InetSocketAddress LOGIN_ADDRESS;
  
  @Property(key = "network.chat.address", defaultValue = "localhost:9021")
  public static InetSocketAddress CHAT_ADDRESS;
  
  @Property(key = "network.chat.password", defaultValue = "")
  public static String CHAT_PASSWORD;
  
  @Property(key = "network.login.gsid", defaultValue = "0")
  public static int GAMESERVER_ID;
  
  @Property(key = "network.login.password", defaultValue = "")
  public static String LOGIN_PASSWORD;
  
  @Property(key = "network.nio.threads.read", defaultValue = "0")
  public static int NIO_READ_THREADS;
  
  @Property(key = "network.nio.threads.write", defaultValue = "0")
  public static int NIO_WRITE_THREADS;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\configs\network\NetworkConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
