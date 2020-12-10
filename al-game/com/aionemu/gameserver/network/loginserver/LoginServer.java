/*     */ package com.aionemu.gameserver.network.loginserver;
/*     */ 
/*     */ import com.aionemu.commons.network.Dispatcher;
/*     */ import com.aionemu.commons.network.NioServer;
/*     */ import com.aionemu.gameserver.configs.network.NetworkConfig;
/*     */ import com.aionemu.gameserver.model.account.Account;
/*     */ import com.aionemu.gameserver.model.account.AccountTime;
/*     */ import com.aionemu.gameserver.network.aion.AionConnection;
/*     */ import com.aionemu.gameserver.network.aion.AionServerPacket;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_L2AUTH_LOGIN_CHECK;
/*     */ import com.aionemu.gameserver.network.aion.serverpackets.SM_RECONNECT_KEY;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_AUTH;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_DISCONNECTED;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_ACCOUNT_RECONNECT_KEY;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_BAN;
/*     */ import com.aionemu.gameserver.network.loginserver.serverpackets.SM_LS_CONTROL;
/*     */ import com.aionemu.gameserver.services.AccountService;
/*     */ import com.aionemu.gameserver.utils.ThreadPoolManager;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoginServer
/*     */ {
/*  54 */   private static final Logger log = Logger.getLogger(LoginServer.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   private Map<Integer, AionConnection> loginRequests = new HashMap<Integer, AionConnection>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   private Map<Integer, AionConnection> loggedInAccounts = new HashMap<Integer, AionConnection>();
/*     */ 
/*     */   
/*     */   private LoginServerConnection loginServer;
/*     */ 
/*     */   
/*     */   private NioServer nioServer;
/*     */   
/*     */   private boolean serverShutdown = false;
/*     */ 
/*     */   
/*     */   public static final LoginServer getInstance() {
/*  77 */     return SingletonHolder.instance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNioServer(NioServer nioServer) {
/*  87 */     this.nioServer = nioServer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginServerConnection connect() {
/*     */     while (true) {
/* 101 */       this.loginServer = null;
/* 102 */       log.info("Connecting to LoginServer: " + NetworkConfig.LOGIN_ADDRESS);
/*     */       
/*     */       try {
/* 105 */         SocketChannel sc = SocketChannel.open(NetworkConfig.LOGIN_ADDRESS);
/* 106 */         sc.configureBlocking(false);
/* 107 */         Dispatcher d = this.nioServer.getReadWriteDispatcher();
/* 108 */         this.loginServer = new LoginServerConnection(sc, d);
/* 109 */         return this.loginServer;
/*     */       }
/* 111 */       catch (Exception e) {
/*     */         
/* 113 */         log.info("Cant connect to LoginServer: " + e.getMessage());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 120 */           Thread.sleep(10000L);
/*     */         }
/* 122 */         catch (Exception exception) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loginServerDown() {
/* 134 */     log.warn("Connection with LoginServer lost...");
/*     */     
/* 136 */     this.loginServer = null;
/* 137 */     synchronized (this) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 143 */       for (AionConnection client : this.loginRequests.values())
/*     */       {
/*     */         
/* 146 */         client.close(true);
/*     */       }
/* 148 */       this.loginRequests.clear();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 154 */     if (!this.serverShutdown) {
/* 155 */       ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 159 */               LoginServer.this.connect();
/*     */             }
/*     */           },  5000L);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void aionClientDisconnected(int accountId) {
/* 173 */     synchronized (this) {
/*     */       
/* 175 */       this.loginRequests.remove(Integer.valueOf(accountId));
/* 176 */       this.loggedInAccounts.remove(Integer.valueOf(accountId));
/*     */     } 
/* 178 */     sendAccountDisconnected(accountId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendAccountDisconnected(int accountId) {
/* 187 */     log.info("Sending account disconnected " + accountId);
/* 188 */     if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED) {
/* 189 */       this.loginServer.sendPacket((LsServerPacket)new SM_ACCOUNT_DISCONNECTED(accountId));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestAuthenticationOfClient(int accountId, AionConnection client, int loginOk, int playOk1, int playOk2) {
/* 208 */     if (this.loginServer == null || this.loginServer.getState() != LoginServerConnection.State.AUTHED) {
/*     */       
/* 210 */       log.warn("LS !!! " + ((this.loginServer == null) ? "NULL" : this.loginServer.getState()));
/*     */       
/* 212 */       client.close(true);
/*     */       
/*     */       return;
/*     */     } 
/* 216 */     synchronized (this) {
/*     */       
/* 218 */       if (this.loginRequests.containsKey(Integer.valueOf(accountId)))
/*     */         return; 
/* 220 */       this.loginRequests.put(Integer.valueOf(accountId), client);
/*     */     } 
/* 222 */     this.loginServer.sendPacket((LsServerPacket)new SM_ACCOUNT_AUTH(accountId, loginOk, playOk1, playOk2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accountAuthenticationResponse(int accountId, String accountName, boolean result, AccountTime accountTime, byte accessLevel, byte membership) {
/* 236 */     AionConnection client = this.loginRequests.remove(Integer.valueOf(accountId));
/*     */     
/* 238 */     if (client == null) {
/*     */       return;
/*     */     }
/* 241 */     if (result) {
/*     */       
/* 243 */       client.setState(AionConnection.State.AUTHED);
/* 244 */       this.loggedInAccounts.put(Integer.valueOf(accountId), client);
/* 245 */       log.info("Account authed: " + accountId + " = " + accountName);
/* 246 */       client.setAccount(AccountService.getAccount(accountId, accountName, accountTime, accessLevel, membership));
/* 247 */       client.sendPacket((AionServerPacket)new SM_L2AUTH_LOGIN_CHECK(true));
/*     */     }
/*     */     else {
/*     */       
/* 251 */       log.info("Account not authed: " + accountId);
/* 252 */       client.close((AionServerPacket)new SM_L2AUTH_LOGIN_CHECK(false), true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requestAuthReconnection(AionConnection client) {
/* 267 */     if (this.loginServer == null || this.loginServer.getState() != LoginServerConnection.State.AUTHED) {
/*     */ 
/*     */       
/* 270 */       client.close(false);
/*     */       
/*     */       return;
/*     */     } 
/* 274 */     synchronized (this) {
/*     */       
/* 276 */       if (this.loginRequests.containsKey(Integer.valueOf(client.getAccount().getId())))
/*     */         return; 
/* 278 */       this.loginRequests.put(Integer.valueOf(client.getAccount().getId()), client);
/*     */     } 
/*     */     
/* 281 */     this.loginServer.sendPacket((LsServerPacket)new SM_ACCOUNT_RECONNECT_KEY(client.getAccount().getId()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void authReconnectionResponse(int accountId, int reconnectKey) {
/* 293 */     AionConnection client = this.loginRequests.remove(Integer.valueOf(accountId));
/*     */     
/* 295 */     if (client == null) {
/*     */       return;
/*     */     }
/* 298 */     log.info("Account reconnectimg: " + accountId + " = " + client.getAccount().getName());
/* 299 */     client.close((AionServerPacket)new SM_RECONNECT_KEY(reconnectKey), false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void kickAccount(int accountId) {
/* 310 */     synchronized (this) {
/*     */       
/* 312 */       AionConnection client = this.loggedInAccounts.get(Integer.valueOf(accountId));
/* 313 */       if (client != null) {
/*     */         
/* 315 */         closeClientWithCheck(client, accountId);
/*     */       
/*     */       }
/*     */       else {
/*     */         
/* 320 */         sendAccountDisconnected(accountId);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void closeClientWithCheck(AionConnection client, final int accountId) {
/* 327 */     log.info("Closing client connection " + accountId);
/* 328 */     client.close(false);
/* 329 */     ThreadPoolManager.getInstance().schedule(new Runnable()
/*     */         {
/*     */           
/*     */           public void run()
/*     */           {
/* 334 */             AionConnection client = (AionConnection)LoginServer.this.loggedInAccounts.get(Integer.valueOf(accountId));
/* 335 */             if (client != null) {
/*     */               
/* 337 */               LoginServer.log.warn("Removing client from server because of stalled connection");
/* 338 */               client.close(false);
/* 339 */               LoginServer.this.loggedInAccounts.remove(Integer.valueOf(accountId));
/* 340 */               LoginServer.this.sendAccountDisconnected(accountId);
/*     */             } 
/*     */           }
/*     */         },  5000L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, AionConnection> getLoggedInAccounts() {
/* 354 */     return Collections.unmodifiableMap(this.loggedInAccounts);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void gameServerDisconnected() {
/* 362 */     synchronized (this) {
/*     */       
/* 364 */       this.serverShutdown = true;
/*     */ 
/*     */ 
/*     */       
/* 368 */       for (AionConnection client : this.loginRequests.values())
/*     */       {
/*     */         
/* 371 */         client.close(true);
/*     */       }
/* 373 */       this.loginRequests.clear();
/*     */       
/* 375 */       this.loginServer.close(false);
/*     */     } 
/*     */     
/* 378 */     log.info("GameServer disconnected from the Login Server...");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendLsControlPacket(String accountName, String playerName, String adminName, int param, int type) {
/* 384 */     if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED) {
/* 385 */       this.loginServer.sendPacket((LsServerPacket)new SM_LS_CONTROL(accountName, playerName, adminName, param, type));
/*     */     }
/*     */   }
/*     */   
/*     */   public void accountUpdate(int accountId, byte param, int type) {
/* 390 */     synchronized (this) {
/*     */       
/* 392 */       AionConnection client = this.loggedInAccounts.get(Integer.valueOf(accountId));
/* 393 */       if (client != null) {
/*     */         
/* 395 */         Account account = client.getAccount();
/* 396 */         if (type == 1)
/* 397 */           account.setAccessLevel(param); 
/* 398 */         if (type == 2) {
/* 399 */           account.setMembership(param);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void sendBanPacket(byte type, int accountId, String ip, int time, int adminObjId) {
/* 406 */     if (this.loginServer != null && this.loginServer.getState() == LoginServerConnection.State.AUTHED)
/* 407 */       this.loginServer.sendPacket((LsServerPacket)new SM_BAN(type, accountId, ip, time, adminObjId)); 
/*     */   }
/*     */   
/*     */   private LoginServer() {}
/*     */   
/*     */   private static class SingletonHolder {
/* 413 */     protected static final LoginServer instance = new LoginServer();
/*     */   }
/*     */ }


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LoginServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */