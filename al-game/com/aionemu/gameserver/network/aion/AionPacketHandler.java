package com.aionemu.gameserver.network.aion;

import com.aionemu.gameserver.configs.main.OptionsConfig;
import com.aionemu.gameserver.utils.Util;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;



























public class AionPacketHandler
{
  private static final Logger log = Logger.getLogger(AionPacketHandler.class);
  
  private Map<AionConnection.State, Map<Integer, AionClientPacket>> packetsPrototypes = new HashMap<AionConnection.State, Map<Integer, AionClientPacket>>();








  
  public AionClientPacket handle(ByteBuffer data, AionConnection client) {
    AionConnection.State state = client.getState();
    int id = data.get() & 0xFF;

    
    data.position(data.position() + 2);
    
    return getPacket(state, id, data, client);
  }

  
  public void addPacketPrototype(AionClientPacket packetPrototype, AionConnection.State... states) {
    for (AionConnection.State state : states) {
      
      Map<Integer, AionClientPacket> pm = this.packetsPrototypes.get(state);
      if (pm == null) {
        
        pm = new HashMap<Integer, AionClientPacket>();
        this.packetsPrototypes.put(state, pm);
      } 
      pm.put(Integer.valueOf(packetPrototype.getOpcode()), packetPrototype);
    } 
  }

  
  private AionClientPacket getPacket(AionConnection.State state, int id, ByteBuffer buf, AionConnection con) {
    AionClientPacket prototype = null;
    
    Map<Integer, AionClientPacket> pm = this.packetsPrototypes.get(state);
    if (pm != null)
    {
      prototype = pm.get(Integer.valueOf(id));
    }
    
    if (prototype == null) {
      
      unknownPacket(state, id, buf);
      return null;
    } 
    
    AionClientPacket res = prototype.clonePacket();
    res.setBuffer(buf);
    res.setConnection(con);
    
    return res;
  }








  
  private void unknownPacket(AionConnection.State state, int id, ByteBuffer data) {
    if (OptionsConfig.LOG_PACKETS)
      log.warn(String.format("[UNKNOWN PACKET] : 0x%02X, state=%s %n%s", new Object[] { Integer.valueOf(id), state.toString(), Util.toHex(data) })); 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\AionPacketHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
