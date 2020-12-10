package com.aionemu.gameserver.services;

import com.aionemu.commons.database.dao.DAOManager;
import com.aionemu.gameserver.configs.main.SiegeConfig;
import com.aionemu.gameserver.dao.SiegeDAO;
import com.aionemu.gameserver.dataholders.DataManager;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.siege.Influence;
import com.aionemu.gameserver.model.siege.SiegeLocation;
import com.aionemu.gameserver.model.siege.SiegeRace;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_SIEGE_LOCATION_INFO;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;
import javolution.util.FastMap;
import org.apache.log4j.Logger;






















public class SiegeService
{
  private static Logger log = Logger.getLogger(SiegeService.class);
  private FastMap<Integer, SiegeLocation> locations;
  
  public static final SiegeService getInstance() {
    return SingletonHolder.instance;
  }



  
  public SiegeService() {
    if (SiegeConfig.SIEGE_ENABLED) {
      
      log.info("Loading Siege Location Data...");
      
      this.locations = DataManager.SIEGE_LOCATION_DATA.getSiegeLocations();
      
      ((SiegeDAO)DAOManager.getDAO(SiegeDAO.class)).loadSiegeLocations(this.locations);
    }
    else {
      
      log.info("Siege Disabled by Config.");
      
      this.locations = new FastMap();
    } 
  }

  
  public FastMap<Integer, SiegeLocation> getSiegeLocations() {
    return this.locations;
  }






  
  public int getSiegeTime() {
    return 0;
  }





  
  public SiegeLocation getSiegeLocation(int locationId) {
    return (SiegeLocation)this.locations.get(Integer.valueOf(locationId));
  }





  
  public void capture(int locationId, SiegeRace race) {
    capture(locationId, race, 0);
  }








  
  public void capture(int locationId, SiegeRace race, int legionId) {
    SiegeLocation sLoc = (SiegeLocation)this.locations.get(Integer.valueOf(locationId));
    sLoc.setRace(race);
    sLoc.setLegionId(legionId);
    
    if (sLoc instanceof com.aionemu.gameserver.model.siege.Fortress) {
      sLoc.setVulnerable(false);
    }
    ((SiegeDAO)DAOManager.getDAO(SiegeDAO.class)).updateSiegeLocation(sLoc);
    
    broadcastUpdate(sLoc);
    Influence.getInstance().recalculateInfluence();
  }




  
  public void broadcastUpdate(SiegeLocation loc) {
    SM_SIEGE_LOCATION_INFO pkt = new SM_SIEGE_LOCATION_INFO(loc);
    broadcast(pkt);
  }

  
  public void broadcastUpdate() {
    SM_SIEGE_LOCATION_INFO pkt = new SM_SIEGE_LOCATION_INFO();
    broadcast(pkt);
  }

  
  private void broadcast(SM_SIEGE_LOCATION_INFO pkt) {
    for (Player player : World.getInstance().getAllPlayers())
    {
      PacketSendUtility.sendPacket(player, (AionServerPacket)pkt);
    }
  }


  
  private static class SingletonHolder
  {
    protected static final SiegeService instance = new SiegeService();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\services\SiegeService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
