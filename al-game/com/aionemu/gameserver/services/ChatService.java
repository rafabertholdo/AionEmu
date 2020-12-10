package com.aionemu.gameserver.services;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_CHAT_INIT;
import com.aionemu.gameserver.network.chatserver.ChatServer;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import com.aionemu.gameserver.world.World;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class ChatService {
  private static final Logger log = Logger.getLogger(ChatService.class);

  private static Map<Integer, Player> players = new HashMap<Integer, Player>();

  private static byte[] ip = new byte[] { Byte.MAX_VALUE, 0, 0, 1 };
  private static int port = 10241;

  public static void onPlayerLogin(final Player player) {
    ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        if (!ChatService.isPlayerConnected(player)) {

          ChatServer.getInstance().sendPlayerLoginRequst(player);
        } else {

          ChatService.log.warn("Player already registered with chat server " + player.getName());
        }
      }
    }, 10000L);
  }

  public static void onPlayerLogout(Player player) {
    players.remove(Integer.valueOf(player.getObjectId()));
    ChatServer.getInstance().sendPlayerLogout(player);
  }

  public static boolean isPlayerConnected(Player player) {
    return players.containsKey(Integer.valueOf(player.getObjectId()));
  }

  public static void playerAuthed(int playerId, byte[] token) {
    Player player = World.getInstance().findPlayer(playerId);
    if (player != null) {

      players.put(Integer.valueOf(playerId), player);
      PacketSendUtility.sendPacket(player, (AionServerPacket) new SM_CHAT_INIT(token));
    }
  }

  public static byte[] getIp() {
    return ip;
  }

  public static int getPort() {
    return port;
  }

  public static void setIp(byte[] _ip) {
    ip = _ip;
  }

  public static void setPort(int _port) {
    port = _port;
  }
}
