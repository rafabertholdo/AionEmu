package com.aionemu.commons.network;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public interface ConnectionFactory {
  AConnection create(SocketChannel paramSocketChannel, Dispatcher paramDispatcher) throws IOException;
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\libs\al-commons-1.0.1.jar!\com\aionemu\commons\network\ConnectionFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */