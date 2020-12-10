package com.aionemu.gameserver.network.loginserver;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

public class LsPacketHandler {
  private static final Logger log = Logger.getLogger(LsPacketHandler.class);

  private static Map<LoginServerConnection.State, Map<Integer, LsClientPacket>> packetPrototypes = new HashMap<LoginServerConnection.State, Map<Integer, LsClientPacket>>();

  public LsClientPacket handle(ByteBuffer data, LoginServerConnection client) {
    LoginServerConnection.State state = client.getState();
    int id = data.get() & 0xFF;

    return getPacket(state, id, data, client);
  }

  public void addPacketPrototype(LsClientPacket packetPrototype, LoginServerConnection.State... states) {
    for (LoginServerConnection.State state : states) {

      Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
      if (pm == null) {

        pm = new HashMap<Integer, LsClientPacket>();
        packetPrototypes.put(state, pm);
      }
      pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
    }
  }

  private LsClientPacket getPacket(LoginServerConnection.State state, int id, ByteBuffer buf,
      LoginServerConnection con) {
    LsClientPacket prototype = null;

    Map<Integer, LsClientPacket> pm = packetPrototypes.get(state);
    if (pm != null) {
      prototype = pm.get(Integer.valueOf(id));
    }

    if (prototype == null) {

      unknownPacket(state, id);
      return null;
    }

    LsClientPacket res = prototype.clonePacket();
    res.setBuffer(buf);
    res.setConnection(con);

    return res;
  }

  private void unknownPacket(LoginServerConnection.State state, int id) {
    log.warn(String.format("Unknown packet recived from Login Server: 0x%02X state=%s",
        new Object[] { Integer.valueOf(id), state.toString() }));
  }
}
