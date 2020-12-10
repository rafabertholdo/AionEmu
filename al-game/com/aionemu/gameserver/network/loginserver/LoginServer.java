package com.aionemu.gameserver.network.loginserver;

import com.aionemu.commons.network.Dispatcher;
import com.aionemu.commons.network.NioServer;
import com.aionemu.gameserver.configs.network.NetworkConfig;
import com.aionemu.gameserver.model.account.Account;
import com.aionemu.gameserver.model.account.AccountTime;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_L2AUTH_LOGIN_CHECK;
import com.aionemu.gameserver.network.aion.serverpackets.SM_RECONNECT_KEY;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_AUTH;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_DISCONNECTED;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_RECONNECT_KEY;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_BAN;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_LS_CONTROL;
import com.aionemu.gameserver.services.AccountService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.nio.channels.SocketChannel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class LoginServer {
  private static final Logger log = Logger.getLogger(LoginServer.class);

  private Map<Integer, AionConnection> loginRequests = new HashMap<Integer, AionConnection>();

  private Map<Integer, AionConnection> loggedInAccounts = new HashMap<Integer, AionConnection>();

  private LoginServerConnection loginServer;

  private NioServer nioServer;

  private boolean serverShutdown = false;

  public static final LoginServer getInstance() {
    return SingletonHolder.instance;
  }

  public void setNioServer(NioServer nioServer) {
    this.nioServer = nioServer;
  }

  public LoginServerConnection connect() {
    while (true) {
      this.loginServer = null;
      log.info("Connecting to LoginServer: " + NetworkConfig.LOGIN_ADDRESS);

      try {
        SocketChannel sc = SocketChannel.open(NetworkConfig.LOGIN_ADDRESS);
        sc.configureBlocking(false);
        Dispatcher d = this.nioServer.getReadWriteDispatcher();
        this.loginServer = new LoginServerConnection(sc, d);
        return this.loginServer;
      } catch (Exception e) {

        log.info("Cant connect to LoginServer: " + e.getMessage());

        try {
          Thread.sleep(10000L);
        } catch (Exception exception) {
        }
      }
    }
  }

  public void loginServerDown() {
    log.warn("Connection with LoginServer lost...");

    this.loginServer = null;
    synchronized (this) {

      for (AionConnection client : this.loginRequests.values()) {

        client.close(true);
      }
      this.loginRequests.clear();
    }

    if (!this.serverShutdown) {
      ThreadPoolManager.getInstance().schedule(new Runnable() {
        public void run() {
          LoginServer.this.connect();
        }
      }, 5000L);
    }
  }

  public void aionClientDisconnected(int accountId) {
    synchronized (this) {

      this.loginRequests.remove(Integer.valueOf(accountId));
      this.loggedInAccounts.remove(Integer.valueOf(accountId));
    }
    sendAccountDisconnected(accountId);
  }

  private void sendAccountDisconnected(int accountId) {
    log.info("Sending account disconnected " + accountId);
    if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED) {
      this.loginServer.sendPacket((LsServerPacket) new SM_ACCOUNT_DISCONNECTED(accountId));
    }
  }

  public void requestAuthenticationOfClient(int accountId, AionConnection client, int loginOk, int playOk1,
      int playOk2) {
    if (this.loginServer == null || this.loginServer.getState() != LoginServerConnection.State.AUTHED) {

      log.warn("LS !!! " + ((this.loginServer == null) ? "NULL" : this.loginServer.getState()));

      client.close(true);

      return;
    }
    synchronized (this) {

      if (this.loginRequests.containsKey(Integer.valueOf(accountId)))
        return;
      this.loginRequests.put(Integer.valueOf(accountId), client);
    }
    this.loginServer.sendPacket((LsServerPacket) new SM_ACCOUNT_AUTH(accountId, loginOk, playOk1, playOk2));
  }

  public void accountAuthenticationResponse(int accountId, String accountName, boolean result, AccountTime accountTime,
      byte accessLevel, byte membership) {
    AionConnection client = this.loginRequests.remove(Integer.valueOf(accountId));

    if (client == null) {
      return;
    }
    if (result) {

      client.setState(AionConnection.State.AUTHED);
      this.loggedInAccounts.put(Integer.valueOf(accountId), client);
      log.info("Account authed: " + accountId + " = " + accountName);
      client.setAccount(AccountService.getAccount(accountId, accountName, accountTime, accessLevel, membership));
      client.sendPacket((AionServerPacket) new SM_L2AUTH_LOGIN_CHECK(true));
    } else {

      log.info("Account not authed: " + accountId);
      client.close((AionServerPacket) new SM_L2AUTH_LOGIN_CHECK(false), true);
    }
  }

  public void requestAuthReconnection(AionConnection client) {
    if (this.loginServer == null || this.loginServer.getState() != LoginServerConnection.State.AUTHED) {

      client.close(false);

      return;
    }
    synchronized (this) {

      if (this.loginRequests.containsKey(Integer.valueOf(client.getAccount().getId())))
        return;
      this.loginRequests.put(Integer.valueOf(client.getAccount().getId()), client);
    }

    this.loginServer.sendPacket((LsServerPacket) new SM_ACCOUNT_RECONNECT_KEY(client.getAccount().getId()));
  }

  public void authReconnectionResponse(int accountId, int reconnectKey) {
    AionConnection client = this.loginRequests.remove(Integer.valueOf(accountId));

    if (client == null) {
      return;
    }
    log.info("Account reconnectimg: " + accountId + " = " + client.getAccount().getName());
    client.close((AionServerPacket) new SM_RECONNECT_KEY(reconnectKey), false);
  }

  public void kickAccount(int accountId) {
    synchronized (this) {

      AionConnection client = this.loggedInAccounts.get(Integer.valueOf(accountId));
      if (client != null) {

        closeClientWithCheck(client, accountId);

      } else {

        sendAccountDisconnected(accountId);
      }
    }
  }

  private void closeClientWithCheck(AionConnection client, final int accountId) {
    log.info("Closing client connection " + accountId);
    client.close(false);
    ThreadPoolManager.getInstance().schedule(new Runnable() {

      public void run() {
        AionConnection client = (AionConnection) LoginServer.this.loggedInAccounts.get(Integer.valueOf(accountId));
        if (client != null) {

          LoginServer.log.warn("Removing client from server because of stalled connection");
          client.close(false);
          LoginServer.this.loggedInAccounts.remove(Integer.valueOf(accountId));
          LoginServer.this.sendAccountDisconnected(accountId);
        }
      }
    }, 5000L);
  }

  public Map<Integer, AionConnection> getLoggedInAccounts() {
    return Collections.unmodifiableMap(this.loggedInAccounts);
  }

  public void gameServerDisconnected() {
    synchronized (this) {

      this.serverShutdown = true;

      for (AionConnection client : this.loginRequests.values()) {

        client.close(true);
      }
      this.loginRequests.clear();

      this.loginServer.close(false);
    }

    log.info("GameServer disconnected from the Login Server...");
  }

  public void sendLsControlPacket(String accountName, String playerName, String adminName, int param, int type) {
    if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED) {
      this.loginServer.sendPacket((LsServerPacket) new SM_LS_CONTROL(accountName, playerName, adminName, param, type));
    }
  }

  public void accountUpdate(int accountId, byte param, int type) {
    synchronized (this) {

      AionConnection client = this.loggedInAccounts.get(Integer.valueOf(accountId));
      if (client != null) {

        Account account = client.getAccount();
        if (type == 1)
          account.setAccessLevel(param);
        if (type == 2) {
          account.setMembership(param);
        }
      }
    }
  }

  public void sendBanPacket(byte type, int accountId, String ip, int time, int adminObjId) {
    if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED)
      this.loginServer.sendPacket((LsServerPacket) new SM_BAN(type, accountId, ip, time, adminObjId));
  }

  private LoginServer() {
  }

  private static class SingletonHolder {
    protected static final LoginServer instance = new LoginServer();
  }
}
