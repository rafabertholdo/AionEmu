package com.aionemu.gameserver.network.chatserver;

import com.aionemu.commons.network.Dispatcher;
import com.aionemu.commons.network.NioServer;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_PLAYER_AUTH;
import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_PLAYER_LOGOUT;
import com.aionemu.gameserver.network.factories.CsPacketHandlerFactory;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.nio.channels.SocketChannel;
import org.apache.log4j.Logger;

public class ChatServer {
  private static final Logger log = Logger.getLogger(ChatServer.class);

  private ChatServerConnection chatServer;

  private NioServer nioServer;

  private boolean serverShutdown = false;

  public static final ChatServer getInstance() {
    return SingletonHolder.instance;
  }

  public void setNioServer(NioServer nioServer) {
    this.nioServer = nioServer;
  }

  public ChatServerConnection connect() {
    while (true) {
      this.chatServer = null;
      log.info("Connecting to ChatServer: " + NetworkConfig.CHAT_ADDRESS);

      try {
        SocketChannel sc = SocketChannel.open(NetworkConfig.CHAT_ADDRESS);
        sc.configureBlocking(false);
        Dispatcher d = this.nioServer.getReadWriteDispatcher();
        CsPacketHandlerFactory csPacketHandlerFactory = new CsPacketHandlerFactory();
        this.chatServer = new ChatServerConnection(sc, d, csPacketHandlerFactory.getPacketHandler());
        return this.chatServer;
      } catch (Exception e) {

        log.info("Cant connect to ChatServer: " + e.getMessage());

        try {
          Thread.sleep(10000L);
        } catch (Exception exception) {
        }
      }
    }
  }

  public void chatServerDown() {
    log.warn("Connection with ChatServer lost...");

    this.chatServer = null;

    if (!this.serverShutdown) {
      ThreadPoolManager.getInstance().schedule(new Runnable() {
        public void run() {
          ChatServer.this.connect();
        }
      }, 5000L);
    }
  }

  public void sendPlayerLoginRequst(Player player) {
    if (this.chatServer != null) {
      this.chatServer.sendPacket((CsServerPacket) new SM_CS_PLAYER_AUTH(player.getObjectId(), player.getAcountName()));
    }
  }

  public void sendPlayerLogout(Player player) {
    if (this.chatServer != null)
      this.chatServer.sendPacket((CsServerPacket) new SM_CS_PLAYER_LOGOUT(player.getObjectId()));
  }

  private ChatServer() {
  }

  private static class SingletonHolder {
    protected static final ChatServer instance = new ChatServer();
  }
}
