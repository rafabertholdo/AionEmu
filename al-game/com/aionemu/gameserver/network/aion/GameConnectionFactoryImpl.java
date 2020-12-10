package com.aionemu.gameserver.network.aion;

import com.aionemu.commons.network.AConnection;
import com.aionemu.commons.network.ConnectionFactory;
import com.aionemu.commons.network.Dispatcher;
import java.io.IOException;
import java.nio.channels.SocketChannel;










































public class GameConnectionFactoryImpl
  implements ConnectionFactory
{
  public AConnection create(SocketChannel socket, Dispatcher dispatcher) throws IOException {
    return new AionConnection(socket, dispatcher);
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\GameConnectionFactoryImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
