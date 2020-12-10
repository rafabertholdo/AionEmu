package com.aionemu.gameserver.network.loginserver;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.Dispatcher;
import com.aionemu.gameserver.network.factories.LsPacketHandlerFactory;
import com.aionemu.gameserver.network.loginserver.serverpackets.SM_GS_AUTH;
import com.aionemu.gameserver.utils.ThreadPoolManager;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.ArrayDeque;
import java.util.Deque;
import org.apache.log4j.Logger;



























public class LoginServerConnection
  extends AConnection
{
  private static final Logger log = Logger.getLogger(LoginServerConnection.class);






  
  public enum State
  {
    CONNECTED,


    
    AUTHED;
  }



  
  private final Deque<LsServerPacket> sendMsgQueue = new ArrayDeque<LsServerPacket>();




  
  private State state;



  
  private LsPacketHandler lsPacketHandler;




  
  public LoginServerConnection(SocketChannel sc, Dispatcher d) throws IOException {
    super(sc, d);
    LsPacketHandlerFactory lsPacketHandlerFactory = LsPacketHandlerFactory.getInstance();
    this.lsPacketHandler = lsPacketHandlerFactory.getPacketHandler();
    
    this.state = State.CONNECTED;
    log.info("Connected to LoginServer!");



    
    sendPacket((LsServerPacket)new SM_GS_AUTH());
  }








  
  public boolean processData(ByteBuffer data) {
    LsClientPacket pck = this.lsPacketHandler.handle(data, this);
    log.info("recived packet: " + pck);



    
    if (pck != null && pck.read()) {
      ThreadPoolManager.getInstance().executeLsPacket((Runnable)pck);
    }
    return true;
  }








  
  protected final boolean writeData(ByteBuffer data) {
    synchronized (this.guard) {
      
      LsServerPacket packet = this.sendMsgQueue.pollFirst();
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
    LoginServer.getInstance().loginServerDown();
  }






  
  protected final void onServerClose() {
    close(true);
  }







  
  public final void sendPacket(LsServerPacket bp) {
    synchronized (this.guard) {



      
      if (isWriteDisabled()) {
        return;
      }
      log.info("sending packet: " + bp);
      
      this.sendMsgQueue.addLast(bp);
      enableWriteInterest();
    } 
  }











  
  public final void close(LsServerPacket closePacket, boolean forced) {
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
    return "LoginServer " + getIP();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\loginserver\LoginServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
