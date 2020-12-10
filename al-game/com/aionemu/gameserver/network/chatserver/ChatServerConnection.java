package com.aionemu.gameserver.network.chatserver;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.Dispatcher;
import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_AUTH;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.log4j.Logger;

public class ChatServerConnection extends AConnection {
  private static final Logger log = Logger.getLogger(ChatServerConnection.class);

  public enum State {
    CONNECTED,

    AUTHED;
  }

  private final Deque<CsServerPacket> sendMsgQueue = new ArrayDeque<CsServerPacket>();

  private State state;

  private ChatServer chatServer;

  private CsPacketHandler csPacketHandler;

  public ChatServerConnection(SocketChannel sc, Dispatcher d, CsPacketHandler csPacketHandler) throws IOException {
    super(sc, d);
    this.chatServer = ChatServer.getInstance();
    this.csPacketHandler = csPacketHandler;

    this.state = State.CONNECTED;
    log.info("Connected to ChatServer!");

    sendPacket((CsServerPacket) new SM_CS_AUTH());
  }

  public boolean processData(ByteBuffer data) {
    CsClientPacket pck = this.csPacketHandler.handle(data, this);
    log.info("recived packet: " + pck);

    if (pck != null && pck.read()) {
      ThreadPoolManager.getInstance().executeLsPacket((Runnable) pck);
    }
    return true;
  }

  protected final boolean writeData(ByteBuffer data) {
    synchronized (this.guard) {

      CsServerPacket packet = this.sendMsgQueue.pollFirst();
      if (packet == null) {
        return false;
      }
      packet.write(this, data);
      return true;
    }
  }

  protected final long getDisconnectionDelay() {
    return 0L;
  }

  protected final void onDisconnect() {
    this.chatServer.chatServerDown();
  }

  protected final void onServerClose() {
    close(true);
  }

  public final void sendPacket(CsServerPacket bp) {
    synchronized (this.guard) {

      if (isWriteDisabled()) {
        return;
      }
      log.info("sending packet: " + bp);

      this.sendMsgQueue.addLast(bp);
      enableWriteInterest();
    }
  }

  public final void close(CsServerPacket closePacket, boolean forced) {
    synchronized (this.guard) {

      if (isWriteDisabled()) {
        return;
      }
      log.info("sending packet: " + closePacket + " and closing connection after that.");

      this.pendingClose = true;
      this.isForcedClosing = forced;
      this.sendMsgQueue.clear();
      this.sendMsgQueue.addLast(closePacket);
      enableWriteInterest();
    }
  }

  public State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public String toString() {
    return "ChatServer " + getIP();
  }
}
