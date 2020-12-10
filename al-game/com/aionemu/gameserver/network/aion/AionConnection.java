package com.aionemu.gameserver.network.aion;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.Dispatcher;
import com.aionemu.commons.utils.concurrent.RunnableStatsManager;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.Crypt;
import com.aionemu.gameserver.network.aion.serverpackets.SM_KEY;
import com.aionemu.gameserver.network.factories.AionPacketHandlerFactory;
import com.aionemu.gameserver.network.loginserver.LoginServer;
import com.aionemu.gameserver.services.PlayerService;
import com.aionemu.gameserver.taskmanager.FIFORunnableQueue;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import javolution.util.FastList;
import org.apache.log4j.Logger;

public class AionConnection extends AConnection {
  private static final Logger log = Logger.getLogger(AionConnection.class);

  public enum State {
    CONNECTED,

    AUTHED,

    IN_GAME;
  }

  private final FastList<AionServerPacket> sendMsgQueue = new FastList();

  private State state;

  private Account account;

  private final Crypt crypt = new Crypt();

  private Player activePlayer;

  private String lastPlayerName = "";

  private AionPacketHandler aionPacketHandler;

  private long lastPingTimeMS;

  private FIFORunnableQueue<Runnable> packetQueue;

  public AionConnection(SocketChannel sc, Dispatcher d) throws IOException {
    super(sc, d);

    AionPacketHandlerFactory aionPacketHandlerFactory = AionPacketHandlerFactory.getInstance();
    this.aionPacketHandler = aionPacketHandlerFactory.getPacketHandler();

    this.state = State.CONNECTED;

    String ip = getIP();
    log.info("connection from: " + ip);

    sendPacket((AionServerPacket) new SM_KEY());
  }

  public final int enableCryptKey() {
    return this.crypt.enableKey();
  }

  protected final boolean processData(ByteBuffer data) {
    try {
      if (!this.crypt.decrypt(data)) {
        log.warn("Decrypt fail!");
        return false;
      }

    } catch (Exception ex) {

      log.error("Exception caught during decrypt!" + ex.getMessage());
      return false;
    }

    AionClientPacket pck = this.aionPacketHandler.handle(data, this);

    if (this.state == State.IN_GAME && this.activePlayer == null) {

      log.warn(
          "CHECKPOINT: Skipping packet processing of " + pck.getPacketName() + " for player " + this.lastPlayerName);
      return false;
    }

    if (pck != null && pck.read()) {
      getPacketQueue().execute(pck);
    }
    return true;
  }

  public FIFORunnableQueue<Runnable> getPacketQueue() {
    if (this.packetQueue == null)
      this.packetQueue = new FIFORunnableQueue<Runnable>() {
      };
    return this.packetQueue;
  }

  protected final boolean writeData(ByteBuffer data) {
    synchronized (this.guard) {

      long begin = System.nanoTime();
      if (this.sendMsgQueue.isEmpty())
        return false;
      AionServerPacket packet = (AionServerPacket) this.sendMsgQueue.removeFirst();

      try {
        packet.write(this, data);
        return true;
      } finally {

        RunnableStatsManager.handleStats(packet.getClass(), "runImpl()", System.nanoTime() - begin);
      }
    }
  }

  protected final long getDisconnectionDelay() {
    return 0L;
  }

  protected final void onDisconnect() {
    if (getAccount() != null)
      LoginServer.getInstance().aionClientDisconnected(getAccount().getId());
    if (getActivePlayer() != null) {

      Player player = getActivePlayer();

      if (player.getController().isInShutdownProgress()) {
        PlayerService.playerLoggedOut(player);

      } else {

        int delay = 15;
        PlayerService.playerLoggedOutDelay(player, delay * 1000);
      }
    }
  }

  protected final void onServerClose() {
    close(true);
  }

  public final void encrypt(ByteBuffer buf) {
    this.crypt.encrypt(buf);
  }

  public final void sendPacket(AionServerPacket bp) {
    synchronized (this.guard) {

      if (isWriteDisabled()) {
        return;
      }
      this.sendMsgQueue.addLast(bp);
      enableWriteInterest();
    }
  }

  public final void close(AionServerPacket closePacket, boolean forced) {
    synchronized (this.guard) {

      if (isWriteDisabled()) {
        return;
      }
      log.debug("sending packet: " + closePacket + " and closing connection after that.");

      this.pendingClose = true;
      this.isForcedClosing = forced;
      this.sendMsgQueue.clear();
      this.sendMsgQueue.addLast(closePacket);
      enableWriteInterest();
    }
  }

  public final State getState() {
    return this.state;
  }

  public void setState(State state) {
    this.state = state;
  }

  public Account getAccount() {
    return this.account;
  }

  public void setAccount(Account account) {
    this.account = account;
  }

  public boolean setActivePlayer(Player player) {
    if (this.activePlayer != null && player != null)
      return false;
    this.activePlayer = player;

    if (this.activePlayer == null) {
      this.state = State.AUTHED;
    } else {
      this.state = State.IN_GAME;
    }
    if (this.activePlayer != null) {
      this.lastPlayerName = player.getName();
    }
    return true;
  }

  public Player getActivePlayer() {
    return this.activePlayer;
  }

  public long getLastPingTimeMS() {
    return this.lastPingTimeMS;
  }

  public void setLastPingTimeMS(long lastPingTimeMS) {
    this.lastPingTimeMS = lastPingTimeMS;
  }
}
