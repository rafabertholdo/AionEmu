package com.aionemu.commons.network;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.channels.spi.SelectorProvider;
import org.apache.log4j.Logger;




























public abstract class Dispatcher
  extends Thread
{
  private static final Logger log = Logger.getLogger(Dispatcher.class);



  
  Selector selector;



  
  private final DisconnectionThreadPool dcPool;


  
  private final Object gate = new Object();








  
  public Dispatcher(String name, DisconnectionThreadPool dcPool) throws IOException {
    super(name);
    this.selector = SelectorProvider.provider().openSelector();
    this.dcPool = dcPool;
  }






  
  abstract void closeConnection(AConnection paramAConnection);






  
  abstract void dispatch() throws IOException;





  
  public final Selector selector() {
    return this.selector;
  }









  
  public void run() {
    while (true) {
      try {
        dispatch();
        
        synchronized (this.gate) {

        
        } 
      } catch (Exception e) {
        
        log.error("Dispatcher error! " + e, e);
      } 
    } 
  }










  
  public final void register(SelectableChannel ch, int ops, AConnection att) throws IOException {
    synchronized (this.gate) {
      
      this.selector.wakeup();
      att.setKey(ch.register(this.selector, ops, att));
    } 
  }










  
  public final SelectionKey register(SelectableChannel ch, int ops, Acceptor att) throws IOException {
    synchronized (this.gate) {
      
      this.selector.wakeup();
      return ch.register(this.selector, ops, att);
    } 
  }







  
  final void accept(SelectionKey key) {
    try {
      ((Acceptor)key.attachment()).accept(key);
    }
    catch (Exception e) {
      
      log.error("Error while accepting connection: +" + e, e);
    } 
  }






  
  final void read(SelectionKey key) {
    int numRead;
    SocketChannel socketChannel = (SocketChannel)key.channel();
    AConnection con = (AConnection)key.attachment();
    
    ByteBuffer rb = con.readBuffer;











    
    try {
      numRead = socketChannel.read(rb);
    }
    catch (IOException e) {
      
      closeConnectionImpl(con);
      
      return;
    } 
    if (numRead == -1) {



      
      closeConnectionImpl(con);
      return;
    } 
    if (numRead == 0) {
      return;
    }

    
    rb.flip();
    while (rb.remaining() > 2 && rb.remaining() >= rb.getShort(rb.position())) {

      
      if (!parse(con, rb)) {
        
        closeConnectionImpl(con);
        return;
      } 
    } 
    if (rb.hasRemaining()) {
      
      con.readBuffer.compact();



    
    }
    else {



      
      rb.clear();
    } 
  }









  
  private boolean parse(AConnection con, ByteBuffer buf) {
    short sz = 0;
    
    try {
      sz = buf.getShort();
      if (sz > 1)
        sz = (short)(sz - 2); 
      ByteBuffer b = (ByteBuffer)buf.slice().limit(sz);
      b.order(ByteOrder.LITTLE_ENDIAN);
      
      buf.position(buf.position() + sz);
      
      return con.processData(b);
    }
    catch (IllegalArgumentException e) {
      
      log.warn("Error on parsing input from client - account: " + con + " packet size: " + sz + " real size:" + buf.remaining(), e);
      
      return false;
    } 
  }







  
  final void write(SelectionKey key) {
    SocketChannel socketChannel = (SocketChannel)key.channel();
    AConnection con = (AConnection)key.attachment();

    
    ByteBuffer wb = con.writeBuffer;
    
    if (wb.hasRemaining()) {
      int numWrite;
      
      try {
        numWrite = socketChannel.write(wb);
      }
      catch (IOException e) {
        
        closeConnectionImpl(con);
        
        return;
      } 
      if (numWrite == 0) {
        
        log.info("Write " + numWrite + " ip: " + con.getIP());
        
        return;
      } 
      
      if (wb.hasRemaining()) {
        return;
      }
    } 
    while (true) {
      int numWrite;
      wb.clear();
      boolean writeFailed = !con.writeData(wb);
      
      if (writeFailed) {
        
        wb.limit(0);

        
        break;
      } 
      
      try {
        numWrite = socketChannel.write(wb);
      }
      catch (IOException e) {
        
        closeConnectionImpl(con);
        
        return;
      } 
      if (numWrite == 0) {
        
        log.info("Write " + numWrite + " ip: " + con.getIP());
        
        return;
      } 
      
      if (wb.hasRemaining()) {
        return;
      }
    } 










    
    key.interestOps(key.interestOps() & 0xFFFFFFFB);

    
    if (con.isPendingClose()) {
      closeConnectionImpl(con);
    }
  }













  
  protected final void closeConnectionImpl(AConnection con) {
    if (con.onlyClose())
      this.dcPool.scheduleDisconnection(new DisconnectionTask(con), con.getDisconnectionDelay()); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\Dispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
