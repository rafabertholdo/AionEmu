package com.aionemu.gameserver.network.chatserver.clientpackets;

import com.aionemu.gameserver.network.chatserver.ChatServerConnection;
import com.aionemu.gameserver.network.chatserver.CsClientPacket;
import com.aionemu.gameserver.network.chatserver.CsServerPacket;
import com.aionemu.gameserver.network.chatserver.serverpackets.SM_CS_AUTH;
import com.aionemu.gameserver.services.ChatService;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import org.apache.log4j.Logger;

public class CM_CS_AUTH_RESPONSE extends CsClientPacket {
  protected static final Logger log = Logger.getLogger(CM_CS_AUTH_RESPONSE.class);

  private int response;

  private byte[] ip;

  private int port;

  public CM_CS_AUTH_RESPONSE(int opcode) {
    super(opcode);
  }

  protected void readImpl() {
    this.response = readC();
    this.ip = readB(4);
    this.port = readH();
  }

  protected void runImpl() {
    switch (this.response) {

      case 0:
        log.info("GameServer authed successfully IP : " + (this.ip[0] & 0xFF) + "." + (this.ip[1] & 0xFF) + "."
            + (this.ip[2] & 0xFF) + "." + (this.ip[3] & 0xFF) + " Port: " + this.port);
        ((ChatServerConnection) getConnection()).setState(ChatServerConnection.State.AUTHED);
        ChatService.setIp(this.ip);
        ChatService.setPort(this.port);
        break;
      case 1:
        log.fatal("GameServer is not authenticated at ChatServer side");
        System.exit(1);
        break;
      case 2:
        log.info("GameServer is already registered at ChatServer side! trying again...");
        ThreadPoolManager.getInstance().schedule(new Runnable() {
          public void run() {
            ((ChatServerConnection) CM_CS_AUTH_RESPONSE.this.getConnection())
                .sendPacket((CsServerPacket) new SM_CS_AUTH());
          }
        }, 10000L);
        break;
    }
  }
}
