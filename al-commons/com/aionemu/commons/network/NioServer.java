package com.aionemu.commons.network;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class NioServer {
  private static final Logger log = Logger.getLogger(NioServer.class.getName());

  private final List<SelectionKey> serverChannelKeys = new ArrayList<SelectionKey>();

  private Dispatcher acceptDispatcher;

  private int currentReadWriteDispatcher;

  private Dispatcher[] readWriteDispatchers;

  private final DisconnectionThreadPool dcPool;

  private int readWriteThreads;

  private ServerCfg[] cfgs;

  public NioServer(int readWriteThreads, DisconnectionThreadPool dcPool, ServerCfg... cfgs) {
    this.dcPool = dcPool;
    this.readWriteThreads = readWriteThreads;
    this.cfgs = cfgs;
  }

  public void connect() {
    try {
      initDispatchers(this.readWriteThreads, this.dcPool);

      for (ServerCfg cfg : this.cfgs) {
        InetSocketAddress isa;
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);

        if ("*".equals(cfg.hostName)) {

          isa = new InetSocketAddress(cfg.port);
          log.info("Server listening on all available IPs on Port " + cfg.port + " for " + cfg.connectionName);

        } else {

          isa = new InetSocketAddress(cfg.hostName, cfg.port);
          log.info("Server listening on IP: " + cfg.hostName + " Port " + cfg.port + " for " + cfg.connectionName);
        }

        serverChannel.socket().bind(isa);

        SelectionKey acceptKey = getAcceptDispatcher().register(serverChannel, 16, new Acceptor(cfg.factory, this));

        this.serverChannelKeys.add(acceptKey);
      }

    } catch (Exception e) {

      log.fatal("NioServer Initialization Error: " + e, e);
      throw new Error("NioServer Initialization Error!");
    }
  }

  public final Dispatcher getAcceptDispatcher() {
    return this.acceptDispatcher;
  }

  public final Dispatcher getReadWriteDispatcher() {
    if (this.readWriteDispatchers == null) {
      return this.acceptDispatcher;
    }
    if (this.readWriteDispatchers.length == 1) {
      return this.readWriteDispatchers[0];
    }
    if (this.currentReadWriteDispatcher >= this.readWriteDispatchers.length)
      this.currentReadWriteDispatcher = 0;
    return this.readWriteDispatchers[this.currentReadWriteDispatcher++];
  }

  private void initDispatchers(int readWriteThreads, DisconnectionThreadPool dcPool) throws IOException {
    if (readWriteThreads <= 1) {

      this.acceptDispatcher = new AcceptReadWriteDispatcherImpl("AcceptReadWrite Dispatcher", dcPool);
      this.acceptDispatcher.start();
    } else {

      this.acceptDispatcher = new AcceptDispatcherImpl("Accept Dispatcher");
      this.acceptDispatcher.start();

      this.readWriteDispatchers = new Dispatcher[readWriteThreads];
      for (int i = 0; i < this.readWriteDispatchers.length; i++) {

        this.readWriteDispatchers[i] = new AcceptReadWriteDispatcherImpl("ReadWrite-" + i + " Dispatcher", dcPool);
        this.readWriteDispatchers[i].start();
      }
    }
  }

  public final int getActiveConnections() {
    int count = 0;
    if (this.readWriteDispatchers != null) {

      for (Dispatcher d : this.readWriteDispatchers) {
        count += d.selector().keys().size();
      }
    } else {

      count = this.acceptDispatcher.selector().keys().size() - this.serverChannelKeys.size();
    }
    return count;
  }

  public final void shutdown() {
    log.info("Closing ServerChannels...");

    try {
      for (SelectionKey key : this.serverChannelKeys)
        key.cancel();
      log.info("ServerChannel closed.");
    } catch (Exception e) {

      log.error("Error during closing ServerChannel, " + e, e);
    }

    notifyServerClose();

    try {
      Thread.sleep(1000L);
    } catch (Throwable t) {

      log.warn("Nio thread was interrupted during shutdown", t);
    }

    log.info(" Active connections: " + getActiveConnections());

    log.info("Forced Disconnecting all connections...");
    closeAll();
    log.info(" Active connections: " + getActiveConnections());

    this.dcPool.waitForDisconnectionTasks();

    try {
      Thread.sleep(1000L);
    } catch (Throwable t) {

      log.warn("Nio thread was interrupted during shutdown", t);
    }
  }

  private void notifyServerClose() {
    if (this.readWriteDispatchers != null) {

      for (Dispatcher d : this.readWriteDispatchers) {
        for (SelectionKey key : d.selector().keys()) {

          if (key.attachment() instanceof AConnection) {
            ((AConnection) key.attachment()).onServerClose();
          }
        }
      }
    } else {

      for (SelectionKey key : this.acceptDispatcher.selector().keys()) {

        if (key.attachment() instanceof AConnection) {
          ((AConnection) key.attachment()).onServerClose();
        }
      }
    }
  }

  private void closeAll() {
    if (this.readWriteDispatchers != null) {

      for (Dispatcher d : this.readWriteDispatchers) {
        for (SelectionKey key : d.selector().keys()) {

          if (key.attachment() instanceof AConnection) {
            ((AConnection) key.attachment()).close(true);
          }
        }
      }
    } else {

      for (SelectionKey key : this.acceptDispatcher.selector().keys()) {

        if (key.attachment() instanceof AConnection) {
          ((AConnection) key.attachment()).close(true);
        }
      }
    }
  }
}
