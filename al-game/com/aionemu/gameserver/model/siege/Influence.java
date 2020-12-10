package com.aionemu.gameserver.model.siege;

import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.network.aion.AionServerPacket;
import com.aionemu.gameserver.network.aion.serverpackets.SM_INFLUENCE_RATIO;
import com.aionemu.gameserver.services.SiegeService;
import com.aionemu.gameserver.utils.PacketSendUtility;
import com.aionemu.gameserver.world.World;






















public class Influence
{
  private float elyos = 0.0F;
  private float asmos = 0.0F;
  private float balaur = 0.0F;

  
  private Influence() {
    calculateInfluence();
  }

  
  public static final Influence getInstance() {
    return SingletonHolder.instance;
  }




  
  public void recalculateInfluence() {
    calculateInfluence();
    
    broadcastInfluencePacket();
  }




  
  private void calculateInfluence() {
    int total = 0;
    int asmos = 0;
    int elyos = 0;
    int balaur = 0;
    
    for (SiegeLocation sLoc : SiegeService.getInstance().getSiegeLocations().values()) {

      
      total += sLoc.getInfluenceValue();
      switch (sLoc.getRace()) {
        
        case BALAUR:
          balaur += sLoc.getInfluenceValue();
        
        case ASMODIANS:
          asmos += sLoc.getInfluenceValue();
        
        case ELYOS:
          elyos += sLoc.getInfluenceValue();
      } 

    
    } 
    this.balaur = balaur / total;
    this.elyos = elyos / total;
    this.asmos = asmos / total;
  }





  
  private void broadcastInfluencePacket() {
    SM_INFLUENCE_RATIO pkt = new SM_INFLUENCE_RATIO();
    
    for (Player player : World.getInstance().getAllPlayers())
    {
      PacketSendUtility.sendPacket(player, (AionServerPacket)pkt);
    }
  }




  
  public float getElyos() {
    return this.elyos;
  }




  
  public float getAsmos() {
    return this.asmos;
  }




  
  public float getBalaur() {
    return this.balaur;
  }

  
  private static class SingletonHolder
  {
    protected static final Influence instance = new Influence();
  }
}


/* Location:              D:\games\aion\servers\AionLightning1.9\docker-gs\gameserver\al-game-1.0.1.jar!\com\aionemu\gameserver\model\siege\Influence.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */
