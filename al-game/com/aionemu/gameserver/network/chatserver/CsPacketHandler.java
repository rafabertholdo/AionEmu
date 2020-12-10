package com.aionemu.gameserver.network.chatserver;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;


























public class CsPacketHandler
{
  private static final Logger log = Logger.getLogger(CsPacketHandler.class);
  
  private Map<ChatServerConnection.State, Map<Integer, CsClientPacket>> packetPrototypes = new HashMap<ChatServerConnection.State, Map<Integer, CsClientPacket>>();








  
  public CsClientPacket handle(ByteBuffer data, ChatServerConnection client) {
    ChatServerConnection.State state = client.getState();
    int id = data.get() & 0xFF;
    
    return getPacket(state, id, data, client);
  }






  
  public void addPacketPrototype(CsClientPacket packetPrototype, ChatServerConnection.State... states) {
    for (ChatServerConnection.State state : states) {
      
      Map<Integer, CsClientPacket> pm = this.packetPrototypes.get(state);
      if (pm == null) {
        
        pm = new HashMap<Integer, CsClientPacket>();
        this.packetPrototypes.put(state, pm);
      } 
      pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
    } 
  }









  
  private CsClientPacket getPacket(ChatServerConnection.State state, int id, ByteBuffer buf, ChatServerConnection con) {
    CsClientPacket prototype = null;
    
    Map<Integer, CsClientPacket> pm = this.packetPrototypes.get(state);
    if (pm != null)
    {
      prototype = pm.get(Integer.valueOf(id));
    }
    
    if (prototype == null) {
      
      unknownPacket(state, id);
      return null;
    } 
    
    CsClientPacket res = prototype.clonePacket();
    res.setBuffer(buf);
    res.setConnection(con);
    
    return res;
  }







  
  private void unknownPacket(ChatServerConnection.State state, int id) {
    log.warn(String.format("Unknown packet recived from Chat Server: 0x%02X state=%s", new Object[] { Integer.valueOf(id), state.toString() }));
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\chatserver\CsPacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
