package com.aionemu.commons.network;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;






























































public class Acceptor
{
  private final ConnectionFactory factory;
  private final NioServer nioServer;
  
  Acceptor(ConnectionFactory factory, NioServer nioServer) {
    this.factory = factory;
    this.nioServer = nioServer;
  }





















  
  public final void accept(SelectionKey key) throws IOException {
    ServerSocketChannel serverSocketChannel = (ServerSocketChannel)key.channel();
    
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.configureBlocking(false);
    
    Dispatcher dispatcher = this.nioServer.getReadWriteDispatcher();
    this.factory.create(socketChannel, dispatcher);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\Acceptor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
