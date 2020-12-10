package com.aionemu.gameserver.network.aion;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.ConnectionFactory;
import com.aionemu.commons.network.Dispatcher;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class GameConnectionFactoryImpl implements ConnectionFactory {
  public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
    return new AionConnection(socket, dispatcher);
  }
}
