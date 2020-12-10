package com.aionemu.gameserver.network.aion.serverpackets;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionConnection;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
























public class SM_PLAYER_SEARCH
  extends AionServerPacket
{
  private static final Logger log = Logger.getLogger(SM_PLAYER_SEARCH.class);


  
  private List<Player> players;


  
  private int region;



  
  public SM_PLAYER_SEARCH(List<Player> players, int region) {
    this.players = new ArrayList<Player>(players);
    this.region = region;
  }





  
  public void writeImpl(AionConnection con, ByteBuffer buf) {
    writeH(buf, this.players.size());
    for (Player player : this.players) {
      
      if (player.getActiveRegion() == null)
      {
        log.warn("CHECKPOINT: null active region for " + player.getObjectId() + "-" + player.getX() + "-" + player.getY() + "-" + player.getZ());
      }
      
      writeD(buf, (player.getActiveRegion() == null) ? this.region : player.getActiveRegion().getMapId().intValue());
      writeF(buf, player.getPosition().getX());
      writeF(buf, player.getPosition().getY());
      writeF(buf, player.getPosition().getZ());
      writeC(buf, player.getPlayerClass().getClassId());
      writeC(buf, player.getGender().getGenderId());
      writeC(buf, player.getLevel());
      
      writeC(buf, player.isLookingForGroup() ? 2 : 0);
      writeS(buf, player.getName());
      byte[] unknown = new byte[44 - player.getName().length() * 2 + 2];
      writeB(buf, unknown);
    } 
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\network\aion\serverpackets\SM_PLAYER_SEARCH.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
